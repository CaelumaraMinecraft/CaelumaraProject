package net.aurika.auspice.platform.inventory.receipe;

import net.aurika.auspice.platform.inventory.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents some type of crafting recipe.
 */
public interface Recipe {

  /**
   * Get the result of this recipe.
   *
   * @return The result stack
   */
  @NotNull ItemStack getResult();

}
