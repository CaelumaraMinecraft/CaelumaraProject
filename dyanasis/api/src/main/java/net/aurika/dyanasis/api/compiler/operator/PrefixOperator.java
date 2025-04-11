package net.aurika.dyanasis.api.compiler.operator;

import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.expression.Operation;
import org.jetbrains.annotations.NotNull;

/**
 * An operator who accepts the operand at the right. Like {@code ++i} {@code !b}.
 */
public interface PrefixOperator extends UnaryOperator {

  @Override
  @NotNull Operation accept(@NotNull Expression right);

}
