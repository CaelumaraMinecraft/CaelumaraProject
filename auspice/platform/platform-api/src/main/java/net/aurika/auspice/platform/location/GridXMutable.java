package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GridXMutable {

  /**
   * Changes the grid x.
   *
   * @param x the x
   */
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull
  GridXMutable gridX(int x);

}
