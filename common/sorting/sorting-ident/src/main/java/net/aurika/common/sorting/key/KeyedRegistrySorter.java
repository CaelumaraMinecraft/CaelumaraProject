package net.aurika.common.sorting.key;

import net.aurika.common.ident.Identified;
import net.aurika.common.ident.registry.IdentifiedRegistry;
import net.aurika.common.sorting.api.Sorter;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface KeyedRegistrySorter<T extends Identified, R extends IdentifiedRegistry<T>> extends Sorter<T, R> {

  @Override
  void sort(@NotNull R container);

  @Override
  default @NotNull Collection<T> elements(R container) {
    return container.registry().values();
  }

}
