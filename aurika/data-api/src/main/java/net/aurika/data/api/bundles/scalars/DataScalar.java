package net.aurika.data.api.bundles.scalars;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

public abstract class DataScalar {
    public static IntDataScalar intDataScalar(int value) {
        return new IntDataScalar(value);
    }

    public static LongDataScalar longDataScalar(long value) {
        return new LongDataScalar(value);
    }

    public static FloatDataScalar floatDataScalar(float value) {
        return new FloatDataScalar(value);
    }

    public static DoubleDataScalar doubleDataScalar(double value) {
        return new DoubleDataScalar(value);
    }

    public static BooleanDataScalar booleanDataScalar(boolean value) {
        return value ? BooleanDataScalar.TRUE : BooleanDataScalar.FALSE;
    }

    public static StringDataScalar stringDataScalar(@NotNull String value) {
        return new StringDataScalar(value);
    }

    public static StringDataScalar stringDataScalar(@NotNull CharSequence value) {
        Checker.Arg.notNull(value, "value");
        return new StringDataScalar(value.toString());
    }

    public abstract @NotNull DataScalarType type();

    public abstract @NotNull Object valueAsObject();

    public abstract @NotNull String valueToString();
}
