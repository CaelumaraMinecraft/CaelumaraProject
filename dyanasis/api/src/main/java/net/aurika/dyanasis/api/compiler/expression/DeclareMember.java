package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

public interface DeclareMember extends Declare {

  /**
   * Gets the declared member name.
   *
   * @return the name
   */
  @NotNull String declaredMemberName();

}
