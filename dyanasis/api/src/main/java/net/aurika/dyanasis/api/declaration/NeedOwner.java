package net.aurika.dyanasis.api.declaration;

import org.jetbrains.annotations.NotNull;

/**
 * A {@linkplain DyanasisDeclaration} that needs an anchor.
 */
public interface NeedOwner {

  /**
   * Gets the anchored anchor of this {@linkplain NeedOwner}.
   *
   * @return the owner
   */
  @NotNull DyanasisDeclarationAnchor owner();

}
