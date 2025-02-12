package net.aurika.util.array;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings("unchecked")
public final class ArrayUtils {
    @Contract(value = "_ -> param1", pure = true)
    public static <T> T @NotNull [] reverse(T @NotNull [] array) {
        Checker.Arg.notNull(array, "array");
        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            T tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
        return array;
    }

    @Contract("_, _ -> param1")
    public static <T> T[] require(T @NotNull [] array, @NotNull Predicate<T> requirement) {
        for (T element : array) {
            if (!requirement.test(element)) {
                throw new IllegalStateException("Array element did not pass requirement");
            }
        }
        return array;
    }

    public static String @NotNull [] mergeObjects(Object @NotNull ... objects) {
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

    public static <T> T[] merge(T @NotNull [] array1, T @NotNull [] array2) {
        Checker.Arg.notNull(array1, "array1");
        Checker.Arg.notNull(array2, "array2");
        T[] joinedArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static Object @NotNull [] shift(Object @NotNull [] array) {
        Object[] result = new Object[array.length - 1];
        System.arraycopy(array, 1, result, 0, result.length);
        return result;
    }

    public static <T> void shuffle(T @NotNull [] array) {
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

        public T @NotNull [] toArray(T[] a) {
            return collection.toArray(a);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T @NotNull [] malloc(@NotNull T @NotNull [] initial, @Range(from = 1, to = Integer.MAX_VALUE) int length) {
        if (length <= initial.length) throw new IllegalArgumentException(
                "Cannot allocate array with the same or smaller size than the initial array " + length + " <= " + initial.length);

        T[] arr = (T[]) Array.newInstance(initial.getClass().getComponentType(), length);
        System.arraycopy(initial, 0, arr, 0, initial.length);
        return arr;
    }

    // ========

    @Contract("_, _ -> new")
    public static <T> T[] removePre(T @NotNull [] array, int count) {
        Checker.Arg.notNull(array, "array");
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

    public static <T> int @NotNull [] findNullIndexes(T @NotNull [] array) {
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
