package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface WorldInfo {

  /**
   * Gets the unique name of the world.
   *
   * @return the unique name
   */
  @NotNull String name();

  /**
   * Gets the unique id of the world.
   *
   * @return the unique id
   */
  @NotNull UUID id();

}
