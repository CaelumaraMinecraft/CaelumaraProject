package net.aurika.auspice.game.server.core;

import net.aurika.auspice.server.core.EventServer;
import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.server.event.EventManager;
import net.aurika.auspice.server.location.WorldRegistry;
import net.aurika.auspice.server.permission.PermissionManager;
import net.aurika.auspice.server.player.PlayerManager;
import org.jetbrains.annotations.NotNull;

public interface GameServer extends Server, EventServer {

  /**
   * Gets ticks the server ran.
   *
   * @return the ticks
   */
  int ticks();

  boolean isMainThread();

  @NotNull EventManager eventManager();

  @NotNull WorldRegistry worldRegistry();

  @NotNull PlayerManager playerManager();

  @NotNull PermissionManager permissionManager();

}
