package net.aurika.data.api.structure.entries;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public class StringMapDataEntry extends MapDataEntry {
    private final @NotNull String value;

    protected StringMapDataEntry(@NotNull String name, @NotNull String value) {
        super(name);
        Checker.Arg.notNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull DataMetaType type() {
        return DataMetaType.STRING;
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
