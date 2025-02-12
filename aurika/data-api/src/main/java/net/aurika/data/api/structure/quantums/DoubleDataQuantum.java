package net.aurika.data.api.structure.quantums;

import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DoubleDataQuantum extends DataQuantum {
    private final double value;

    public DoubleDataQuantum(double value) {
        this.value = value;
    }

    public double value() {
        return value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.FLOAT;
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
        if (!(o instanceof DoubleDataQuantum that)) return false;
        return Double.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
