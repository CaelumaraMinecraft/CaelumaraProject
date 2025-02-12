package net.aurika.data.api.bundles.scalars;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BooleanDataScalar extends DataScalar {
    public static final BooleanDataScalar TRUE = new BooleanDataScalar(true);
    public static final BooleanDataScalar FALSE = new BooleanDataScalar(false);

    private final boolean value;

    protected BooleanDataScalar(boolean value) {
        this.value = value;
    }

    public boolean value() {
        return value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.BOOLEAN;
    }

    @Override
    public @NotNull Boolean valueAsObject() {
        return value;
    }

    @Override
    public @NotNull String valueToString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BooleanDataScalar that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
