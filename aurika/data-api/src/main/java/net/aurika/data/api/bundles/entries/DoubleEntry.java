package net.aurika.data.api.bundles.entries;

import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class DoubleEntry extends SimpleMappingDataEntry {
    private final double value;

    protected DoubleEntry(@NotNull String name, double value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.DOUBLE;
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
