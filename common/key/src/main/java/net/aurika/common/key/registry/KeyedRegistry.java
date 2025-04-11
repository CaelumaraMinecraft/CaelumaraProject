package net.aurika.common.key.registry;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface KeyedRegistry<T extends Keyed> extends net.aurika.common.keyed.KeyedRegistry<Key, T> {

  @Override
  void register(T obj);

  @Override
  boolean isRegistered(Key key);

  @Override
  @Nullable T getRegistered(Key key);

  @NotNull Map<Key, T> registry();

}
