package net.aurika.ecliptor.api.structured.scalars;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FloatDataScalar extends DataScalar {
    private final float value;

    public FloatDataScalar(float value) {
        this.value = value;
    }

    public float value() {
        return value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.FLOAT;
    }

    @Override
    public @NotNull Float valueAsObject() {
        return value;
    }

    @Override
    public @NotNull String valueToString() {
        return Float.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FloatDataScalar that)) return false;
        return Float.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
