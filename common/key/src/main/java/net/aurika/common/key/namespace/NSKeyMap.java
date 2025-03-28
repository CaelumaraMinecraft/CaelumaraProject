package net.aurika.common.key.namespace;

import net.aurika.auspice.utils.nonnull.NonNullMap;
import net.aurika.auspice.utils.unsafe.string.PrettyStringFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class NSKeyMap<V> implements Map<NSedKey, V> {

  protected final Map<NSedKey, V> map;

  public NSKeyMap() {
    this.map = new NonNullMap<>();
  }

  public NSKeyMap(Map<NSedKey, V> map) {
    this.map = NonNullMap.of(map);
  }

  public int size() {
    return this.map.size();
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  public V get(Object key) {
    return this.map.get(key);
  }

  public @Nullable V put(NSedKey key, V value) {
    return this.map.put(key, value);
  }

  public V remove(Object key) {
    return this.map.remove(key);
  }

  public void putAll(@NotNull Map<? extends NSedKey, ? extends V> m) {
    this.map.putAll(m);
  }

  public void clear() {
    this.map.clear();
  }

  public @NotNull Set<NSedKey> keySet() {
    return this.map.keySet();
  }

  public @NotNull Collection<V> values() {
    return this.map.values();
  }

  public @NotNull Set<Map.Entry<NSedKey, V>> entrySet() {
    return this.map.entrySet();
  }

  public @NotNull String toString() {
    return PrettyStringFactory.toDefaultPrettyString(this);
  }

}
