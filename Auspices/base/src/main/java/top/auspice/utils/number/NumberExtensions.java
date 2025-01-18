package top.auspice.utils.number;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NumberExtensions {
    private NumberExtensions() {
    }

    public static double squared(double $this$squared) {
        return $this$squared * $this$squared;
    }

    public static float squared(float $this$squared) {
        return $this$squared * $this$squared;
    }

    public static int squared(int $this$squared) {
        return $this$squared * $this$squared;
    }

    public static boolean isEven(@NotNull Number $this$isEven) {
        Objects.requireNonNull($this$isEven);
        return $this$isEven.intValue() % 2 == 0;
    }

    public static void requireNonNegative(@NotNull Number $this$requireNonNegative) {
        Objects.requireNonNull($this$requireNonNegative);
        if (AnyNumber.of($this$requireNonNegative).isNegative()) {
            throw new IllegalStateException("Required a non-negative number, but got: " + $this$requireNonNegative);
        }
    }
}
