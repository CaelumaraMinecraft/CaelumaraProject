package top.auspice.bukkitlegacy.server.inventory

import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import top.auspice.server.inventory.BukkitInventoryView

class OldInventoryView(view: Any) : BukkitInventoryView {
    val view: org.bukkit.inventory.InventoryView = view as org.bukkit.inventory.InventoryView

    override val topInventory: Inventory get() = view.topInventory
    override val bottomInventory: Inventory get() = view.bottomInventory
    override val player: HumanEntity get() = view.player
    override val type: InventoryType get() = view.type
    override fun setItem(var1: Int, var2: ItemStack?) = view.setItem(var1, var2)
    override fun getItem(var1: Int): ItemStack? = view.getItem(var1)
    override fun setCursor(var1: ItemStack?) {
        view.cursor = var1
    }

    override val cursor: ItemStack? get() = view.cursor
    override fun convertSlot(var1: Int): Int = view.convertSlot(var1)
    override fun close() = view.close()
    override fun countSlots(): Int = view.countSlots()
    override val title: String get() = view.title
}