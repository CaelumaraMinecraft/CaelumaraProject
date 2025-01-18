package top.auspice.utils.internal.arrays;

import kotlin.jvm.internal.CollectionToArray;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.Checker;
import top.auspice.utils.nonnull.NonNullList;

import java.util.*;

public final class FunctionalList<E> implements List<E> {
    @NotNull
    private final List<E> original;

    public FunctionalList() {
        this(new NonNullList<>(new ArrayList<>()));
    }

    public FunctionalList(@NotNull final List<E> original) {
        Checker.Argument.checkNotNull(original, "original");
        this.original = original;
    }

    public int getSize() {
        return this.original.size();
    }

    @Override
    public boolean add(final E element) {
        return this.original.add(element);
    }

    @Override
    public void add(final int index, final E element) {
        this.original.add(index, element);
    }

    @Override
    public boolean addAll(final int index, @NotNull final Collection<? extends E> elements) {
        Checker.Argument.checkNotNull(elements, "elements");
        return this.original.addAll(index, elements);
    }

    @Override
    public boolean addAll(@NotNull final Collection<? extends E> elements) {
        Checker.Argument.checkNotNull(elements, "elements");
        return this.original.addAll(elements);
    }

    @Override
    public void clear() {
        this.original.clear();
    }

    @Override
    public boolean contains(final Object element) {
        return this.original.contains(element);
    }

    @Override
    public boolean containsAll(@NotNull final Collection<?> elements) {
        Checker.Argument.checkNotNull(elements, "elements");
        return this.original.containsAll(elements);
    }

    @Override
    public E get(final int index) {
        return this.original.get(index);
    }

    @Override
    public int indexOf(final Object element) {
        return this.original.indexOf(element);
    }

    @Override
    public boolean isEmpty() {
        return this.original.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return this.original.iterator();
    }

    @Override
    public int lastIndexOf(final Object element) {
        return this.original.lastIndexOf(element);
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator() {
        return this.original.listIterator();
    }

    @NotNull
    @Override
    public ListIterator<E> listIterator(final int index) {
        return this.original.listIterator(index);
    }

    @Override
    public boolean remove(final Object element) {
        return this.original.remove(element);
    }

    @Override
    public boolean removeAll(@NotNull final Collection<?> elements) {
        Checker.Argument.checkNotNull(elements, "elements");
        return this.original.removeAll(elements);
    }

    public E removeAt(final int index) {
        return this.original.remove(index);
    }

    @Override
    public boolean retainAll(@NotNull final Collection<?> elements) {
        Checker.Argument.checkNotNull(elements, "elements");
        return this.original.retainAll(elements);
    }

    @Override
    public E set(final int index, final E element) {
        return this.original.set(index, element);
    }

    @NotNull
    @Override
    public List<E> subList(final int fromIndex, final int toIndex) {
        return this.original.subList(fromIndex, toIndex);
    }

    @NotNull
    public FunctionalList<E> addIf(final boolean condition, final E element) {
        if (condition) {
            this.add(element);
        }
        return this;
    }

    @NotNull
    public FunctionalList<E> removeIf(final boolean condition, final E element) {
        if (condition) {
            this.remove(element);
        }
        return this;
    }

    @NotNull
    public static <T> FunctionalList<T> create() {
        return new FunctionalList<>();
    }

    @Override
    public int size() {
        return this.getSize();
    }

    @Override
    public E remove(final int index) {
        return this.removeAt(index);
    }

    @Override
    public <T> T[] toArray(final T @NotNull [] array) {
        Checker.Argument.checkNotNull(array, "array");
        return (T[]) CollectionToArray.toArray(this, array);
    }

    @Override
    public Object[] toArray() {
        return CollectionToArray.toArray(this);
    }
}
