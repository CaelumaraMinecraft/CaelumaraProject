package top.auspice.utils.compiler.math;

import org.jetbrains.annotations.NotNull;

public final class MathEvaluateException extends ArithmeticException {
    public MathEvaluateException(@NotNull String message) {
        super(message);
    }
}
