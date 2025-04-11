package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

public interface DeclareLocalVariable extends Declare {

  @NotNull String variableName();

}
