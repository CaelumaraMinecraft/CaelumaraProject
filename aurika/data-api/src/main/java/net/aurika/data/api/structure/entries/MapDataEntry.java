package net.aurika.data.api.structure.entries;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataMetaType;
import org.jetbrains.annotations.NotNull;

public abstract class MapDataEntry {
    private final @NotNull String key;

    public static IntMapDataEntry of(@NotNull String key, int value) {
        return new IntMapDataEntry(key, value);
    }

    public static LongMapDataEntry of(@NotNull String key, long value) {
        return new LongMapDataEntry(key, value);
    }

    public static FloatMapDataEntry of(@NotNull String key, float value) {
        return new FloatMapDataEntry(key, value);
    }

    public static DoubleMapDataEntry of(@NotNull String key, double value) {
        return new DoubleMapDataEntry(key, value);
    }

    public static BooleanMapDataEntry of(@NotNull String key, boolean value) {
        return new BooleanMapDataEntry(key, value);
    }

    public static StringMapDataEntry of(@NotNull String key, @NotNull String value) {
        return new StringMapDataEntry(key, value);
    }

    public static StringMapDataEntry of(@NotNull String key, @NotNull CharSequence value) {
        return new StringMapDataEntry(key, value.toString());
    }

    protected MapDataEntry(@NotNull String key) {
        Checker.Arg.notNull(key, "key");
        this.key = key;
    }

    public @NotNull String key() {
        return key;
    }

    public abstract @NotNull DataMetaType type();

    public abstract @NotNull String valueAsString();

    public abstract @NotNull Object valueAsObject();
}
