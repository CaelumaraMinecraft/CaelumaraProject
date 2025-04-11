package net.aurika.dyanasis.api.compiler.expression;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A placeholder for other {@linkplain Access} {@linkplain Access#operand()}.
 */
@ApiStatus.Experimental
@ApiStatus.Obsolete
public interface AccessImplicit extends Access {

  default @NotNull Access operand() {
    return this;
  }

}
