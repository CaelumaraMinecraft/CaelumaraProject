package net.aurika.dyanasis.api.declaration.executable.parameter;

import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

public interface DyanasisParameter {

  boolean isAllowedInputType(@NotNull DyanasisTypeIdent typeIdent);

  /**
   * Gets the parameter name. There is no need to implementation this method.
   *
   * @return the parameter name
   */
  @UnknownNullability
  String name();

}
