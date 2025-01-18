package top.auspice.utils.compiler.math;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.compiler.base.Expression;

public interface MathExpression extends Expression<MathVariableTranslator, Double> {
    @NotNull Double eval(@NotNull MathVariableTranslator variableTranslator);

    boolean isDefault();

    default @Nullable MathExpression nullIfDefault() {
        return this.isDefault() ? null : this;
    }

    @Nullable
    String getOriginalString();

    @NotNull String asString(boolean b);
}

