package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class FloatEntry extends SimpleMappingDataEntry {
    private final float value;

    protected FloatEntry(@NotNull String name, float value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.FLOAT;
    }

    @Override
    public @NotNull String valueAsString() {
        return String.valueOf(value);
    }

    @Override
    public @NotNull Float valueAsObject() {
        return value;
    }

    public float getValue() {
        return value;
    }
}
