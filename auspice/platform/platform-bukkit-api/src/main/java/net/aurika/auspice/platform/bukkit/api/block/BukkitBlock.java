package net.aurika.auspice.platform.bukkit.api.block;

import net.aurika.auspice.platform.block.Block;
import org.jetbrains.annotations.NotNull;

public interface BukkitBlock extends Block {

  org.bukkit.block.@NotNull Block bukkitBlock();

}
