package net.aurika.common.keyed;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractKeyedRegistry<K, T extends Keyed<K>> implements KeyedRegistry<K, T> {

  protected Map<K, T> registry;

  protected AbstractKeyedRegistry() {
  }

  protected AbstractKeyedRegistry(Map<K, T> registry) {
    this.registry = registry;
  }

  /**
   * Gets the raw registry.
   *
   * @return The raw registry
   */
  protected Map<K, T> rawRegistry() {
    return this.registry;
  }

  /**
   * Sets the raw registry.
   *
   * @return The old registry (maybe null)
   */
  protected Map<K, T> rawRegistry(Map<K, T> newRegistry) {
    Map<K, T> oldRegistry = registry;
    this.registry = newRegistry;
    return oldRegistry;
  }

  @Override
  public void register(T obj) {
    if (obj == null) return;
    if (this.registry == null) {
      this.registry = new HashMap<>();
    }
    K key = obj.key();
    Objects.requireNonNull(key, "Obj key");
    this.registry.put(key, obj);
  }

  @Override
  public boolean isRegistered(K key) {
    return this.registry != null && this.registry.containsKey(key);
  }

  @Override
  public @Nullable T getRegistered(K key) {
    return this.registry == null ? null : this.registry.get(key);
  }

}
