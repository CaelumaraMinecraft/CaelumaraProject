package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.NotNull;

public interface DeclareFunction extends DeclareMember {

  @NotNull DeclareFunction.Body functionBody();

  interface Body { }

}
