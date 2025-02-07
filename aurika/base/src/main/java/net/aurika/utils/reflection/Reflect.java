package net.aurika.utils.reflection;


import net.aurika.utils.arrays.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public final class Reflect {
    private Reflect() {
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className, false, Reflect.class.getClassLoader());
            return true;
        } catch (NoClassDefFoundError | ClassNotFoundException ex) {
            return false;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return true;
        }
    }

    public static Field getDeclaredField(Class<?> clazz, String... names) throws NoSuchFieldException {
        NoSuchFieldException error = null;
        int var5 = 0;

        while (var5 < names.length) {
            String name = names[var5];

            try {
                return clazz.getDeclaredField(name);
            } catch (NoSuchFieldException var8) {
                if (error == null) {
                    error = new NoSuchFieldException("Couldn't find any of the fields " + Arrays.toString(names) + " in class: " + clazz);
                }

                error.addSuppressed(var8);
                ++var5;
            }
        }

        throw error;
    }

    public static Class<?>[] getClassHierarchy(Class<?> clazz, boolean allowAnonymous) {
        List<Class<?>> classes = new ArrayList<>();

        for (Class<?> lastUpperClass = clazz; (lastUpperClass = allowAnonymous ? lastUpperClass.getEnclosingClass() : lastUpperClass.getDeclaringClass()) != null; classes.add(lastUpperClass)) {
            if (classes.isEmpty()) {
                classes.add(clazz);
            }
        }

        if (classes.isEmpty()) {
            return new Class[]{clazz};
        } else {
            Class<?>[] reversed = classes.toArray(new Class[0]);
            ArrayUtils.reverse(reversed);
            return reversed;
        }
    }

    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?>[] var2 = getClassHierarchy(clazz, false);

        for (Class<?> hierarchy : var2) {
            fields.addAll(Arrays.asList(hierarchy.getDeclaredFields()));
        }

        return fields;
    }

    public static String toString(Object obj) {
        Class<?> clazz = obj.getClass();
        List<Field> fields = getFields(clazz);
        StringBuilder string = (new StringBuilder(clazz.getSimpleName())).append('{');
        StringJoiner joiner = new StringJoiner(", ");

        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);

                try {
                    joiner.add(field.getName() + '=' + field.get(obj));
                } catch (IllegalAccessException var8) {
                    throw new RuntimeException(var8);
                }
            }
        }

        return string.append(joiner).append('}').toString();
    }
}
