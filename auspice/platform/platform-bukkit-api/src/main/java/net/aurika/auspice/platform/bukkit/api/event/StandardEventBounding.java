package net.aurika.auspice.platform.bukkit.api.event;

import net.aurika.auspice.event.bukkit.BukkitEvent;
import net.aurika.auspice.event.bukkit.BukkitEventManager;
import net.aurika.auspice.platform.bukkit.event.block.AbstractBukkitBlockEvent;
import net.aurika.auspice.platform.bukkit.event.entity.BukkitEntityEvent;
import net.aurika.auspice.platform.bukkit.event.player.BukkitPlayerEvent;
import net.aurika.auspice.platform.bukkit.event.world.BukkitWorldEvent;
import net.aurika.common.util.empty.EmptyArray;
import org.jetbrains.annotations.NotNull;

public final class StandardEventBounding {

  static final Class<?>[] emptyClassArray = EmptyArray.emptyArray(Class.class);

  public static void standardEventBounding(@NotNull BukkitEventManager bukkitEventManager) {
    bukkitEventManager.generateEventBounding(BukkitEvent.class, emptyClassArray);
    bukkitEventManager.generateEventBounding(AbstractBukkitBlockEvent.class, emptyClassArray);
    bukkitEventManager.generateEventBounding(BukkitEntityEvent.class, emptyClassArray);
    bukkitEventManager.generateEventBounding(BukkitPlayerEvent.class, emptyClassArray);
    bukkitEventManager.generateEventBounding(BukkitWorldEvent.class, emptyClassArray);
    bukkitEventManager.generateEventBounding(AbstractBukkitBlockEvent.class, emptyClassArray);
    bukkitEventManager.generateEventBounding(AbstractBukkitBlockEvent.class, emptyClassArray);
  }

}
