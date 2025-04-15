package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface World {

  /**
   * Gets the name of the world.
   *
   * @return the world name
   */
  @NotNull String name();

  /**
   * Gets the uuid of the world.
   *
   * @return the world uuid
   */
  @NotNull UUID uuid();

  /**
   * Gets the max height of the world.
   *
   * @return the world max height
   */
  int maxHeight();

  /**
   * Gets the minimum height of the world.
   *
   * @return the world minimum height
   */
  int minHeight();

}