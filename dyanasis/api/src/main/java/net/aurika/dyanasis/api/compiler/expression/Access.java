package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

/**
 * An accessing. It can be a function invoking, a property getting, a namespace accessing or others.
 */
public interface Access extends Statement {

  /**
   * Gets the operand that be invoked.
   *
   * @return the operand
   */
  @NotNull Expression operand();

}
