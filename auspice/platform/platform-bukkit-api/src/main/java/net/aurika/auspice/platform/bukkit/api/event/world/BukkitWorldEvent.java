package net.aurika.auspice.platform.bukkit.api.event.world;

import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import net.aurika.auspice.event.bukkit.BukkitEvent;
import net.aurika.auspice.platform.bukkit.world.BukkitWorld;
import net.aurika.auspice.platform.bukkit.world.BukkitWorldAdapter;
import net.aurika.auspice.platform.event.world.WorldEvent;

/**
 * Represents events within a world.
 */
@NativeBukkitEvent(org.bukkit.event.world.WorldEvent.class)
public interface BukkitWorldEvent extends BukkitEvent, WorldEvent {

  /**
   * @see org.bukkit.event.world.WorldEvent#getWorld()
   */
  @Override
  default BukkitWorld world() {
    org.bukkit.event.Event bukkitEvent = toBukkitEvent();
    if (bukkitEvent instanceof org.bukkit.event.world.WorldEvent) {
      org.bukkit.event.world.WorldEvent bukkitWorldEvent = (org.bukkit.event.world.WorldEvent) bukkitEvent;
      return BukkitWorldAdapter.get().adapt(bukkitWorldEvent.getWorld());
    }
    throw new UnsupportedOperationException();
  }

}
