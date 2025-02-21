package net.aurika.ecliptor.api.structured.scalars;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public abstract class DataScalar {
    @Contract("_ -> new")
    public static @NotNull IntDataScalar intDataScalar(int value) {
        return new IntDataScalar(value);
    }

    @Contract("_ -> new")
    public static @NotNull LongDataScalar longDataScalar(long value) {
        return new LongDataScalar(value);
    }

    @Contract("_ -> new")
    public static @NotNull FloatDataScalar floatDataScalar(float value) {
        return new FloatDataScalar(value);
    }

    @Contract("_ -> new")
    public static @NotNull DoubleDataScalar doubleDataScalar(double value) {
        return new DoubleDataScalar(value);
    }

    public static @NotNull BooleanDataScalar booleanDataScalar(boolean value) {
        return value ? BooleanDataScalar.TRUE : BooleanDataScalar.FALSE;
    }

    @Contract("_ -> new")
    public static @NotNull StringDataScalar stringDataScalar(@NotNull String value) {
        return new StringDataScalar(value);
    }

    @Contract("_ -> new")
    public static @NotNull StringDataScalar stringDataScalar(@NotNull CharSequence value) {
        Validate.Arg.notNull(value, "value");
        return new StringDataScalar(value.toString());
    }

    public abstract @NotNull DataScalarType type();

    public abstract @NotNull Object valueAsObject();

    public abstract @NotNull String valueToString();
}
