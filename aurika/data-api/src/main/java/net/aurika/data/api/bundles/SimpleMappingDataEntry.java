package net.aurika.data.api.bundles;

import net.aurika.checker.Checker;
import net.aurika.data.api.bundles.entries.*;
import net.aurika.data.api.bundles.scalars.DataScalarType;
import org.jetbrains.annotations.NotNull;

public abstract class SimpleMappingDataEntry {
    private final @NotNull String key;

    public static IntEntry of(@NotNull String key, int value) {
        return new IntEntry(key, value);
    }

    public static LongEntry of(@NotNull String key, long value) {
        return new LongEntry(key, value);
    }

    public static FloatEntry of(@NotNull String key, float value) {
        return new FloatEntry(key, value);
    }

    public static DoubleEntry of(@NotNull String key, double value) {
        return new DoubleEntry(key, value);
    }

    public static BooleanEntry of(@NotNull String key, boolean value) {
        return new BooleanEntry(key, value);
    }

    public static StringEntry of(@NotNull String key, @NotNull String value) {
        return new StringEntry(key, value);
    }

    public static StringEntry of(@NotNull String key, @NotNull CharSequence value) {
        Checker.Arg.notNull(value, "value");
        return new StringEntry(key, value.toString());
    }

    protected SimpleMappingDataEntry(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        this.key = key;
    }

    public @NotNull String key() {
        return key;
    }

    public abstract @NotNull DataScalarType type();

    public abstract @NotNull String valueAsString();

    public abstract @NotNull Object valueAsObject();

    public int intValue() {
        return ((IntEntry) this).getValue();
    }

    public long longValue() {
        return ((LongEntry) this).getValue();
    }

    public float floatValue() {
        return ((FloatEntry) this).getValue();
    }

    public double doubleValue() {
        return ((DoubleEntry) this).getValue();
    }

    public boolean booleanValue() {
        return ((BooleanEntry) this).getValue();
    }

    public @NotNull String stringValue() {
        return ((StringEntry) this).getValue();
    }
}
