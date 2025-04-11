package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

/**
 * Property declaration.
 */
public interface DeclareProperty extends DeclareMember {

  /**
   * Gets the declared property name.
   *
   * @return the property name
   */
  @Override
  @NotNull String declaredMemberName();

}
