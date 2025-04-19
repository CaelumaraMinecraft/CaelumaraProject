package net.aurika.auspice.platform;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface DoubleIdentified {

  /**
   * Gets the unique name.
   *
   * @return the unique name
   */
  @NotNull String name();

  /**
   * Gets the unique id.
   *
   * @return the unique id
   */
  @NotNull UUID uuid();

}
