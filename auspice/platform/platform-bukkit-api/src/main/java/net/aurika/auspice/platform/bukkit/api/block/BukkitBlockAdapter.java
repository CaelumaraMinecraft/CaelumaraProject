package net.aurika.auspice.platform.bukkit.api.block;

import org.jetbrains.annotations.NotNull;

public interface BukkitBlockAdapter {

  static @NotNull BukkitBlockAdapter get() {
    // TODO
  }

  @NotNull BukkitBlock adaptToAuspice(@NotNull org.bukkit.block.Block bukkitBlock);

}
