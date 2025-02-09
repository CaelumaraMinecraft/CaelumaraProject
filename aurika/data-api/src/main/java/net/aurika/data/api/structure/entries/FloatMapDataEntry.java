package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public class FloatMapDataEntry extends MapDataEntry {
    private final float value;

    protected FloatMapDataEntry(@NotNull String name, float value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataMetaType type() {
        return DataMetaType.FLOAT;
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
