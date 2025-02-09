package net.aurika.utils.cache.single;

import java.util.Objects;
import java.util.function.Supplier;

public interface CacheableObject<T> extends Supplier<T> {
    void invalidate();

    boolean isCached();

    default boolean contains(T other) {
        return Objects.equals(this.get(), other);
    }

    default boolean isNull() {
        return this.get() == null;
    }

    default boolean isPresent() {
        return this.get() != null;
    }

    T get();

    void set(T var1);
}