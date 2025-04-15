package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.declaration.executable.DyanasisExecutable;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link DyanasisExecutable} executing.
 */
public interface Execute extends Access {

  default @NotNull DyanasisExecutable accessed() {
    throw new UnsupportedOperationException("executed()");
  }

}
