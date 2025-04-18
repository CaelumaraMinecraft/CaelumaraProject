package net.aurika.common.ident;

import org.jetbrains.annotations.NotNull;

public interface Identified extends IdentAware, net.aurika.common.keyed.Keyed<Ident> {

  /**
   * Gets the ident for this {@link Identified}.
   *
   * @return the ident
   */
  @Override
  @NotNull Ident ident();

  @Override
  default @NotNull Ident key() { return ident(); }

}
