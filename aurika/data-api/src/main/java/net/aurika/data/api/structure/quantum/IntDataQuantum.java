package net.aurika.data.api.structure.quantum;

import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class IntDataQuantum extends DataQuantum {
    private final int value;

    public IntDataQuantum(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.INT;
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
        if (!(o instanceof IntDataQuantum that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
