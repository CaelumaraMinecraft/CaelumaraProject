package net.aurika.ecliptor.api.structured.scalars;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IntDataScalar extends DataScalar {
    private final int value;

    public IntDataScalar(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.INT;
    }

    @Override
    public @NotNull Integer valueAsObject() {
        return value;
    }

    @Override
    public @NotNull String valueToString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntDataScalar that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
