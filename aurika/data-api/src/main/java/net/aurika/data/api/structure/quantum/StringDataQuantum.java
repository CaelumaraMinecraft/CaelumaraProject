package net.aurika.data.api.structure.quantum;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StringDataQuantum extends DataQuantum {
    private final @NotNull String value;

    public StringDataQuantum(@NotNull String value) {
        Checker.Arg.notNull(value, "value");
        this.value = value;
    }

    public @NotNull String value() {
        return value;
    }

    @Override
    public @NotNull DataUnitType type() {
        return DataUnitType.STRING;
    }

    @Override
    public @NotNull String valueAsObject() {
        return value;
    }

    @Override
    public @NotNull String valueToString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StringDataQuantum that)) return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
