package net.aurika.data.api.bundles.scalars;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LongDataScalar extends DataScalar {
    private final long value;

    public LongDataScalar(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.LONG;
    }

    @Override
    public @NotNull Long valueAsObject() {
        return value;
    }

    @Override
    public @NotNull String valueToString() {
        return Long.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LongDataScalar that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
