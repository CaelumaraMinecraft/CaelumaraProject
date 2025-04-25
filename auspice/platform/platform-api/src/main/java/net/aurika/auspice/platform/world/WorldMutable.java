package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

// TODO rename

/**
 * 拥有世界属性, 且能更改.
 */
public interface WorldMutable {

  /**
   * Sets the world and returns this object.
   *
   * @param world the new world
   * @return this world mutable
   */
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull WorldMutable world(@NotNull World world);

}
