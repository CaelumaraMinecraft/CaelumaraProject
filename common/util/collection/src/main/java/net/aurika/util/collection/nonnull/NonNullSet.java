package net.aurika.util.collection.nonnull;

import kotlin.jvm.internal.markers.KMutableSet;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class NonNullSet<E> implements Set<E>, KMutableSet {

  private final @NotNull Set<E> set;

  public NonNullSet() {
    this(new HashSet<>());
  }

  public NonNullSet(@NotNull Set<E> set) {
    Objects.requireNonNull(set, "set");
    this.set = set;
  }

  @Override
  public void clear() {
    set.clear();
  }

  @Override
  public boolean isEmpty() {
    return set.isEmpty();
  }

  @Override
  @NotNull
  public Iterator<E> iterator() {
    return set.iterator();
  }

  @Override
  public int size() {
    return set.size();
  }

  @Override
  public boolean addAll(@NotNull Collection<? extends E> elements) {
    Objects.requireNonNull(elements, "elements");
    return set.addAll(Nullability.assertNonNullElements(elements));
  }

  @Override
  public boolean add(E element) {
    return set.add(Nullability.assertNonNull(this, element));
  }

  @Override
  public boolean remove(Object element) {
    return set.remove(Nullability.assertNonNull(this, element));
  }

  @Override
  public boolean contains(Object element) {
    return set.contains(Nullability.assertNonNull(this, element));
  }

  @Override
  public boolean containsAll(@NotNull Collection<?> elements) {
    Objects.requireNonNull(elements, "elements");
    return set.containsAll(Nullability.assertNonNullElements(elements));
  }

  @Override
  public boolean retainAll(@NotNull Collection<?> elements) {
    Objects.requireNonNull(elements, "elements");
    return set.retainAll(Nullability.assertNonNullElements(elements));
  }

  @Override
  public boolean removeAll(@NotNull Collection<?> elements) {
    Objects.requireNonNull(elements, "elements");
    return set.removeAll(Nullability.assertNonNullElements(elements));
  }

  @Override
  public <T> T @NotNull [] toArray(T @NotNull [] array) {
    Objects.requireNonNull(array, "array");
    return set.toArray(array);
  }

  @Override
  public Object @NotNull [] toArray() {
    return set.toArray();
  }

}
