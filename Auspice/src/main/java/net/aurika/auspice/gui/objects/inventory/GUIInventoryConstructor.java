package net.aurika.auspice.gui.objects.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface GUIInventoryConstructor {
    Inventory create(InventoryHolder var1, String var2);

    int getMaxSize();

    InventoryType getType();
}
