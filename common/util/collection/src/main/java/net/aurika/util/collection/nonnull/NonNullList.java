package net.aurika.util.collection.nonnull;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class NonNullList<V> implements List<V> {

  private final @NotNull List<V> list;

  public NonNullList(@NotNull List<V> list) {
    Intrinsics.checkNotNullParameter(list, "list");
    this.list = list;
  }

  public @NotNull List<V> getList() {
    return list;
  }

  public NonNullList(int size) {
    this(new ArrayList<>(size));
  }

  @Override
  public V set(int index, V element) {
    return list.set(index, Nullability.assertNonNull(this, element));
  }

  @Override
  public boolean add(V element) {
    return list.add(Nullability.assertNonNull(this, element));
  }

  @Override
  public void add(int index, V element) {
    list.add(index, Nullability.assertNonNull(this, element));
  }

  @Override
  public V remove(int index) {
    return list.remove(index);
  }

  @Override
  public boolean addAll(int index, @NotNull Collection<? extends V> elements) {
    Intrinsics.checkNotNullParameter(elements, "elements");
    return list.addAll(index, Nullability.assertNonNullElements(elements));
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends V> elements) {
    Intrinsics.checkNotNullParameter(elements, "elements");
    return list.addAll(Nullability.assertNonNullElements(elements));
  }

  @Override
  public boolean remove(Object element) {
    return list.remove(Nullability.assertNonNull(this, element));
  }

  @Override
  public int indexOf(Object element) {
    return list.indexOf(Nullability.assertNonNull(this, element));
  }

  @Override
  public int lastIndexOf(Object element) {
    return list.lastIndexOf(Nullability.assertNonNull(this, element));
  }

  @Override
  public boolean contains(Object element) {
    return list.contains(Nullability.assertNonNull(this, element));
  }

  @Override
  public boolean containsAll(@NotNull Collection<?> elements) {
    Intrinsics.checkNotNullParameter(elements, "elements");
    return (new HashSet<>(list)).containsAll(Nullability.assertNonNullElements(elements));
  }

  @Override
  public boolean retainAll(@NotNull Collection<?> elements) {
    Intrinsics.checkNotNullParameter(elements, "elements");
    return list.retainAll(CollectionsKt.toSet(Nullability.assertNonNullElements(elements)));
  }

  @Override
  public boolean removeAll(@NotNull Collection<?> elements) {
    Intrinsics.checkNotNullParameter(elements, "elements");
    return list.removeAll(CollectionsKt.toSet(Nullability.assertNonNullElements(elements)));
  }

  public int hashCode() {
    throw new UnsupportedOperationException("Hashcode are not supported for this implementation");
  }

  public @NotNull String toString() {
    return getClass().getSimpleName() + '(' + list + ')';
  }

  @Override
  public void clear() {
    list.clear();
  }

  @Override
  public @NotNull ListIterator<V> listIterator() {
    return list.listIterator();
  }

  @Override
  public @NotNull ListIterator<V> listIterator(int index) {
    return list.listIterator(index);
  }

  @Override
  public @NotNull List<V> subList(int fromIndex, int toIndex) {
    return list.subList(fromIndex, toIndex);
  }

  @Override
  public int size() {
    return list.size();
  }

  @Override
  public boolean isEmpty() {
    return list.isEmpty();
  }

  @Override
  public @NotNull Iterator<V> iterator() {
    return list.iterator();
  }

  @Override
  public V get(int index) {
    return list.get(index);
  }

  @Override
  public <T> T @NotNull [] toArray(T @NotNull [] array) {
    Intrinsics.checkNotNullParameter(array, "array");
    return list.toArray(array);
  }

  @Override
  public Object @NotNull [] toArray() {
    return list.toArray();
  }

}
