package net.aurika.common.ident;

import org.jetbrains.annotations.NotNull;

public interface IdentAware {

  /**
   * Gets the known ident.
   *
   * @return the known ident
   */
  @NotNull Ident ident();

}
