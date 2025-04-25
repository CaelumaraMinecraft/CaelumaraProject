package net.aurika.auspice.platform.inventory;

import net.aurika.auspice.platform.inventory.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface Inventory extends InventoryAware {

  @NotNull Iterator<ItemStack> items();

  @Override
  default @NotNull Inventory inventory() { return this; }

}
