package net.aurika.common.sorting.key;

import net.aurika.common.key.Keyed;
import net.aurika.common.key.registry.KeyedRegistry;
import org.jetbrains.annotations.NotNull;

public class After<T extends Keyed, R extends KeyedRegistry<T>> implements KeyedRegistrySorter<T, R> {

  @Override
  public void sort(@NotNull R container) {
  }

}
