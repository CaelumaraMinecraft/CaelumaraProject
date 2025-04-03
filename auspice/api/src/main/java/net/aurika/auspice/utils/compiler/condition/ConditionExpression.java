package net.aurika.auspice.utils.compiler.condition;

import net.aurika.auspice.utils.compiler.base.Expression;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

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
