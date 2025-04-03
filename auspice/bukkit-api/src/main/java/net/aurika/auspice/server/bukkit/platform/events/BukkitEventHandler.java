package net.aurika.auspice.server.bukkit.platform.events;

import net.aurika.auspice.bukkit.server.core.BukkitServer;
import net.aurika.auspice.bukkit.server.event.LatestEventPropagator;
import net.aurika.auspice.platform.annotations.MeansType;
import net.aurika.auspice.platform.bukkit.old.event.OldEventPropagator;
import net.aurika.auspice.platform.event.EventHandler;
import net.aurika.auspice.platform.event.EventPropagator;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public class BukkitEventHandler implements EventHandler {

  private final BukkitServer server;

  public BukkitEventHandler(BukkitServer server) {
    Objects.requireNonNull(server);
    this.server = server;
  }

  public void onLoad() {
    Stream.of(new OldEventPropagator.Dismount(), new LatestEventPropagator.Dismount())
        .filter(EventPropagator::shouldRegister).forEach(this::registerEvents);
  }

  @Override
  public void callEvent(@NotNull @MeansType("org.bukkit.event.Event") Object event) {
    Bukkit.getPluginManager().callEvent((Event) event);
  }

  @Override
  public void registerEvents(@NotNull @MeansType("org.bukkit.event.Listener") Object listener) {
    Bukkit.getPluginManager().registerEvents((Listener) listener, this.server.getPlugin());
  }

}
