package top.auspice.bukkit.server.event;

import org.bukkit.event.EventHandler;
import top.auspice.server.core.Server;
import top.auspice.server.event.EntityDismountEvent;
import top.auspice.server.event.EntityMountEvent;
import top.auspice.server.event.EventPropagator;
import top.auspice.utils.reflection.Reflect;

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
            top.auspice.server.event.EntityMountEvent delegate = new top.auspice.server.event.EntityMountEvent(event.getEntity(), event.getMount());
            Server.get().getEventHandler().callEvent(delegate);
        }

        @Override
        public boolean shouldRegister() {
            return Reflect.classExists("org.bukkit.event.entity.EntityDismountEvent");
        }
    }
}
