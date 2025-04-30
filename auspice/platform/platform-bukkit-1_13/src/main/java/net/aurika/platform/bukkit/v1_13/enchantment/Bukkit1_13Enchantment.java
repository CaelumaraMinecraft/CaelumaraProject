package net.aurika.platform.bukkit.v1_13.enchantment;

import net.aurika.auspice.platform.bukkit.api.KeyUtil;
import net.aurika.auspice.platform.enchantment.Enchantment;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class Bukkit1_13Enchantment implements Enchantment {

  private final @NotNull org.bukkit.enchantments.Enchantment bukkitEnchantment;

  public Bukkit1_13Enchantment(@NotNull org.bukkit.enchantments.Enchantment bukkitEnchantment) {
    Validate.Arg.notNull(bukkitEnchantment, "bukkitEnchantment");
    this.bukkitEnchantment = bukkitEnchantment;
  }

  @Override
  public @NotNull Key key() {
    return KeyUtil.adaptBukkitKey(bukkitEnchantment.getKey());
  }

  @Override
  public int maxLevel() {
    return bukkitEnchantment.getMaxLevel();
  }

  @Override
  public int startLevel() {
    return bukkitEnchantment.getStartLevel();
  }

  @Override
  public @NotNull Component displayName(int level) { }

  @Override
  public @NotNull Component description() { }

}
