package net.aurika.auspice.platform.enchantment;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface Enchantment extends Keyed {

  @Override
  @NotNull Key key();

  /**
   * Gets the maximum level that this Enchantment may become.
   *
   * @return Maximum level of the Enchantment
   */
  int maxLevel();

  /**
   * Gets the level that this Enchantment should start at
   *
   * @return Starting level of the Enchantment
   */
  int startLevel();

  /**
   * Get the name of the enchantment with its applied level.
   * <p>
   * If the given {@code level} is either less than the {@link #startLevel()} or greater than the {@link #maxLevel()},
   * the level may not be shown in the numeral format one may otherwise expect.
   * </p>
   *
   * @param level the level of the enchantment to show
   * @return the name of the enchantment with {@code level} applied
   */
  @NotNull Component displayName(int level);

  /**
   * Provides the description of this enchantment entry as displayed to the client, e.g. "Sharpness" for the sharpness
   * enchantment.
   *
   * @return the description component.
   */
  @NotNull Component description();

}
