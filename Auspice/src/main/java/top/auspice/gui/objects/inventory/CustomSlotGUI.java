package top.auspice.gui.objects.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class CustomSlotGUI implements GUIInventoryConstructor {
    private final int a;

    public CustomSlotGUI(int var1) {
        this.a = var1;
    }

    public Inventory create(InventoryHolder var1, String var2) {
        return Bukkit.createInventory(var1, this.a, var2);
    }

    public int getMaxSize() {
        return this.a;
    }

    public InventoryType getType() {
        return InventoryType.CHEST;
    }
}
