package net.aurika.data.api.bundles.entries;

import net.aurika.checker.Checker;
import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import org.jetbrains.annotations.NotNull;

public class StringEntry extends SimpleMappingDataEntry {
    private final @NotNull String value;

    protected StringEntry(@NotNull String name, @NotNull String value) {
        super(name);
        Checker.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull DataScalarType type() {
        return DataScalarType.STRING;
    }

    @Override
    public @NotNull String valueAsString() {
        return value;
    }

    @Override
    public @NotNull String valueAsObject() {
        return value;
    }

    public @NotNull String getValue() {
        return value;
    }
}
