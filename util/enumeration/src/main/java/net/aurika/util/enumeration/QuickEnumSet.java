package net.aurika.util.enumeration;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

@SuppressWarnings("unused")
public class QuickEnumSet<E extends Enum<E>> extends AbstractSet<E> {
    private final transient E[] universe;
    private final transient boolean[] elements;
    private transient int size;
    private transient int modCount;

    public QuickEnumSet(E[] universe) {
        this.universe = universe;
        this.elements = new boolean[universe.length];
    }

    public static <E extends Enum<E>> QuickEnumSet<E> allOf(E[] universe) {
        QuickEnumSet<E> set = new QuickEnumSet<>(universe);
        set.size = universe.length;

        for (int i = 0; i < universe.length; ++i) {
            set.elements[i] = true;
        }

        return set;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean contains(Object o) {
        return this.elements[((Enum<?>) o).ordinal()];
    }

    public @NonNull Iterator<E> iterator() {
        return new EnumSetIterator();
    }

    @NotNull
    public Object @NotNull [] toArray() {
        Object[] array = new Object[this.size];
        int index = 0;

        Enum<E> element;
        for (Iterator<E> iterator = this.iterator(); iterator.hasNext(); array[index++] = element) {
            element = iterator.next();
        }

        return array;
    }

    @NotNull
    public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
        @SuppressWarnings("unchecked") T[] array = a.length >= this.size ? a : (T[]) Array.newInstance(a.getClass().getComponentType(), this.size);
        int index = 0;

        E element;
        for (Iterator<E> var4 = this.iterator(); var4.hasNext(); //noinspection unchecked
             array[index++] = (T) element) {
            element = var4.next();
        }

        return array;
    }

    public boolean add(E e) {
        int ordinal = e.ordinal();
        boolean contained = this.elements[ordinal];
        if (contained) {
            return false;
        } else {
            this.elements[ordinal] = true;
            ++this.modCount;
            ++this.size;
            return true;
        }
    }

    public boolean remove(Object o) {
        int ordinal = ((Enum<?>) o).ordinal();
        boolean contained = this.elements[ordinal];
        if (contained) {
            this.elements[ordinal] = false;
            ++this.modCount;
            --this.size;
        }

        return contained;
    }

    public boolean containsAll(@NonNull Collection<?> c) {
        Iterator<?> iterator = c.iterator();

        Enum<?> a;
        do {
            if (!iterator.hasNext()) {
                return true;
            }

            a = (Enum<?>) iterator.next();
        } while (this.elements[a.ordinal()]);

        return false;
    }

    public boolean addAll(@NonNull Collection<? extends E> c) {
        ++this.modCount;
        boolean changed = false;

        for (E e : c) {
            boolean contained = this.elements[e.ordinal()];
            if (!contained) {
                changed = true;
                this.elements[e.ordinal()] = true;
                ++this.size;
            }
        }

        return changed;
    }

    @SafeVarargs
    public final QuickEnumSet<E> addAll(E... enums) {

        for (E e : enums) {
            this.elements[e.ordinal()] = true;
        }

        return this;
    }

    public boolean removeAll(@NonNull Collection<?> c) {
        ++this.modCount;
        boolean changed = false;

        for (Object o : c) {
            @SuppressWarnings("unchecked") E e = (E) o;
            boolean contained = this.elements[e.ordinal()];
            if (!contained) {
                changed = true;
                this.elements[e.ordinal()] = false;
                --this.size;
            }
        }

        return changed;
    }

    public void clear() {
        Arrays.fill(this.elements, false);
        ++this.modCount;
        this.size = 0;
    }

    private final class EnumSetIterator implements Iterator<E> {
        private int cursor;
        private int iterModCount;

        private EnumSetIterator() {
            this.iterModCount = QuickEnumSet.this.modCount;
        }

        public boolean hasNext() {
            this.checkModCount();

            while (this.cursor != QuickEnumSet.this.elements.length) {
                if (QuickEnumSet.this.elements[this.cursor]) {
                    return true;
                }

                ++this.cursor;
            }

            return false;
        }

        public E next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException("Size=" + QuickEnumSet.this.size);
            } else {
                return QuickEnumSet.this.universe[this.cursor++];
            }
        }

        void checkModCount() {
            if (this.iterModCount != QuickEnumSet.this.modCount) {
                throw new ConcurrentModificationException();
            }
        }

        public void remove() {
            this.checkModCount();
            if (QuickEnumSet.this.elements[this.cursor - 1]) {
                QuickEnumSet.this.elements[this.cursor - 1] = false;
                this.iterModCount = ++QuickEnumSet.this.modCount;
                QuickEnumSet.this.size--;
            }

        }
    }
}
