package net.aurika.common.key;

import org.jetbrains.annotations.NotNull;

public interface Identified {

  /**
   * Gets the ident for this {@link Identified}
   *
   * @return the ident
   */
  @NotNull Ident ident();

}
