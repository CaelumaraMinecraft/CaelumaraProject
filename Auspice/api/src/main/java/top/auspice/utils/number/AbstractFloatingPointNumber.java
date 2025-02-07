package top.auspice.utils.number;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class AbstractFloatingPointNumber extends AbstractAnyNumber implements FloatingPointNumber {
    public AbstractFloatingPointNumber() {
    }

    protected final void requireFinite(boolean requirement, @NotNull Supplier<String> lazyMessage) {
        Objects.requireNonNull(lazyMessage, "lazyMessage");
        if (!requirement) {
            throw new NonFiniteNumberException(lazyMessage.get());
        }
    }
}