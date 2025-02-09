package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public class LongMapDataEntry extends MapDataEntry {
    private final long value;

    protected LongMapDataEntry(@NotNull String name, long value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataMetaType type() {
        return DataMetaType.LONG;
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
