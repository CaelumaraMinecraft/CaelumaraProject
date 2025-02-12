package net.aurika.data.api.bundles.entries;

import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class IntEntry extends SimpleMappingDataEntry {
    private final int value;

    protected IntEntry(@NotNull String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.INT;
    }

    @Override
    public @NotNull String valueAsString() {
        return String.valueOf(value);
    }

    @Override
    public @NotNull Integer valueAsObject() {
        return value;
    }

    public int getValue() {
        return value;
    }
}
