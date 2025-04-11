package net.aurika.common.sorting.key;

import net.aurika.common.key.Keyed;
import net.aurika.common.key.registry.KeyedRegistry;
import net.aurika.common.sorting.api.Sorter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface KeyedRegistrySorter<T extends Keyed, R extends KeyedRegistry<T>> extends Sorter<T, R> {

  @Override
  void sort(@NotNull R container);

  @Override
  default @NotNull Collection<T> elements(R container) {
    return container.registry().values();
  }

}
