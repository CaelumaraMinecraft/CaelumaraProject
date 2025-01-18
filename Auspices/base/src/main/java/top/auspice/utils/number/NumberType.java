package top.auspice.utils.number;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public enum NumberType {
    BYTE(Byte.TYPE, -128, 127, 1) {
        @NotNull
        public AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of((Number) Byte.parseByte(string));
        }
    },//
    SHORT(Short.TYPE, Short.MIN_VALUE, 32767, 2) {
        @NotNull
        public AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of((Number) Short.parseShort(string));
        }
    },//
    INT(Integer.TYPE, Integer.MIN_VALUE, Integer.MAX_VALUE, 4) {
        @NotNull
        public AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of(Integer.parseInt(string));
        }
    },//
    LONG(Long.TYPE, Long.MIN_VALUE, Long.MAX_VALUE, 8) {
        @NotNull
        public AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of((Number) Long.parseLong(string));
        }
    },//
    FLOAT(Float.TYPE, Float.MIN_VALUE, Float.MAX_VALUE, 4) {
        @NotNull
        public AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of(Float.parseFloat(string));
        }
    },//
    DOUBLE(Double.TYPE, Double.MIN_VALUE, Double.MAX_VALUE, 8) {
        @NotNull
        public AnyNumber parseStringRaw(@NotNull String string) {
            Objects.requireNonNull(string);
            return AnyNumber.of(Double.parseDouble(string));
        }
    };//

    @NotNull
    private final Class<? extends Number> jvmClass;
    @NotNull
    private final Number minValue;
    @NotNull
    private final Number maxValue;
    private final int byteSize;

    NumberType(@NotNull Class<? extends Number> jvmClass, @NotNull Number minValue, @NotNull Number maxValue, int byteSize) {
        this.jvmClass = jvmClass;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.byteSize = byteSize;
    }

    @NotNull
    public final Class<? extends Number> getJvmClass() {
        return this.jvmClass;
    }

    @NotNull
    public final Number getMinValue() {
        return this.minValue;
    }

    @NotNull
    public final Number getMaxValue() {
        return this.maxValue;
    }

    public final int getByteSize() {
        return this.byteSize;
    }

    @NotNull
    public abstract AnyNumber parseStringRaw(@NotNull String string);

    @Nullable
    public final AnyNumber parseString(@NotNull String string) {
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
