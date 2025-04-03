package net.aurika.auspice.server.bukkit.platform.inventory;

import net.aurika.auspice.platform.inventory.BukkitInventoryView;
import net.aurika.auspice.platform.inventory.NewInventoryView;

public class InventoryView {

  public static BukkitInventoryView of(Object any) {
    if (any.getClass().isInterface()) return new NewInventoryView(any);
    else return new OldInventoryView(any);
  }

}
