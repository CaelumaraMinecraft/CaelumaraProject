package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.NotNull;

public interface WorldAware {

  /**
   * Gets the world.
   *
   * @return the world
   */
  @NotNull World world();

}