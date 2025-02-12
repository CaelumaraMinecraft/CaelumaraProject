package net.aurika.data.api.bundles.entries;

import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class FloatEntry extends SimpleMappingDataEntry {
    private final float value;

    protected FloatEntry(@NotNull String name, float value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.FLOAT;
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
