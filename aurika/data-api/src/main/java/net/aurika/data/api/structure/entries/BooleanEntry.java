package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class BooleanEntry extends SimpleMappingDataEntry {
    private final boolean value;

    public BooleanEntry(@NotNull String key, boolean value) {
        super(key);
        this.value = value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.BOOLEAN;
    }

    @Override
    public @NotNull String valueAsString() {
        return String.valueOf(value);
    }

    @Override
    public @NotNull Boolean valueAsObject() {
        return value;
    }

    public boolean getValue() {
        return value;
    }
}
