package net.aurika.auspice.utils.unsafe.functional;

import java.util.function.Supplier;

@FunctionalInterface
public interface SecondarySupplier<T> extends Supplier<T> {
    T get();
}

