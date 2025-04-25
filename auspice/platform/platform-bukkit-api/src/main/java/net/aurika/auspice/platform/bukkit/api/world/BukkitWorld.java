package net.aurika.auspice.platform.bukkit.api.world;

import net.aurika.auspice.platform.world.World;
import org.jetbrains.annotations.NotNull;

public interface BukkitWorld extends World {

  @NotNull org.bukkit.World bukkitWorld();

}
