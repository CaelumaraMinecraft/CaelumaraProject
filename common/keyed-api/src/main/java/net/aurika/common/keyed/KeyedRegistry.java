package net.aurika.common.keyed;

import org.jetbrains.annotations.Nullable;

public interface KeyedRegistry<K, T> {

  void register(T obj);

  boolean isRegistered(K key);

  @Nullable T getRegistered(K key);

}
