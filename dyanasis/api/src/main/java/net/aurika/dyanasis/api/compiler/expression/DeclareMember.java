package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.declaration.member.DyanasisMemberAnchor;
import org.jetbrains.annotations.NotNull;

/**
 * A member declaration.
 */
public interface DeclareMember extends Declare {

  /**
   * Gets the declared member name.
   *
   * @return the name
   */
  @NotNull String declaredMemberName();

  default @NotNull DyanasisMemberAnchor declaredFor() {
    throw new UnsupportedOperationException();
  }

}
