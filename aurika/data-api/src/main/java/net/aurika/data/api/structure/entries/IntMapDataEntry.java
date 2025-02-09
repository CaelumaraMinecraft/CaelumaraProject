package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public class IntMapDataEntry extends MapDataEntry {
    private final int value;

    protected IntMapDataEntry(@NotNull String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataMetaType type() {
        return DataMetaType.INT;
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
