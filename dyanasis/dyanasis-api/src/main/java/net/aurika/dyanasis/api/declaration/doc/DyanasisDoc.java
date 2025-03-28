package net.aurika.dyanasis.api.declaration.doc;

import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Experimental
public interface DyanasisDoc extends DyanasisDeclaration, NeedOwner, DyanasisRuntimeObject {

  /**
   * Gets the dyanasis doc value.
   *
   * @return the doc value
   */
  @NotNull String value();

  @NotNull DyanasisDocAnchor owner();

  @Override
  @NotNull DyanasisRuntime dyanasisRuntime();

}
