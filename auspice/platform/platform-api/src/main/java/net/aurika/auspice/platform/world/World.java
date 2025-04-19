package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface World extends WorldInfo {

  @Override
  @NotNull String name();

  @Override
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
