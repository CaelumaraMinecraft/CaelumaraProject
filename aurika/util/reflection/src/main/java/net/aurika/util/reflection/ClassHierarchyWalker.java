package net.aurika.util.reflection;

import java.util.Objects;
import java.util.function.Function;

public final class ClassHierarchyWalker<V> {
    private final Class<?> base;
    private final Function<Class<?>, V> find;

    private ClassHierarchyWalker(Class<?> base, Function<Class<?>, V> find) {
        this.base = Objects.requireNonNull(base);
        this.find = Objects.requireNonNull(find);
        if (base == Class.class) {
            throw new IllegalArgumentException("Recursive class parameter: " + base);
        }
    }

    private V check(Class<?> check) {
        if (check == Object.class) {
            return null;
        } else {
            V found = this.find.apply(check);
            if (found != null) {
                return found;
            } else {
                Class<?> superClass = check.getSuperclass();
                if (superClass != null) {
                    V any = this.check(superClass);
                    if (any != null) {
                        return any;
                    }
                }

                Class<?>[] var9 = check.getInterfaces();

                for (Class<?> interfacee : var9) {
                    V any = this.check(interfacee);
                    if (any != null) {
                        return any;
                    }
                }

                return null;
            }
        }
    }

    public V find() {
        return this.check(this.base);
    }

    public static <V> V walk(Class<?> base, Function<Class<?>, V> find) {
        return (new ClassHierarchyWalker<>(base, find)).find();
    }
}
