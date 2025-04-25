package net.aurika.auspice.platform.bukkit.api.material;

import net.aurika.auspice.platform.bukkit.api.KeyUtil;
import net.aurika.auspice.platform.material.Material;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface BukkitMaterial extends Material {

  @Contract("_ -> new")
  static @NotNull BukkitMaterial bukkitMaterial(org.bukkit.@NotNull Material bukkitMaterial) {
    return new BukkitMaterialImpl(bukkitMaterial);
  }

  @NotNull org.bukkit.Material bukkitMaterial();

}

final class BukkitMaterialImpl implements BukkitMaterial {

  private final @NotNull org.bukkit.Material bukkitMaterial;

  BukkitMaterialImpl(org.bukkit.@NotNull Material bukkitMaterial) {
    Validate.Arg.notNull(bukkitMaterial, "bukkitMaterial");
    this.bukkitMaterial = bukkitMaterial;
  }

  @Override
  public @NotNull org.bukkit.Material bukkitMaterial() {
    return bukkitMaterial;
  }

  @Override
  public @NotNull Key key() {
    return KeyUtil.adaptBukkitKey(bukkitMaterial.getKey());
  }

  @Override
  public @NotNull String translationKey() {
    if (bukkitMaterial.isItem()) {
      return java.util.Objects.requireNonNull(bukkitMaterial.asItemType()).translationKey();
    } else {
      return java.util.Objects.requireNonNull(bukkitMaterial.asBlockType()).translationKey();
    }
  }

  @Override
  public int defaultMaxStackSize() {
    return bukkitMaterial().getMaxStackSize();
  }

}
