package top.auspice.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class Validate {
    private Validate() {
    }

    public static boolean isTrue(boolean expression, String message, Object secondMessage) {
        if (!expression) {
            throw new IllegalArgumentException(message + secondMessage);
        }
        return expression;
    }

    public static boolean isTrue(boolean expression, String message, long secondMessage) {
        if (!expression) {
            throw new IllegalArgumentException(message + secondMessage);
        }
        return expression;
    }

    public static void isTrue(boolean expression, String message, double secondMessage) {
        if (!expression) {
            throw new IllegalArgumentException(message + secondMessage);
        }
    }

    public static boolean isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
        return expression;
    }

    public static boolean isTrue(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("The validated expression is false");
        }
        return expression;
    }

    public static <T> T notNull(T object) {
        return notNull(object, "The validated object is null");
    }

    public static <T> T notNull(T object, String message) {
        if (object == null) {
            throw new IllegalStateException(message);
        }
        return object;
    }

    public static Object[] notEmpty(Object[] objects) {
        return notEmpty(objects, "The validated array is empty");
    }

    public static Object[] notEmpty(Object[] objects, String message) {
        if (objects == null || objects.length == 0) {
            throw new IllegalArgumentException(message);
        }
        return objects;
    }

    public static void notEmpty(Collection<?> collection, String message) {
        if (collection == null || collection.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "The validated collection is empty");
    }

    public static Map<?, ?> notEmpty(Map<?, ?> map, String message) {
        if (map == null || map.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return map;
    }

    public static Map<?, ?> notEmpty(Map<?, ?> map) {
        return notEmpty(map, "The validated map is empty");
    }

    public static String notEmpty(String string, String message) {
        if (string != null && !string.isEmpty()) {
            return string;
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    public static String notEmpty(String string) {
        return notEmpty(string, "The validated string is empty");
    }

    public static void noNullElements(Object[] array, String message) {
        notNull(array);

        for (Object o : array) {
            if (o == null) {
                throw new IllegalArgumentException(message);
            }
        }

    }

    public static void noNullElements(Object[] array) {
        notNull(array);

        for (int index = 0; index < array.length; ++index) {
            if (array[index] == null) {
                throw new IllegalArgumentException("The validated array contains null element at index: " + index);
            }
        }

    }

    public static <T> void noNullElements(Collection<T> var0, String var1) {
        notNull(var0);
        Iterator<?> var3 = var0.iterator();

        do {
            if (!var3.hasNext()) {
                return;
            }
        } while (var3.next() != null);

        throw new IllegalArgumentException(var1);
    }

    public static void noNullElements(Collection<?> var0) {
        notNull(var0);
        int var1 = 0;

        for (Iterator<?> var2 = var0.iterator(); var2.hasNext(); ++var1) {
            if (var2.next() == null) {
                throw new IllegalArgumentException("The validated collection contains null element at index: " + var1);
            }
        }

    }

    public static void allElementsOfType(Collection<?> var0, Class<?> var1, String var2) {
        notNull(var0);
        notNull(var1);
        Iterator<?> var4 = var0.iterator();

        Object var3;
        do {
            if (!var4.hasNext()) {
                return;
            }

            var3 = var4.next();
        } while (var1.isInstance(var3));

        throw new IllegalArgumentException(var2);
    }

    public static void allElementsOfType(Collection<?> var0, Class<?> var1) {
        notNull(var0);
        notNull(var1);
        int var2 = 0;

        for (Iterator<?> var3 = var0.iterator(); var3.hasNext(); ++var2) {
            if (!var1.isInstance(var3.next())) {
                throw new IllegalArgumentException("The validated collection contains an element not of type " + var1.getName() + " at index: " + var2);
            }
        }

    }
}