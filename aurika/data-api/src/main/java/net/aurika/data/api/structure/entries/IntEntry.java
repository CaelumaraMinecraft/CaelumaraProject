package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class IntEntry extends SimpleMappingDataEntry {
    private final int value;

    protected IntEntry(@NotNull String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.INT;
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
