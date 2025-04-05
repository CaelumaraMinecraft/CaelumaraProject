package net.aurika.common.key.registry;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractKeyedRegistry<T extends Keyed> implements KeyedRegistry<T> {

  private Map<Key, T> registry;

  protected AbstractKeyedRegistry() {
  }

  protected AbstractKeyedRegistry(Map<Key, T> registry) {
    this.registry = registry;
  }

  /**
   * Get the raw registry.
   *
   * @return The raw registry
   */
  protected Map<Key, T> rawRegistry() {
    return this.registry;
  }

  /**
   * Set the raw registry.
   *
   * @return The old registry (maybe null)
   */
  protected Map<Key, T> rawRegistry(Map<Key, T> newRegistry) {
    Map<Key, T> oldRegistry = registry;
    this.registry = newRegistry;
    return oldRegistry;
  }

  @Override
  public void register(T obj) {
    if (obj == null) return;
    if (this.registry == null) {
      this.registry = new HashMap<>();
    }
    Key key = obj.key();
    Objects.requireNonNull(key, "Obj key");
    this.registry.put(key, obj);
  }

  @Override
  public boolean isRegistered(Key key) {
    return this.registry != null && this.registry.containsKey(key);
  }

  @Override
  public @Nullable T getRegistered(Key key) {
    return this.registry == null ? null : this.registry.get(key);
  }

}
