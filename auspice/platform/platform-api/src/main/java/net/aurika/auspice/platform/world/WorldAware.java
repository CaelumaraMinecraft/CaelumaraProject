package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.NotNull;

/**
 * An object has the world property.
 */
public interface WorldAware {

  /**
   * Gets the world.
   *
   * @return the world
   */
  @NotNull World world();

}