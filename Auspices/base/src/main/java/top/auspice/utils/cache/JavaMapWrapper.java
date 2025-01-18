package top.auspice.utils.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class JavaMapWrapper<K, V> implements PeekableMap<K, V> {
    private final ConcurrentHashMap<K, V> cache;
    private final CacheLoader<K, V> loader;

    public JavaMapWrapper(ConcurrentHashMap<K, V> cache, CacheLoader<K, V> loader) {
        this.cache = cache;
        this.loader = loader;
    }

    public int size() {
        return this.cache.size();
    }

    public boolean isEmpty() {
        return this.cache.isEmpty();
    }

    public boolean containsKey(Object key) {
        return this.cache.containsKey(key);
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public V get(Object key) {
        V data = this.cache.get(key);
        if (data != null) {
            return data;
        } else {
            try {
                data = this.loader.load((K) key);
                if (data != null) {
                    this.put((K) key, data);
                }

                return data;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public @Nullable V put(K key, V value) {
        this.cache.put(key, value);
        return null;
    }

    public V remove(Object key) {
        this.cache.remove(key);
        return null;
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        this.cache.putAll(m);
    }

    public void clear() {
        this.cache.clear();
    }

    public @NotNull Set<K> keySet() {
        return this.cache.keySet();
    }

    public @NotNull Collection<V> values() {
        return this.cache.values();
    }

    public @NotNull Set<Map.Entry<K, V>> entrySet() {
        return this.cache.entrySet();
    }

    public V peek(K key) {
        return this.cache.get(key);
    }

    public V getIfPresent(K key) {
        return this.cache.get(key);
    }
}
