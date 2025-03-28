package net.aurika.util.registry;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRegistry<K, O> implements Registry<K, O> {

  private final Map<K, O> registry;

  protected AbstractRegistry() {
    this(new HashMap<>());
  }

  protected AbstractRegistry(Map<K, O> registry) {
    this.registry = registry;
  }

  protected abstract K getKey(O o);

  protected Map<K, O> getRawRegistry() {
    return registry;
  }

  @Override
  public void register(O object) {
    registry.put(getKey(object), object);
  }

  @Override
  public O getRegistered(K key) {
    return registry.get(key);
  }

}