package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public class DoubleMapDataEntry extends MapDataEntry {
    private final double value;

    protected DoubleMapDataEntry(@NotNull String name, double value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataMetaType type() {
        return DataMetaType.DOUBLE;
    }

    @Override
    public @NotNull String valueAsString() {
        return String.valueOf(value);
    }

    @Override
    public @NotNull Double valueAsObject() {
        return value;
    }

    public double getValue() {
        return value;
    }
}
