package net.aurika.auspice.platform.inventory.item;

public interface ItemStack {

  interface Adapter<AI extends ItemStack, PT> extends net.aurika.auspice.platform.adapter.Adapter<AI, PT> { }

}
