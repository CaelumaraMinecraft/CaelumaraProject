package top.auspice.utils.nonnull;

import org.jetbrains.annotations.NotNull;
import net.aurika.utils.Checker;

import java.util.function.Supplier;

public class NonNullConstantSupplier<T> implements Supplier<T> {

    private final T value;

    public NonNullConstantSupplier(T value) {
        Checker.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull T get() {
        return this.value;
    }
}
