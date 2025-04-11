package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

/**
 * A block declaration. It can be in a function declaration, switch branch, etc...
 */
public interface DeclareBlock extends Expression, Declare {

  @NotNull Expression @NotNull [] expressions();

}
