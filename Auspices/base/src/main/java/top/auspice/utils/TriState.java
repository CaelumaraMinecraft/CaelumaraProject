package top.auspice.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

public enum TriState {
    NOT_SET,
    FALSE,
    TRUE;

    public @Nullable Boolean toBoolean() {
        return switch (this) {
            case TRUE -> Boolean.TRUE;
            case FALSE -> Boolean.FALSE;
            default -> null;
        };
    }

    public boolean toBooleanOrElse(final boolean other) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            default -> other;
        };
    }

    public boolean toBooleanOrElseGet(final @NotNull BooleanSupplier supplier) {
        return switch (this) {
            case TRUE -> true;
            case FALSE -> false;
            default -> supplier.getAsBoolean();
        };
    }

    public static @NotNull TriState byBoolean(final boolean value) {
        return value ? TRUE : FALSE;
    }

    public static @NotNull TriState byBoolean(final @Nullable Boolean value) {
        return value == null ? NOT_SET : byBoolean(value.booleanValue());
    }
}
