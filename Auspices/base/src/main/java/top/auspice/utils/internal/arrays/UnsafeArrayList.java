package top.auspice.utils.internal.arrays;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;

public final class UnsafeArrayList<E> extends AbstractCollection<E> implements List<E>, RandomAccess {
    private static final int DEFAULT_CAPACITY = 10;
    private transient E[] array;
    public int size;

    private UnsafeArrayList() {
    }

    private UnsafeArrayList(E[] array) {
        this.array = array;
        this.size = array.length;
    }

    public static <E> UnsafeArrayList<E> withSize(E[] array) {
        UnsafeArrayList<E> list = new UnsafeArrayList<>();
        list.array = array;
        return list;
    }

    @SafeVarargs
    public static <E> UnsafeArrayList<E> of(E... elements) {
        return new UnsafeArrayList<>(elements);
    }

    @SafeVarargs
    public static <E> UnsafeArrayList<E> copyOf(E... elements) {
        return new UnsafeArrayList<>(Arrays.copyOf(elements, elements.length));
    }

    public String toString() {
        if (this.isEmpty()) {
            return "UnsafeArrayList:[]";
        } else {
            int iMax = this.size - 1;
            StringBuilder builder = new StringBuilder(20 + this.size * 5);
            builder.append("UnsafeArrayList:[");
            int i = 0;

            while (true) {
                builder.append(this.array[i]);
                if (i == iMax) {
                    return builder.append(']').toString();
                }

                builder.append(", ");
                ++i;
            }
        }
    }

    public void grow() {
        this.grow(this.size + 1);
    }

    public int size() {
        return this.size;
    }

    public void resetPointer() {
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean contains(Object o) {
        return this.indexOf(o) != -1;
    }

    public @NonNull Iterator<E> iterator() {
        return new Itr(0);
    }

    public int indexOf(Object object) {
        return this.indexOfRange(object, 0, this.size);
    }

    public int indexOfRange(Object object, int start, int end) {
        for (E[] array = this.array; start < end; ++start) {
            if (object.equals(array[start])) {
                return start;
            }
        }

        return -1;
    }

    public void setArray(E[] elements) {
        this.array = elements;
        this.size = elements.length;
    }

    public E[] getArray() {
        return this.array;
    }

    public int lastIndexOf(Object o) {
        return this.lastIndexOfRange(o, 0, this.size);
    }

    public @NonNull ListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    public @NonNull ListIterator<E> listIterator(int index) {
        return new Itr(index);
    }

    public @NonNull List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public int lastIndexOfRange(Object object, int start, int end) {
        E[] array = this.array;

        do {
            --end;
            if (end < start) {
                return -1;
            }
        } while (!object.equals(array[end]));

        return end;
    }

    public E @NotNull [] toArray() {
        return Arrays.copyOf(this.array, this.size);
    }

    public <T> @NotNull T @NotNull [] toArray(T @NotNull [] a) {
        if (a.length < this.size) {
            return (T[]) Arrays.copyOf(this.array, this.size, a.getClass());
        } else {
            System.arraycopy(this.array, 0, a, 0, this.size);
            if (a.length > this.size) {
                a[this.size] = null;
            }

            return a;
        }
    }

    public E get(int index) {
        return this.array[index];
    }

    public E set(int index, E element) {
        E oldValue = this.array[index];
        this.array[index] = Objects.requireNonNull(element, "Cannot add null object");
        return oldValue;
    }

    public void add(int index, E element) {
        if (this.size == this.array.length) {
            this.grow();
        }

        System.arraycopy(this.array, index, this.array, index + 1, this.size - index);
        this.array[index] = Objects.requireNonNull(element, "Cannot add null object");
        ++this.size;
    }

    public E remove(int index) {
        E oldValue = this.array[index];
        this.fastRemove(index);
        return oldValue;
    }

    public boolean add(E element) {
        if (this.size == this.array.length) {
            this.grow();
        }

        this.array[this.size++] = Objects.requireNonNull(element, "Cannot add null object");
        return true;
    }

    public boolean batchRemove(Collection<?> c, boolean complement, int from, int end) {
        for (E[] array = this.array; from != end; ++from) {
            if (c.contains(array[from]) != complement) {
                int w = from++;

                try {
                    for (; from < end; ++from) {
                        E e = array[from];
                        if (c.contains(e) == complement) {
                            array[w++] = e;
                        }
                    }
                } catch (Throwable var11) {
                    System.arraycopy(array, from, array, w, end - from);
                    w += end - from;
                    throw var11;
                } finally {
                    this.shiftTailOverGap(array, w, end);
                }

                return true;
            }
        }

        return false;
    }

    public void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, this.size - hi);
        int to = this.size;

