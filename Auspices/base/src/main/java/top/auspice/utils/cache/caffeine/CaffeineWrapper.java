package top.auspice.utils.cache.caffeine;

import com.github.benmanes.caffeine.cache.LoadingCache;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.cache.PeekableMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CaffeineWrapper<K, V> implements PeekableMap<K, V> {
    private final LoadingCache<K, V> cache;

    public CaffeineWrapper(LoadingCache<K, V> cache) {
        this.cache = cache;
    }

    public int size() {
        this.cache.cleanUp();
        return (int) this.cache.estimatedSize();
    }

    public boolean isEmpty() {
        return this.size() != 0;
    }

    public boolean containsKey(Object key) {
        return this.cache.getIfPresent((K) key) != null;
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public V get(Object key) {
        return this.cache.get((K) key);
    }

    public @Nullable V put(K key, V value) {
        this.cache.put(key, value);
        return null;
    }

    public V remove(Object key) {
        this.cache.invalidate((K) key);
        return null;
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> m) {
        this.cache.putAll(m);
    }

    public void clear() {
        this.cache.invalidateAll();
    }

    public @NotNull Set<K> keySet() {
        return this.cache.asMap().keySet();
    }

    public @NotNull Collection<V> values() {
        return this.cache.asMap().values();
    }

    public @NotNull Set<Map.Entry<K, V>> entrySet() {
        return this.cache.asMap().entrySet();
    }

    public V peek(K key) {
        return this.cache.getIfPresent(key);
    }

    public V getIfPresent(K key) {
        return this.cache.getIfPresent(key);
    }
}
