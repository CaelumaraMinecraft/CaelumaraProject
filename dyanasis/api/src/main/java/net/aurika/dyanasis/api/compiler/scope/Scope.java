package net.aurika.dyanasis.api.compiler.scope;

import org.jetbrains.annotations.Nullable;

public interface Scope {

  @Nullable Scope parent();

}
