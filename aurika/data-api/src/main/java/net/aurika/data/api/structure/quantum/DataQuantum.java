package net.aurika.data.api.structure.quantum;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataUnitType;
import org.jetbrains.annotations.NotNull;

public abstract class DataQuantum {
    public static IntDataQuantum intDataQuantum(int value) {
        return new IntDataQuantum(value);
    }

    public static LongDataQuantum longDataQuantum(long value) {
        return new LongDataQuantum(value);
    }

    public static FloatDataQuantum floatDataQuantum(float value) {
        return new FloatDataQuantum(value);
    }

    public static DoubleDataQuantum doubleDataQuantum(double value) {
        return new DoubleDataQuantum(value);
    }

    public static BooleanDataQuantum booleanDataQuantum(boolean value) {
        return value ? BooleanDataQuantum.TRUE : BooleanDataQuantum.FALSE;
    }

    public static StringDataQuantum stringDataQuantum(@NotNull String value) {
        return new StringDataQuantum(value);
    }

    public static StringDataQuantum stringDataQuantum(@NotNull CharSequence value) {
        Checker.Arg.notNull(value, "value");
        return new StringDataQuantum(value.toString());
    }

    public abstract @NotNull DataUnitType type();

    public abstract @NotNull Object valueAsObject();

    public abstract @NotNull String valueToString();
}
