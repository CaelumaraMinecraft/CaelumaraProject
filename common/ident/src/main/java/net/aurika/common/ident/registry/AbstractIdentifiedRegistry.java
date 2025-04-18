package net.aurika.common.ident.registry;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractIdentifiedRegistry<T extends Identified> implements IdentifiedRegistry<T> {

  private Map<Ident, T> registry;

  protected AbstractIdentifiedRegistry() {
  }

  protected AbstractIdentifiedRegistry(Map<Ident, T> registry) {
    this.registry = registry;
  }

  /**
   * Gets the raw registry.
   *
   * @return The raw registry
   */
  protected Map<Ident, T> rawRegistry() {
    return this.registry;
  }

  /**
   * Sets the raw registry.
   *
   * @return The old registry (maybe null)
   */
  protected Map<Ident, T> rawRegistry(Map<Ident, T> newRegistry) {
    Map<Ident, T> oldRegistry = registry;
    this.registry = newRegistry;
    return oldRegistry;
  }

  @Override
  public void register(T obj) {
    if (obj == null) return;
    if (this.registry == null) {
      this.registry = new HashMap<>();
    }
    Ident ident = obj.ident();
    Objects.requireNonNull(ident, "Obj key");
    this.registry.put(ident, obj);
  }

  @Override
  public boolean isRegistered(Ident ident) {
    return this.registry != null && this.registry.containsKey(ident);
  }

  @Override
  public @Nullable T getRegistered(Ident ident) {
    return this.registry == null ? null : this.registry.get(ident);
  }

  @Override
  public @NotNull Map<Ident, T> registry() {
    return this.registry == null ? new HashMap<>() : Collections.unmodifiableMap(this.registry);
  }

}
