package net.aurika.util.collection.nonnull;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class NonNullSupplier<T> implements Supplier<T> {

    private final @NotNull T value;

    public NonNullSupplier(@NotNull T value) {
        Checker.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull T get() {
        return this.value;
    }
}
