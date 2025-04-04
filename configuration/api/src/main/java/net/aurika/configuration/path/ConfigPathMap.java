package net.aurika.configuration.path;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ConfigPathMap<V> {

  private final Map<ConfigPath, V> map;

  public ConfigPathMap() {
    this(new HashMap<>());
  }

  public ConfigPathMap(Map<ConfigPath, V> map) {
    this.map = map;
  }

  public V put(String path, V value) {
    return this.map.put(new ConfigPath(path), value);
  }

  public V put(String[] path, V value) {
    return this.map.put(new ConfigPath(path), value);
  }

  public V put(ConfigPath path, V value) {
    return this.map.put(path, value);
  }

  public V get(String path) {
    return this.map.get(new ConfigPath(path));
  }

  public V get(String[] path) {
    return this.map.get(new ConfigPath(path));
  }

  public V get(ConfigPath path) {
    return this.map.get(path);
  }

  /**
   * 自动将形参 map 的 String key 转化为 {@link ConfigPath}
   */
  @Contract("_ -> new")
  public static <V> ConfigPathMap<V> of(@Nullable Map<String, V> map) {
    ConfigPathMap<V> out = new ConfigPathMap<>();
    if (map != null) {
      for (Map.Entry<String, V> entry : map.entrySet()) {
        out.put(entry.getKey(), entry.getValue());
      }
    }
    return out;
  }

}
