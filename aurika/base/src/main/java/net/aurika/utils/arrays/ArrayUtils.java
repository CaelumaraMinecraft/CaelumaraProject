package net.aurika.utils.arrays;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.*;

public final class ArrayUtils {

    @Contract("_, _ -> new")
    public static <T> T[] removePre(T[] oldArray, int count) {
        if (count > oldArray.length) {
            throw new IllegalArgumentException("Count is better than array length: " + count + " > " + Arrays.toString(oldArray) + ".length");
        }
        T[] newArray = (T[]) Array.newInstance(oldArray.getClass().getComponentType(), oldArray.length - count);
        System.arraycopy(oldArray, count, newArray, 0, oldArray.length - count);
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

    public static <T> void reverse(T @NotNull [] array) {
        int var1 = 0;

        for (int var2 = array.length - 1; var2 > var1; --var2) {
            T var3 = array[var2];
            array[var2] = array[var1];
            array[var1] = var3;
            ++var1;
        }
    }

    public static int sizeOfIterator(Iterator<?> iterator) {
        int size;
        for (size = 0; iterator.hasNext(); ++size) {
            iterator.next();
        }

        return size;
    }

    public static <T> T getLast(List<T> list) {
        return list.isEmpty() ? null : list.getLast();
    }

    public static String[] mergeObjects(Object... objects) {
        ArrayList<String> var1 = new ArrayList<>(objects.length << 1);

        for (Object object : objects) {
            if (object instanceof Object[] var8) {
                for (Object var7 : var8) {
                    var1.add(var7.toString());
                }
            } else {
                var1.add(object.toString());
            }
        }

        return var1.toArray(new String[0]);
    }

    @Contract("_, _ -> new")
    public static String[] merge(String[] first, String[] second) {
        String[] merged = new String[first.length + second.length];
        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);
        return merged;
    }

    @Contract("_, _ -> new")
    public static <T> T[] merge(T[] first, T[] second) {
        Class<?> type = first.getClass().getComponentType();
        T[] merged = (T[]) Array.newInstance(type, first.length + second.length);

        System.arraycopy(first, 0, merged, 0, first.length);
        System.arraycopy(second, 0, merged, first.length, second.length);
        return merged;
    }

    public static Object[] shift(Object[] array) {
        Object[] var1 = new Object[array.length - 1];
        System.arraycopy(array, 1, var1, 0, var1.length);
        return var1;
    }

    public static <T> ConditionalBuilder<T> when(boolean var0, T var1) {
        return (new ConditionalBuilder<T>(new ArrayList<>())).when(var0, var1);
    }

    public static <T> T[] malloc(@NonNull T[] array, int count) {
        if (count <= array.length) {
            throw new IllegalArgumentException("Cannot allocate array with the same or smaller size than the initial array " + count + " <= " + array.length);
        } else {
            T[] newArr = (T[]) Array.newInstance(array.getClass().getComponentType(), count);
            System.arraycopy(array, 0, newArr, 0, array.length);
            return newArr;
        }
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
        for (Object e : array) {
            if (e.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public static final class ConditionalBuilder<T> {
        private final Collection<T> collection;

        private ConditionalBuilder(Collection<T> var1) {
            this.collection = var1;
        }

        public ConditionalBuilder<T> when(boolean var1, T var2) {
            if (var1) {
                this.collection.add(var2);
            }

            return this;
        }

        public Collection<T> build() {
            return this.collection;
        }

        public T[] toArray(T[] var1) {
            return this.collection.toArray(var1);
        }
    }
}
