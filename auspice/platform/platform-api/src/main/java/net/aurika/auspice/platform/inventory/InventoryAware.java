package net.aurika.auspice.platform.inventory;

import org.jetbrains.annotations.NotNull;

public interface InventoryAware {
  /**
   * Get the object's inventory.
   *
   * @return The inventory.
   */
  @NotNull Inventory inventory();

}
