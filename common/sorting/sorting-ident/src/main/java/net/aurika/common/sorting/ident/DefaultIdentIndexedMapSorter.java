package net.aurika.common.sorting.ident;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.common.validate.Validate;
import net.aurika.util.collection.IndexedMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.Map;

public class DefaultIdentIndexedMapSorter<T extends Identified, C extends IndexedMap<Ident, T>> implements IdentIndexedMapSorter<T, C> {

  private final @NotNull Map<Ident, Sort[]> sorts;

  DefaultIdentIndexedMapSorter(@NotNull Map<Ident, Sort[]> sorts) {
    Validate.Arg.notNull(sorts, "sorts");
    this.sorts = sorts;
  }

  @Override
  public void sort(@NotNull C container) {
    Validate.Arg.notNull(container, "container");
  }

  protected void sortByRelative(@NotNull C container) {
  }

  @Override
  public @NotNull Collection<? extends T> elements(@NotNull C container) {
    return container.values();
  }

  protected static class IndexSortImpl implements IndexSort {

    private final int index;

    IndexSortImpl(int index) { this.index = index; }

    @Override
    @Range(from = 0, to = Integer.MAX_VALUE)
    public int index() {
      return index;
    }

  }

  protected static class PriorySortImpl implements PriorySort {

    private final double priory;

    PriorySortImpl(double priory) { this.priory = priory; }

    @Override
    @Range(from = 0, to = 1)
    public double priory() {
      return priory;
    }

  }

  protected static class BeforeSortImpl extends RelativeSortImpl implements BeforeSort {

    /**
     * @param targetId the targetId element ident
     * @param distance the distance form the target element
     */
    protected BeforeSortImpl(@NotNull Ident targetId, int distance) {
      super(targetId, distance);
    }

  }

  protected static class AfterSortImpl extends RelativeSortImpl implements AfterSort {

    /**
     * @param targetId the targetId element ident
     * @param distance the distance form the target element
     */
    protected AfterSortImpl(@NotNull Ident targetId, int distance) {
      super(targetId, distance);
    }

  }

  protected abstract static class RelativeSortImpl implements RelativeSort {

    private final @NotNull Ident target;
    private final int distance;

    /**
     * @param targetId the targetId element ident
     * @param distance the distance form the target element
     */
    protected RelativeSortImpl(@NotNull Ident targetId, int distance) {
      Validate.Arg.notNull(targetId, "targetId");
      this.target = targetId;
      this.distance = distance;
    }

    /**
     * @return the target element ident
     */
    @Override
    public @NotNull Ident targetId() { return this.target; }

    @Override
    public int distance() { return this.distance; }

  }

}
