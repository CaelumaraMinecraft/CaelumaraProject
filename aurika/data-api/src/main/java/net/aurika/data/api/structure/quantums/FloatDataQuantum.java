package net.aurika.data.api.structure.quantums;

import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FloatDataQuantum extends DataQuantum {
    private final float value;

    public FloatDataQuantum(float value) {
        this.value = value;
    }

    public float value() {
        return value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.FLOAT;
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
        if (!(o instanceof FloatDataQuantum that)) return false;
        return Float.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
