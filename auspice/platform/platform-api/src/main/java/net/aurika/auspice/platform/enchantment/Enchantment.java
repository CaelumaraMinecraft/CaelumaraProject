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
  public abstract int getMaxLevel();

  /**
   * Gets the level that this Enchantment should start at
   *
   * @return Starting level of the Enchantment
   */
  public abstract int getStartLevel();

  /**
   * Get the name of the enchantment with its applied level.
   * <p>
   * If the given {@code level} is either less than the {@link #getStartLevel()} or greater than the {@link #getMaxLevel()},
   * the level may not be shown in the numeral format one may otherwise expect.
   * </p>
   *
   * @param level the level of the enchantment to show
   * @return the name of the enchantment with {@code level} applied
   */
  public abstract @NotNull Component displayName(int level);

  /**
   * Provides the description of this enchantment entry as displayed to the client, e.g. "Sharpness" for the sharpness
   * enchantment.
   *
   * @return the description component.
   */
  public abstract @NotNull Component description();

}
