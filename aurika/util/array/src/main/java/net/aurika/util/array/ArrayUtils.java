package net.aurika.util.array;

import net.aurika.checker.Checker;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.checkerframework.dataflow.qual.Pure;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;

public final class ArrayUtils {

    @Pure
    public static <T> T[] reverse(T[] array) {
        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            T tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
        return array;
    }

    public static <T> T[] require(T[] array, Predicate<T> requirement) {
        for (T element : array) {
            if (!requirement.test(element)) {
                throw new IllegalStateException("Array element did not pass requirement");
            }
        }
        return array;
    }

    public static int sizeOfIterator(@NotNull Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static <T> T getLast(List<T> list) {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    public static String[] mergeObjects(Object... objects) {
        List<String> list = new ArrayList<>(objects.length * 2);
        for (Object object : objects) {
            if (object instanceof Object[]) {
                for (Object o : ((Object[]) object)) {
                    list.add(o.toString());
                }
            } else list.add(object.toString());
        }

        return list.toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] merge(T[] array1, T[] array2) {
        T[] joinedArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static Object[] shift(Object[] array) {
        Object[] result = new Object[array.length - 1];
        System.arraycopy(array, 1, result, 0, result.length);
        return result;
    }

    public static <T> void shuffle(T[] array) {
        Random rnd = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);

            // Simple swap
            T a = array[index];
            array[index] = array[i];
            array[i] = a;
        }
    }

    public static <T> ConditionalBuilder<T> when(boolean cond, T item) {
        return new ConditionalBuilder<T>(new ArrayList<>()).when(cond, item);
    }

    public static final class ConditionalBuilder<T> {
        protected final Collection<T> collection;

        private ConditionalBuilder(Collection<T> collection) {
            this.collection = collection;
        }

        public ArrayUtils.ConditionalBuilder<T> when(boolean cond, T item) {
            if (cond) collection.add(item);
            return this;
        }

        public Collection<T> build() {
            return collection;
        }

        public T[] toArray(T[] a) {
            return collection.toArray(a);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] malloc(@NonNull T[] initial, @IntRange(from = 1, to = Integer.MAX_VALUE) int length) {
        if (length <= initial.length) throw new IllegalArgumentException(
                "Cannot allocate array with the same or smaller size than the initial array " + length + " <= " + initial.length);

        T[] arr = (T[]) Array.newInstance(initial.getClass().getComponentType(), length);
        System.arraycopy(initial, 0, arr, 0, initial.length);
        return arr;
    }

    // ========

    @Contract("_, _ -> new")
    public static <T> T[] removePre(T[] array, int count) {
        if (count > array.length) {
            throw new IllegalArgumentException("Count is better than array length: " + count + " > " + Arrays.toString(array) + ".length");
        }
        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), array.length - count);
        System.arraycopy(array, count, newArray, 0, array.length - count);
        return newArray;
    }

    public static <T, ST extends T> boolean startsWith(T[] array, ST[] prefix) {
        return startsWith(array, prefix, 0);
    }

    public static <T, ST extends T> boolean startsWith(T[] array, ST[] prefix, int offset) {
        if (offset < 0 || offset >= array.length) {
            return false;
        }
        if (prefix.length > array.length) {
            return false;
        }

        boolean s = true;
        for (int i = offset; i < prefix.length; i++) {
            if (!array[i].equals(prefix[i])) {
                s = false;
                break;
            }
        }

        return s;
    }

    @Contract("_, _ -> new")
    public static String[] merge(String @NotNull [] first, String @NotNull [] second) {
        Checker.Arg.notNull(first, "first");
        Checker.Arg.notNull(second, "second");
        String[] merged = new String[first.length + second.length];
        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);
        return merged;
    }

    public static Map<String, Object> stringToObjectSettings(Object[] settings) {
        HashMap<String, Object> var1 = new HashMap<>();
        if (settings.length >= 2) {
            for (int i = 0; i < settings.length; i++) {
                if (i % 2 == 0) {
                    var1.put(settings[i].toString(), settings[i + 1]);
                }
            }
        }
        return var1;
    }

    public static <T> int[] findNullIndexes(T[] array) {
        int nulls = 0;

        for (T t : array) {
            if (t == null) {
                nulls++;
            }
        }

        int[] indexes = new int[nulls];
        int count = 0;

        for (int i = 0; i < array.length; i++) {
            T t = array[i];
            if (t == null) {
                indexes[count] = i;
                count++;
            }
        }

        return indexes;
    }

    @Contract("_, _, _, _ -> param2")
    public static <T> List<T> typeFilter(List<?> list, List<T> to, Class<T> elementType, boolean nonNulElement) {
        for (Object e : list) {
            if (e == null) {
                if (nonNulElement) {
                    continue;
                }
            } else {
                if (!elementType.isInstance(e)) {
                    continue;
                }
            }

            to.add((T) e);
        }

        return to;
    }

    @SuppressWarnings("unchecked")
    @Contract("_, _, _ -> param1")
    public static <T> List<T> typeFilter(List<?> list, Class<T> elementType, boolean nonNulElement) {
        for (Object e : list) {
            if (e == null) {
                if (nonNulElement) {
                    list.remove(e);
                }
            } else {
                if (!elementType.isInstance(e)) {
                    list.remove(e);
                }
            }
        }

        return (List<T>) list;
    }

    public static boolean contains(Object @NotNull [] array, Object o) {
        Checker.Arg.notNull(array, "array");
        for (Object e : array) {
            if (Objects.equals(e, o)) {
                return true;
            }
        }
        return false;
    }
}
