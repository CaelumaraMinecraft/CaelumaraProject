package net.aurika.data.api.structure.quantum;

import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class LongDataQuantum extends DataQuantum {
    private final long value;

    public LongDataQuantum(long value) {
        this.value = value;
    }

    public long value() {
        return value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.LONG;
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
        if (!(o instanceof LongDataQuantum that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
