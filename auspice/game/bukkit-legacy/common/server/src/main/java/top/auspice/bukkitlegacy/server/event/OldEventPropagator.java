package net.aurika.auspice.bukkitlegacy.server.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;
import net.aurika.auspice.platform.core.Server;
import net.aurika.auspice.platform.event.EventPropagator;
import net.aurika.auspice.utils.reflection.Reflect;

public final class OldEventPropagator {

  private OldEventPropagator() { }

  public static final class Dismount implements Listener, EventPropagator {

    @EventHandler
    public void onEntityDismount(EntityDismountEvent event) {
      net.aurika.auspice.platform.event.EntityDismountEvent delegate = new net.aurika.auspice.platform.event.EntityDismountEvent(
          event.getEntity(), event.getDismounted());
      Server.get().getEventHandler().callEvent(delegate);
    }

    @EventHandler
    public void onEntityDismount(EntityMountEvent event) {
      net.aurika.auspice.platform.event.EntityMountEvent delegate = new net.aurika.auspice.platform.event.EntityMountEvent(
          event.getEntity(), event.getMount());
      Server.get().getEventHandler().callEvent(delegate);
    }

    @Override
    public boolean shouldRegister() {
      return !Reflect.classExists("org.bukkit.event.entity.EntityDismountEvent");
    }

  }

}
