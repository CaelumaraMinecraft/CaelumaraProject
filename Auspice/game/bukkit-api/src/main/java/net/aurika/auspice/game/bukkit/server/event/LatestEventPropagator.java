package net.aurika.auspice.game.bukkit.server.event;

import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.server.event.EntityDismountEvent;
import net.aurika.auspice.server.event.EntityMountEvent;
import net.aurika.auspice.server.event.EventPropagator;
import net.aurika.auspice.utils.reflection.Reflect;
import org.bukkit.event.EventHandler;

import java.net.http.WebSocket;

public final class LatestEventPropagator {

  public static final class Dismount implements WebSocket.Listener, EventPropagator {

    @EventHandler
    public void onEntityDismount(org.bukkit.event.entity.EntityDismountEvent event) {
      EntityDismountEvent delegate = new EntityDismountEvent(event.getEntity(), event.getDismounted());
      delegate.setCancelled(event.isCancelled());
      Server.get().getEventHandler().callEvent(delegate);
    }

    @EventHandler
    public void onEntityDismount(EntityMountEvent event) {
      net.aurika.auspice.server.event.EntityMountEvent delegate = new net.aurika.auspice.server.event.EntityMountEvent(
          event.getEntity(), event.getMount());
      Server.get().getEventHandler().callEvent(delegate);
    }

    @Override
    public boolean shouldRegister() {
      return Reflect.classExists("org.bukkit.event.entity.EntityDismountEvent");
    }

  }

}
