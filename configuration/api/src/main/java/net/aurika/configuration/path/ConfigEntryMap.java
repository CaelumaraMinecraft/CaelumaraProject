package net.aurika.configuration.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigEntryMap<V> implements Map<ConfigEntry, V> {

  private final Map<ConfigEntry, V> map;

  public ConfigEntryMap() {
    this(new HashMap<>());
  }

  public ConfigEntryMap(Map<ConfigEntry, V> map) {
    this.map = map;
  }

  public V put(String path, V value) {
    return this.map.put(ConfigEntry.fromString(path), value);
  }

  public V put(String[] path, V value) {
    return this.map.put(new ConfigEntry(path), value);
  }

  @Override
  public int size() {
    return this.map.size();
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return this.map.containsKey(key);  // TODO
  }

  @Override
  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  @Override
  public V get(Object key) {
    return this.map.get(key);
  }

  public V put(ConfigEntry path, V value) {
    return this.map.put(path, value);
  }

  @Override
  public V remove(Object key) {
    return this.map.remove(key); // TODO
  }

  @Override
  public void putAll(@NotNull Map<? extends ConfigEntry, ? extends V> m) {
    this.map.putAll(m);
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public @NotNull Set<ConfigEntry> keySet() {
    return this.map.keySet();
  }

  @Override
  public @NotNull Collection<V> values() {
    return this.map.values();
  }

  @Override
  public @NotNull Set<Entry<ConfigEntry, V>> entrySet() {
    return this.map.entrySet();
  }

  public V get(String path) {
    return this.map.get(ConfigEntry.fromString(path));
  }

  public V get(String[] path) {
    return this.map.get(new ConfigEntry(path));
  }

  public V get(ConfigEntry path) {
    return this.map.get(path);
  }

  /**
   * 自动将形参 map 的 String key 转化为 {@link ConfigEntry}
   */
  @Contract("_ -> new")
  public static <V> ConfigEntryMap<V> of(@Nullable Map<String, V> map) {
    ConfigEntryMap<V> out = new ConfigEntryMap<>();
    if (map != null) {
      for (Map.Entry<String, V> entry : map.entrySet()) {
        out.put(entry.getKey(), entry.getValue());
      }
    }
    return out;
  }

}
