package net.aurika.dyanasis.api.compiler.operator;

import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.expression.Operation;
import org.jetbrains.annotations.NotNull;

/**
 * An operator who accepts the operand at the left. Like {@code i++} {@code i--}.
 */
public interface SuffixOperator extends UnaryOperator {

  /**
   * Accepts the left operand.
   *
   * @param left the left operand
   */
  @Override
  @NotNull Operation accept(@NotNull Expression left);

}
