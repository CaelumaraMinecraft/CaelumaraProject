package net.aurika.data.api.bundles.entries;

import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class LongEntry extends SimpleMappingDataEntry {
    private final long value;

    protected LongEntry(@NotNull String name, long value) {
        super(name);
        this.value = value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.LONG;
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
