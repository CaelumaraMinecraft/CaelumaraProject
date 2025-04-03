package net.aurika.auspice.folia.server.core;

import net.aurika.auspice.platform.server.Platform;
import net.aurika.auspice.platform.location.WorldRegistry;
import net.aurika.auspice.platform.player.PlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class FoliaServer extends BukkitServer implements Platform {

  public FoliaServer(JavaPlugin plugin) {
    super(plugin);
  }

  @Override
  public boolean isMainThread() {
    return false;  //TODO
  }

  @Override
  public @NotNull FoliaEventHandler eventManager() {
    return null;
  }

  @Override
  public @NotNull WorldRegistry worldRegistry() {
    return null;
  }

  @Override
  public @NotNull PlayerManager playerManager() {
    return null;
  }

}