        for (int i = this.size -= hi - lo; i < to; ++i) {
            es[i] = null;
        }

    }

    public int hashCode() {
        int hashCode = 1;
        E[] array = this.array;

        for (E element : array) {
            hashCode = 31 * hashCode + element.hashCode();
        }

        return hashCode;
    }

    public boolean remove(Object obj) {
        E[] array = this.array;

        for (int i = 0; i < this.size; ++i) {
            if (obj.equals(array[i])) {
                this.fastRemove(i);
                return true;
            }
        }

        return false;
    }

    public void fastRemove(int i) {
        if (--this.size > i) {
            System.arraycopy(this.array, i + 1, this.array, i, this.size - i);
        }

        this.array[this.size] = null;
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        Iterator<?> var2 = collection.iterator();

        Object e;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            e = var2.next();
        } while (this.contains(e));

        return false;
    }

    public void clear() {
        Arrays.fill(this.array, (Object) null);
        this.size = 0;
    }

    public boolean addAll(Collection<? extends E> c) {
        return this.addAll((E[]) c.toArray(new Object[0]));
    }

    public boolean addAll(E[] e) {
        int len = e.length;
        if (len == 0) {
            return false;
        } else {
            if (this.size + len > this.array.length) {
                this.grow(this.size + len);
            }

            System.arraycopy(e, 0, this.array, this.size, len);
            this.size += len;
            return true;
        }
    }

    public void grow(int minCapacity) {
        this.array = Arrays.copyOf(this.array, this.newCapacity(minCapacity));
    }

    public int newCapacity(int minCapacity) {
        int currentCapacity = this.array.length;
        int newCapacity = currentCapacity + (currentCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (currentCapacity == 0) {
                return Math.max(10, minCapacity);
            } else if (minCapacity < 0) {
                throw new OutOfMemoryError();
            } else {
                return minCapacity;
            }
        } else {
            return newCapacity;
        }
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        } else {
            if (numNew > this.array.length - this.size) {
                this.grow(this.size + numNew);
            }

            int numMoved = this.size - index;
            if (numMoved > 0) {
                System.arraycopy(this.array, index, this.array, index + numNew, numMoved);
            }

            System.arraycopy(a, 0, this.array, index, numNew);
            this.size += numNew;
            return true;
        }
    }

    public boolean removeAll(@NonNull Collection<?> c) {
        return this.batchRemove(c, false, 0, this.size);
    }

    public boolean retainAll(@NonNull Collection<?> c) {
        return this.batchRemove(c, true, 0, this.size);
    }

    public void forEach(@NonNull Consumer<? super E> action) {
        E[] var2 = this.array;

        for (E element : var2) {
            action.accept(element);
        }

    }

    private class Itr implements ListIterator<E> {
        public int cursor;

        Itr(int cursor) {
            this.cursor = cursor;
        }

        public boolean hasNext() {
            return this.cursor != UnsafeArrayList.this.size;
        }

        public E next() {
            return UnsafeArrayList.this.array[this.cursor++];
        }

        public void remove() {
            UnsafeArrayList.this.remove(this.cursor - 1);
        }

        public void forEachRemaining(Consumer<? super E> action) {
            int size = UnsafeArrayList.this.size;
            if (this.cursor < size) {
                while (this.cursor < size) {
                    action.accept(UnsafeArrayList.this.array[this.cursor++]);
                }
            }

        }

        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        public int nextIndex() {
            return this.cursor;
        }

        public int previousIndex() {
            return this.cursor - 1;
        }

        public E previous() {
            return UnsafeArrayList.this.array[--this.cursor];
        }

        public void set(E e) {
            UnsafeArrayList.this.set(this.cursor - 1, e);
        }

        public void add(E e) {
            UnsafeArrayList.this.add(this.cursor++, e);
        }
    }
}
