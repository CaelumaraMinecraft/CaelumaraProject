package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class LongEntry extends SimpleMappingDataEntry {
    private final long value;

    protected LongEntry(@NotNull String name, long value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.LONG;
    }

    @Override
    public @NotNull String valueAsString() {
        return String.valueOf(value);
    }

    @Override
    public @NotNull Long valueAsObject() {
        return value;
    }

    public long getValue() {
        return value;
    }
}
