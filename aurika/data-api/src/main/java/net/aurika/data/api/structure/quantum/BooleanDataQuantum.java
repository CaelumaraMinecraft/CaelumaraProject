package net.aurika.data.api.structure.quantum;

import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BooleanDataQuantum extends DataQuantum {
    public static final BooleanDataQuantum TRUE = new BooleanDataQuantum(true);
    public static final BooleanDataQuantum FALSE = new BooleanDataQuantum(false);

    private final boolean value;

    protected BooleanDataQuantum(boolean value) {
        this.value = value;
    }

    public boolean value() {
        return value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.BOOLEAN;
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
        if (!(o instanceof BooleanDataQuantum that)) return false;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
