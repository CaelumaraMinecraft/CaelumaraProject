package net.aurika.auspice.platform.bukkit.api.inventory;

import net.aurika.auspice.platform.inventory.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface BukkitItemStack extends ItemStack {

  @NotNull org.bukkit.inventory.ItemStack bukkitObject();

  interface Adapter<AI extends BukkitItemStack, PT extends org.bukkit.inventory.ItemStack> extends ItemStack.Adapter<AI, PT> { }

}
