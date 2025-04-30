package net.aurika.auspice.platform.inventory.item;

import net.aurika.auspice.platform.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

public interface ItemStack {

  @NotNull Enchantments enchantments();

  interface Adapter<AI extends ItemStack, PT> extends net.aurika.auspice.platform.adapter.Adapter<AI, PT> { }

}
