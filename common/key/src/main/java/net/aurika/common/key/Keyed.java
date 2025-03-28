package net.aurika.common.key;

import org.jetbrains.annotations.NotNull;

public interface Keyed {

  /**
   * Get the key.
   */
  @NotNull Key key();

}
