package net.aurika.auspice.platform.bukkit.api.event.player;

import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import net.aurika.auspice.platform.bukkit.event.entity.BukkitEntityEvent;
import net.aurika.auspice.platform.event.player.PlayerEvent;
import org.bukkit.entity.Player;

/**
 * Represents a player related event
 */
@NativeBukkitEvent(org.bukkit.event.player.PlayerEvent.class)
public interface BukkitPlayerEvent extends BukkitEntityEvent, PlayerEvent {

  /**
   * @see org.bukkit.event.player.PlayerEvent#getPlayer()
   */
  @Override
  default Player bukkitEntity() {
    return ((org.bukkit.event.player.PlayerEvent) this).getPlayer();
  }

}
