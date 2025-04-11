package net.aurika.auspice.server.platform;

import net.aurika.auspice.platform.event.EventManager;
import net.aurika.auspice.platform.location.WorldRegistry;
import net.aurika.auspice.platform.permission.PermissionManager;
import net.aurika.auspice.platform.player.PlayerManager;
import net.aurika.auspice.platform.server.EventServer;
import net.aurika.auspice.platform.server.Platform;
import org.jetbrains.annotations.NotNull;

public interface Server extends Platform, EventServer {

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
