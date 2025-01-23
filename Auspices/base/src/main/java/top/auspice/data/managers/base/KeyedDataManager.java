package top.auspice.data.managers.base;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Nullable;
import top.auspice.data.centers.DataCenter;
import top.auspice.data.database.base.KeyedDatabase;
import top.auspice.data.object.KeyedDataObject;
import top.auspice.key.NSedKey;
import top.auspice.utils.cache.JavaMapWrapper;
import top.auspice.utils.cache.PeekableMap;
import top.auspice.utils.cache.caffeine.CacheHandler;
import top.auspice.utils.cache.caffeine.CaffeineWrapper;
import top.auspice.utils.logging.AuspiceLogger;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public abstract class KeyedDataManager<K, T extends KeyedDataObject<K>> extends DataManager<T> {
    protected final PeekableMap<K, T> cache;
    protected final Set<K> doesntExist;
    protected KeyedDatabase<K, T> database;
    private final boolean a;

    public KeyedDataManager(NSedKey NSedKey, final KeyedDatabase<K, T> database, boolean b, DataCenter kingdomsDataCenter) {
        super(NSedKey, kingdomsDataCenter);
        this.database = database;
        if (b) {
            this.doesntExist = new HashSet<K>();
        } else {
            this.doesntExist = null;
        }
        final CacheLoader<K, T> cacheLoader = b ? new CacheLoader_1(database) : new CacheLoader_2(database);
        if (this.isCacheStatic()) {
            this.cache = new JavaMapWrapper<>(new ConcurrentHashMap<>(), cacheLoader);
            this.a = false;
            return;
        }
        final Caffeine<Object, Object> builder = CacheHandler.newBuilder();
        final Duration autoSaveInterval;
        Duration duration;
        if ((autoSaveInterval = this.getAutoSaveInterval()) == null || autoSaveInterval.toMinutes() < 30L) {
            duration = Duration.ofMinutes(30L);
        } else {
            duration = autoSaveInterval.plusMinutes(10L);
        }
        builder.expireAfterAccess(duration.toMillis(), TimeUnit.MILLISECONDS);
        builder.weakValues();
        this.cache = new CaffeineWrapper<>(builder.build(cacheLoader));
        this.a = true;
    }

    public void delete(@NonNull T object) {
        this.unload(object);
        K key = object.getKey();
        this.database.delete(key);
        if (this.doesntExist != null) {
            this.doesntExist.add(key);
        }
    }

    public void clearCache() {
        this.cache.clear();
        if (this.doesntExist != null) {
            this.doesntExist.clear();
        }
    }

    public KeyedDatabase<K, T> getDatabase() {
        return this.database;
    }

    public void deleteAllData() {
        this.cache.clear();
        this.database.deleteAllData();
    }

    public void copyAllDataTo(DataManager<T> otherManager) {
        if (!(otherManager instanceof KeyedDataManager)) {
            throw new UnsupportedOperationException("Cannot copy from " + this + " to " + otherManager);
        } else {
            ((KeyedDataManager<K, T>) otherManager).cache.putAll(this.cache);
        }
    }

    @Internal
    public Collection<T> loadAllData(boolean b) {
        final Collection<T> collection = (b ? this.database.loadAllData(o -> !this.cache.containsKey(o)) : this.database.loadAllData(null));
        for (T o : collection) {
            this.cache(o, false);
        }
        return collection;
    }

    @Internal
    public List<T> load(Iterable<K> var1, List<T> container) {
        HashSet<K> keys = new HashSet<>();

        for (K key : var1) {
            T object = this.cache.getIfPresent(key);
            if (object != null) {
                container.add(object);
            } else {
                keys.add(key);
            }
        }

        this.database.load(keys, (var2x) -> {
            container.add(var2x);
            this.cache(var2x, false);
        });
        return container;
    }

    public int saveAll(boolean var1) {
        if (!var1) {
            Collection<T> var5 = this.cache.values();
            this.database.save(var5);
            return var5.size();
        } else if (!super.savingState) {
            AuspiceLogger.info("Saving state was turned off for " + this + ", skipping saving data...");
            return 0;
        } else {
            ArrayList<T> objects = new ArrayList<>(this.cache.size());

            for (T var3 : this.cache.values()) {
                if (!this.isSmartSaving() || var3.shouldSave()) {
                    objects.add(var3);
                }
            }

            this.database.save(objects);
            return objects.size();
        }
    }

    public void loadAndSave(@NonNull T var1) {
        this.cache(var1, true);
        this.database.save(var1);
    }

    public @Nullable T getOrLoadData(@NonNull K key) {
        T var2 = this.cache.get(key);
        this.saveObjectState(var2, false);
        return var2;
    }

    public T getDataIfLoaded(K key) {
        return this.cache.getIfPresent(key);
    }

    public boolean isLoaded(K key) {
        return this.cache.containsKey(key);
    }

    protected void unload(@NonNull T object) {
        super.unload(object);
        this.cache.remove(object.getKey());
    }

    public void cache(@NonNull T object, boolean var2) {
        K key = object.getKey();
        object = this.onLoad(object);
        this.cache.put(key, object);
        this.saveObjectState(object, var2);
        if (this.doesntExist != null) {
            this.doesntExist.remove(key);
        }
    }

    public @Nullable T peek(@NonNull K var1) {
        T var2 = this.cache.peek(var1);
        if (var2 == null) {
            var2 = this.database.load(var1);
        }

        return var2;
    }

    public boolean exists(K var1) {
        if (this.doesntExist != null && this.doesntExist.contains(var1)) {
            return false;
        } else {
            return this.cache.containsKey(var1) || this.database.hasData(var1);
        }
    }

    public int size() {
        return this.cache.size();
    }

    public Collection<T> getLoadedData() {
        return this.cache.values();
    }

    public Collection<T> peekAllData() {
        if (this.a) {
            return this.getLoadedData();
        } else {
            Collection<K> var1 = this.database.getAllDataKeys();
            ArrayList<T> var2 = new ArrayList<>(var1.size());
            var2.addAll(this.cache.values());
            HashSet<K> var3 = new HashSet<>(var1);
            var3.removeAll(this.cache.keySet());
            KeyedDatabase<K, T> var10000 = this.database;
            Objects.requireNonNull(var2);
            var10000.load(var3, var2::add);
            return var2;
        }
    }

    class CacheLoader_1 implements CacheLoader<K, T> {
        private final KeyedDatabase<K, T> a;

        CacheLoader_1(KeyedDatabase<K, T> var2) {
            this.a = var2;
        }

        @Nullable
        public final T load(@NonNull K k) throws Exception {
            if (KeyedDataManager.this.doesntExist.contains(k)) {
                return null;
            } else {
                T var3 = this.a.load(k);
                if (var3 == null) {
                    KeyedDataManager.this.doesntExist.add(k);
                }

                if (var3 != null) {
                    var3 = KeyedDataManager.this.onLoad(var3);
                }

                return var3;
            }
        }
    }

    class CacheLoader_2 implements CacheLoader<K, T> {
        private final KeyedDatabase<K, T> dataBase;

        CacheLoader_2(KeyedDatabase<K, T> database) {
            this.dataBase = database;
        }

        @Nullable
        public final T load(@NonNull K key) throws Exception {
            T loaded = this.dataBase.load(key);
            if (loaded != null) {
                loaded = KeyedDataManager.this.onLoad(loaded);
            }

            return loaded;
        }
    }
}
