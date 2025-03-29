package net.aurika.auspice.abstraction;

import net.aurika.auspice.constants.player.AuspicePlayer;
import org.jetbrains.annotations.NotNull;

public interface AuspicePlayerOperator {

  /**
   * Gets the operating {@linkplain AuspicePlayer}.
   *
   * @return the auspice player
   */
  @NotNull AuspicePlayer auspicePlayer();

}
