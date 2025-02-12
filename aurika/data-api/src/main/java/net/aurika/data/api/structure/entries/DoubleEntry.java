package net.aurika.data.api.structure.entries;

import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class DoubleEntry extends SimpleMappingDataEntry {
    private final double value;

    protected DoubleEntry(@NotNull String name, double value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.DOUBLE;
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
