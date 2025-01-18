package top.auspice.utils.number;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractAnyNumber implements AnyNumber {
    public AbstractAnyNumber() {
    }

    public final boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof AnyNumber && (this.getValue().equals(((AnyNumber) other).getValue()));
        }
    }

    public final int hashCode() {
        return this.getValue().hashCode();
    }

    @NotNull
    public final String getAsString() {
        return this.getValue().toString();
    }

    @NotNull
    public final String asDataString() {
        return this.getAsString();
    }

    @NotNull
    public final String toString() {
        return this.getType().name() + '(' + this.getValue() + ')';
    }
}
