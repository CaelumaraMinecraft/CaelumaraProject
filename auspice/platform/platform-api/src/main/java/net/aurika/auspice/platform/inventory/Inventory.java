package net.aurika.auspice.platform.inventory;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface Inventory extends Iterable<ItemStack> {

  @Override
  @NotNull Iterator<ItemStack> iterator();

}
