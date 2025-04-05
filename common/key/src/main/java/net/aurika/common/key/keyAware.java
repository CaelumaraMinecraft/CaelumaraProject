package net.aurika.common.key;

import org.jetbrains.annotations.NotNull;

public interface keyAware {

  /**
   * Gets the key.
   *
   * @return the key
   */
  @NotNull Key key();

}
