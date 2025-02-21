package net.aurika.auspice.gui.objects.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PredefinedTypeGUI implements GUIInventoryConstructor {
    private final InventoryType a;

    public PredefinedTypeGUI(InventoryType var1) {
        this.a = var1;
    }

    public Inventory create(InventoryHolder var1, String var2) {
        return Bukkit.createInventory(var1, this.a, var2);
    }

    public int getMaxSize() {
        return this.a.getDefaultSize();
    }

    public InventoryType getType() {
        return this.a;
    }
}
