package net.aurika.common.sorting.ident;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.common.sorting.api.Sorter;
import net.aurika.util.collection.IndexedMap;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.Map;

public interface IdentIndexedMapSorter<T extends Identified, C extends IndexedMap<Ident, T>> extends Sorter<T, C> {

  @Contract("_ -> new")
  static <T extends Identified, C extends IndexedMap<Ident, T>> @NotNull IdentIndexedMapSorter<T, C> identIndexedMapSorter(
      @NotNull Map<Ident, Sort[]> sorts
  ) {
    return new DefaultIdentIndexedMapSorter<>(sorts);
  }

  @Contract(value = "_ -> new", pure = true)
  static @NotNull IndexSort indexSort(int index) {
    return new DefaultIdentIndexedMapSorter.IndexSortImpl(index);
  }

  @Contract(value = "_ -> new", pure = true)
  static @NotNull PriorySort priorySort(double priory) {
    return new DefaultIdentIndexedMapSorter.PriorySortImpl(priory);
  }

  @Contract("_, _ -> new")
  static @NotNull BeforeSort beforeSort(@NotNull Ident targetId, int distance) {
    return new DefaultIdentIndexedMapSorter.BeforeSortImpl(targetId, distance);
  }

  @Contract("_, _ -> new")
  static @NotNull AfterSort afterSort(@NotNull Ident targetId, int distance) {
    return new DefaultIdentIndexedMapSorter.AfterSortImpl(targetId, distance);
  }

  @Override
  void sort(@NotNull C container);

  @Override
  @NotNull Collection<? extends T> elements(@NotNull C container);

  /**
   * Requires a specific index.
   */
  interface IndexSort extends Sort {

    /**
     * @return the index
     */
    @Range(from = 0, to = Integer.MAX_VALUE)
    int index();

  }

  interface PriorySort extends Sort {

    @Range(from = 0, to = 1)
    double priory();

  }

  interface BeforeSort extends RelativeSort { }

  interface AfterSort extends RelativeSort { }

  interface RelativeSort extends Sort {

    /**
     * Don't require a fixed distance.
     */
    int LOOSE_DISTANCE = -1;

    /**
     * @return the target element ident
     */
    @NotNull Ident targetId();

    /**
     * @return the distance from the target
     */
    int distance();

  }

  interface Sort { }

}

