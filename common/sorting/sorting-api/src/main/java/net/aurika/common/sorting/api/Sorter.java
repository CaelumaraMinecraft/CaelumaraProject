package net.aurika.common.sorting.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Sorter.
 *
 * @param <E> the element type
 * @param <C> the container type
 */
public interface Sorter<E, C> {

  @Contract(mutates = "param1")
  void sort(@NotNull C container);

  @Contract(pure = true)
  @NotNull Collection<? extends E> elements(C container);

}
