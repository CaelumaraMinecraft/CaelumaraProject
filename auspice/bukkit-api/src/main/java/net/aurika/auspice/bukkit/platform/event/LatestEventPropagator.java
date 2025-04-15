package net.aurika.auspice.bukkit.platform.event;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.event.EntityDismountEvent;
import net.aurika.auspice.platform.event.EntityMountEvent;
import net.aurika.auspice.platform.event.EventPropagator;
import net.aurika.auspice.utils.reflection.Reflect;
import org.bukkit.event.EventHandler;

import java.net.http.WebSocket;

public final class LatestEventPropagator {

  public static final class Dismount implements WebSocket.Listener, EventPropagator {

    @EventHandler
    public void onEntityDismount(org.bukkit.event.entity.EntityDismountEvent event) {
      EntityDismountEvent delegate = new EntityDismountEvent(event.getEntity(), event.getDismounted());
      delegate.setCancelled(event.isCancelled());
      Platform.get().getEventHandler().callEvent(delegate);
    }

    @EventHandler
    public void onEntityDismount(EntityMountEvent event) {
      net.aurika.auspice.platform.event.EntityMountEvent delegate = new net.aurika.auspice.platform.event.EntityMountEvent(
          event.getEntity(), event.getMount());
      Platform.get().getEventHandler().callEvent(delegate);
    }

    @Override
    public boolean shouldRegister() {
      return Reflect.classExists("org.bukkit.event.entity.EntityDismountEvent");
    }

  }

}
