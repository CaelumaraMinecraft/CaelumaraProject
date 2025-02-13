package net.aurika.util.number;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public enum NumberType {
    /**
     * Byte
     */
    BYTE(byte.class, -128, 127, 1) {
        public @NotNull AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of((Number) Byte.parseByte(string));
        }
    },
    /**
     * Short
     */
    SHORT(short.class, Short.MIN_VALUE, 32767, 2) {
        public @NotNull AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of((Number) Short.parseShort(string));
        }
    },
    /**
     * Int
     */
    INT(int.class, Integer.MIN_VALUE, Integer.MAX_VALUE, 4) {
        public @NotNull AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of(Integer.parseInt(string));
        }
    },
    /**
     * Long
     */
    LONG(long.class, Long.MIN_VALUE, Long.MAX_VALUE, 8) {
        public @NotNull AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of((Number) Long.parseLong(string));
        }
    },
    /**
     * Float
     */
    FLOAT(float.class, Float.MIN_VALUE, Float.MAX_VALUE, 4) {
        public @NotNull AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of(Float.parseFloat(string));
        }
    },
    /**
     * Double
     */
    DOUBLE(double.class, Double.MIN_VALUE, Double.MAX_VALUE, 8) {
        public @NotNull AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of(Double.parseDouble(string));
        }
    };

    private final @NotNull Class<? extends Number> jvmClass;
    private final @NotNull Number minValue;
    private final @NotNull Number maxValue;
    private final int byteSize;

    NumberType(@NotNull Class<? extends Number> jvmClass, @NotNull Number minValue, @NotNull Number maxValue, int byteSize) {
        this.jvmClass = jvmClass;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.byteSize = byteSize;
    }

    public final @NotNull Class<? extends Number> getJvmClass() {
        return jvmClass;
    }

    public final @NotNull Number getMinValue() {
        return minValue;
    }

    public final @NotNull Number getMaxValue() {
        return maxValue;
    }

    public final int getByteSize() {
        return byteSize;
    }

    public abstract @NotNull AnyNumber parseStringRaw(@NotNull String string);

    public final @Nullable AnyNumber parseString(@NotNull String string) {
        Objects.requireNonNull(string);

        AnyNumber var2;
        try {
            var2 = this.parseStringRaw(string);
        } catch (NumberFormatException var4) {
            var2 = null;
        }

        return var2;
    }

    /**
     * 是否是浮点数字
     */
    public final boolean isFloatingPoint() {
        return this == FLOAT || this == DOUBLE;
    }
}
