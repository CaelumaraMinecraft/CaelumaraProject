package net.aurika.util.collection;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public final class CollectionUtils {
    public static int sizeOfIterator(@NotNull Iterator<?> iterator) {
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static <T> @Nullable T getLast(@NotNull List<T> list) {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    @Contract("_, _, _, _ -> param2")
    public static <T> List<T> typeFilter(@NotNull List<?> list, List<T> to, Class<T> elementType, boolean nonNulElement) {
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
    public static <T> List<T> typeFilter(@NotNull List<?> list, Class<T> elementType, boolean nonNulElement) {
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
}
