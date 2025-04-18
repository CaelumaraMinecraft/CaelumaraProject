package net.aurika.common.sorting.key;

import net.aurika.common.ident.Identified;
import net.aurika.common.ident.registry.IdentifiedRegistry;
import org.jetbrains.annotations.NotNull;

public class After<T extends Identified, R extends IdentifiedRegistry<T>> implements KeyedRegistrySorter<T, R> {

  @Override
  public void sort(@NotNull R container) {
  }

}
