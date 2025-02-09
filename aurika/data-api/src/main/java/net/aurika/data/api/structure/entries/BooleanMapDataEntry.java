package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public class BooleanMapDataEntry extends MapDataEntry {
    private final boolean value;

    public BooleanMapDataEntry(@NotNull String key, boolean value) {
        super(key);
        this.value = value;
    }

    @Override
    public @NotNull DataMetaType type() {
        return DataMetaType.BOOLEAN;
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
