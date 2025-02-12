package net.aurika.utils.number;

import net.aurika.data.api.DataStringRepresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * An immutable, finite, non-NaN, non-Infinity number.
 * If any of the operations causes the number to become NaN or -/+Infinity,
 * a {@linkplain IllegalStateException} is thrown.
 */
public interface AnyNumber extends Comparable<AnyNumber>, DataStringRepresentation {
    AnyNumber ZERO = of(0);
    AnyNumber TWO = of(2);

    @NotNull Number getValue();

    @NotNull NumberType getType();

    @NotNull AnyNumber constructNew(@NotNull Number var1);

    boolean isNegative();

    boolean isPositive();

    boolean isZero();

    default boolean isEven() {
        return this.rem(TWO).equals(ZERO);
    }

    default boolean isOdd() {
        return !this.isEven();
    }

    default @NotNull AnyNumber abs() {
        return this.compareTo(ZERO) < 0 ? this.unaryMinus() : this;
    }

    @NotNull AnyNumber unaryMinus();

    @NotNull AnyNumber unaryPlus();

    @NotNull AnyNumber inc();

    @NotNull AnyNumber dec();

    @NotNull AnyNumber plus(@NotNull AnyNumber var1);

    @NotNull AnyNumber minus(@NotNull AnyNumber var1);

    @NotNull AnyNumber times(@NotNull AnyNumber var1);

    @NotNull AnyNumber div(@NotNull AnyNumber var1);

    @NotNull AnyNumber rem(@NotNull AnyNumber var1);

    static @NotNull AnyNumber of(@NotNull Number number) {
        Objects.requireNonNull(number, "number");
        AnyNumber var10000;
        if (number instanceof Integer) {
            var10000 = new _Int(number.intValue());
        } else if (number instanceof Float) {
            var10000 = new _Float(number.floatValue());
        } else {
            if (!(number instanceof Double)) {
                throw new UnsupportedOperationException("Unsupported number format: " + number + " (" + number.getClass() + ')');
            }

            var10000 = new _Double(number.doubleValue());
        }

        return var10000;
    }

    static @NotNull AnyNumber of(int i) {
        return new _Int(i);
    }

    static @NotNull AnyNumber of(float f) {
        return new _Float(f);
    }

    static @NotNull AnyNumber of(double d) {
        return new _Double(d);
    }

    static @Nullable AnyNumber of(@NotNull String string) {
        Objects.requireNonNull(string);
        NumberType[] var3 = new NumberType[]{NumberType.INT, NumberType.LONG, NumberType.DOUBLE};
        int var9 = 0;

        for (int var4 = var3.length; var9 < var4; ++var9) {
            NumberType type = var3[var9];
            AnyNumber anyNumber = type.parseString(string);
            if (anyNumber != null) {
                return anyNumber;
            }
        }

        return null;
    }
}

