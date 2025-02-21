package net.aurika.util.collection;

import net.aurika.validate.Validate;
import net.aurika.util.collection.nonnull.NonNullList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public final class FunctionalList<E> implements List<E> {

    private final @NotNull List<E> original;

    @NotNull
    public static <T> FunctionalList<T> create() {
        return new FunctionalList<>();
    }

    public FunctionalList() {
        this(new NonNullList<>(new ArrayList<>()));
    }

    public FunctionalList(@NotNull List<E> original) {
        Validate.Arg.notNull(original, "original");
        this.original = original;
    }

    public @NotNull FunctionalList<E> addIf(boolean condition, E element) {
        if (condition) {
            add(element);
        }
        return this;
    }

    public @NotNull FunctionalList<E> removeIf(boolean condition, E element) {
        if (condition) {
            remove(element);
        }
        return this;
    }

    @Override
    public boolean add(E element) {
        return original.add(element);
    }

    @Override
    public void add(int index, E element) {
        original.add(index, element);
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends E> elements) {
        Validate.Arg.notNull(elements, "elements");
        return original.addAll(index, elements);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> elements) {
        Validate.Arg.notNull(elements, "elements");
        return original.addAll(elements);
    }

    @Override
    public void clear() {
        original.clear();
    }

    @Override
    public boolean contains(Object element) {
        return original.contains(element);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> elements) {
        Validate.Arg.notNull(elements, "elements");
        return new HashSet<>(original).containsAll(elements);
    }

    @Override
    public E get(int index) {
        return original.get(index);
    }

    @Override
    public int indexOf(Object element) {
        return original.indexOf(element);
    }

    @Override
    public boolean isEmpty() {
        return original.isEmpty();
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return original.iterator();
    }

    @Override
    public int lastIndexOf(Object element) {
        return original.lastIndexOf(element);
    }

    @Override
    public @NotNull ListIterator<E> listIterator() {
        return original.listIterator();
    }

    @Override
    public @NotNull ListIterator<E> listIterator(int index) {
        return original.listIterator(index);
    }

    @Override
    public boolean remove(Object element) {
        return original.remove(element);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> elements) {
        Validate.Arg.notNull(elements, "elements");
        return original.removeAll(elements);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> elements) {
        Validate.Arg.notNull(elements, "elements");
        return original.retainAll(elements);
    }

    @Override
    public E set(int index, E element) {
        return original.set(index, element);
    }

    @Override
    public @NotNull List<E> subList(int fromIndex, int toIndex) {
        return original.subList(fromIndex, toIndex);
    }

    @Override
    public int size() {
        return original.size();
    }

    @Override
    public E remove(int index) {
        return original.remove(index);
    }

    @Override
    public <T> T @NotNull [] toArray(T @NotNull [] array) {
        Validate.Arg.notNull(array, "array");
        return original.toArray(array);
    }

    @Override
    public Object @NotNull [] toArray() {
        return original.toArray();
    }
}
