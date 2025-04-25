package net.aurika.auspice.platform.bukkit.api.event.entity;

import net.aurika.auspice.event.bukkit.NativeBukkitEvent;
import net.aurika.auspice.event.bukkit.BukkitEvent;
import net.aurika.auspice.platform.event.entity.EntityEvent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

@NativeBukkitEvent(org.bukkit.event.entity.EntityEvent.class)
public interface BukkitEntityEvent extends BukkitEvent, EntityEvent {

  /**
   * @see org.bukkit.event.entity.EntityEvent#getEntity()
   */
  default Entity bukkitEntity() {
    return ((org.bukkit.event.entity.EntityEvent) this).getEntity();
  }

  default EntityType bukkitEntityType() {
    return bukkitEntity().getType();
  }

}
