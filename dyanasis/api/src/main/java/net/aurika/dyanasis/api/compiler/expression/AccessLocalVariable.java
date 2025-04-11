package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

/**
 * The access to a local variable and parameter in a function.
 */
public interface AccessLocalVariable extends Access {

  @NotNull String variableName();

}
