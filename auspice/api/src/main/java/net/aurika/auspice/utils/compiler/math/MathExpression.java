package net.aurika.auspice.utils.compiler.math;

import net.aurika.auspice.utils.compiler.base.Expression;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

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

