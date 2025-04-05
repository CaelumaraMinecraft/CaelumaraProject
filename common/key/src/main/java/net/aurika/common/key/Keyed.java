package net.aurika.common.key;

import org.jetbrains.annotations.NotNull;

public interface Keyed extends keyAware {

  /**
   * Gets the key for this {@link Keyed}.
   *
   * @return the key
   */
  @Override
  @NotNull Key key();

}
