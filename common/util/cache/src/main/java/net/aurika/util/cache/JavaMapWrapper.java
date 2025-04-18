package net.aurika.util.cache;

import com.github.benmanes.caffeine.cache.CacheLoader;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class JavaMapWrapper<K, V> implements PeekableMap<K, V> {

  private final ConcurrentHashMap<K, V> cache;
  private final CacheLoader<K, V> loader;

  public JavaMapWrapper(@NotNull ConcurrentHashMap<K, V> cache, @NotNull CacheLoader<K, V> loader) {
    Validate.Arg.notNull(cache, "cache");
    Validate.Arg.notNull(loader, "loader");
    this.cache = cache;
    this.loader = loader;
  }

  @Override
  public int size() { return cache.size(); }

  @Override
  public boolean isEmpty() { return cache.isEmpty(); }

  @Override
  public boolean containsKey(Object key) { return cache.containsKey(key); }

  @Override
  public boolean containsValue(Object value) { throw new UnsupportedOperationException(); }

  @SuppressWarnings("unchecked")
  @Override
  public V get(Object key) {
    V data = cache.get(key);
    if (data != null) return data;

    try {
      data = loader.load((K) key);
      if (data != null) put((K) key, data);
      return data;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Nullable
  @Override
  public V put(K key, V value) {
    cache.put(key, value);
    return null;
  }

  @Override
  public V remove(Object key) {
    cache.remove(key);
    return null;
  }

  @Override
  public void putAll(@NotNull Map<? extends K, ? extends V> m) {
    cache.putAll(m);
  }

  @Override
  public void clear() {
    cache.clear();
  }

  @NotNull
  @Override
  public Set<K> keySet() {
    return cache.keySet();
  }

  @NotNull
  @Override
  public Collection<V> values() {
    return cache.values();
  }

  @NotNull
  @Override
  public Set<Entry<K, V>> entrySet() {
    return cache.entrySet();
  }

  @Override
  public V peek(K key) {
    return cache.get(key);
  }

  @Override
  public V getIfPresent(K key) {
    return cache.get(key);
  }

}
