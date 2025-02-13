package net.aurika.ecliptor.api.structured.scalars;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DoubleDataScalar extends DataScalar {
    private final double value;

    public DoubleDataScalar(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.FLOAT;
    }

    @Override
    public @NotNull Double valueAsObject() {
        return value;
    }

    @Override
    public @NotNull String valueToString() {
        return Double.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DoubleDataScalar that)) return false;
        return Double.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
