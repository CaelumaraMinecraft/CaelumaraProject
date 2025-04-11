package net.aurika.dyanasis.api.compiler.operator;

import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.expression.Operation;
import org.jetbrains.annotations.NotNull;

/**
 * An operator that accepts a left operand and a right operand like {@code 1 + 4}
 */
public interface BinaryOperator extends Operator {

  @NotNull Operation accept(@NotNull Expression left, @NotNull Expression right);

}
