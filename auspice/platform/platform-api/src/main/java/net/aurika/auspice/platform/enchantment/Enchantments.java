package net.aurika.auspice.platform.enchantment;

import org.jetbrains.annotations.NotNull;

public interface Enchantments {

  boolean hasEnchantment(@NotNull Enchantment enchantment);

  /**
   * Removes an enchantment and returns the previous level.
   *
   * @param enchantment the enchantment
   * @return the previous level
   */
  int removeEnchantment(@NotNull Enchantment enchantment);

  int getLevel(@NotNull Enchantment enchantment);

  /**
   * Sets the new enchantment level for an enchantment and returns the previous level.
   *
   * @return the previous level
   */
  int setLevel(@NotNull Enchantment enchantment, int level);

}
