package net.aurika.auspice.game.bukkit.server.inventory;

import top.auspice.server.inventory.BukkitInventoryView;
import top.auspice.server.inventory.NewInventoryView;

public class InventoryView {
    public static BukkitInventoryView of(Object any) {
        if (any.getClass().isInterface()) return new NewInventoryView(any);
        else return new OldInventoryView(any);
    }
}
