package top.auspice.utils.compiler.condition;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.compiler.base.Expression;

public interface ConditionExpression extends Expression<ConditionVariableTranslator, Boolean> {
    @NotNull Boolean eval(@NotNull ConditionVariableTranslator variableTranslator);

    boolean isDefault();

    @Nullable
    String getOriginalString();

    @NotNull String asString(boolean b);

    default @Nullable ConditionExpression nullIfDefault() {
        return this.isDefault() ? null : this;
    }
}
