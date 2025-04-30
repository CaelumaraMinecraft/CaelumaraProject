package net.aurika.platform.bukkit.v1_13.inventory;

import net.aurika.auspice.platform.bukkit.api.KeyUtil;
import net.aurika.auspice.platform.bukkit.api.inventory.BukkitItemStack;
import net.aurika.auspice.platform.enchantment.Enchantment;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

;

public class BukkitFlattedItemStack implements BukkitItemStack {

  @Contract("_ -> new")
  public static @NotNull BukkitFlattedItemStack bukkitFlattedItemStack(org.bukkit.inventory.@NotNull ItemStack bukkitItemStack) {
    return new BukkitFlattedItemStack(bukkitItemStack);
  }

  private final org.bukkit.inventory.@NotNull ItemStack bukkitItemStack;
  private final Enchantments enchantments = new Enchantments();

  protected BukkitFlattedItemStack(org.bukkit.inventory.@NotNull ItemStack bukkitItemStack) {
    Validate.Arg.notNull(bukkitItemStack, "bukkitItemStack");
    this.bukkitItemStack = bukkitItemStack;
  }

  @Override
  public @NotNull org.bukkit.inventory.ItemStack bukkitObject() {
    return bukkitItemStack;
  }

  @Override
  public @NotNull Enchantments enchantments() {
    return enchantments;
  }

  public class Enchantments implements net.aurika.auspice.platform.enchantment.Enchantments {

    @Override
    public boolean hasEnchantment(@NotNull Enchantment enchantment) {
      Key enchKey = enchantment.key();
      return bukkitItemStack.containsEnchantment(
          org.bukkit.enchantments.Enchantment.getByKey(KeyUtil.adaptBukkitKey(enchKey)));
    }

    @Override
    public int removeEnchantment(@NotNull Enchantment enchantment) {
      return 0;
    }

    @Override
    public int getLevel(@NotNull Enchantment enchantment) {
      return 0;
    }

    @Override
    public int setLevel(@NotNull Enchantment enchantment, int level) {
      return 0;
    }

  }

}
