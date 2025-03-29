package net.aurika.auspice.game.bukkit.server.core;

import net.aurika.auspice.game.bukkit.server.adapers.BukkitNBTAdapter;
import net.aurika.auspice.game.bukkit.server.events.BukkitEventHandler;
import net.aurika.auspice.game.bukkit.server.location.BukkitWorldRegistry;
import net.aurika.auspice.game.bukkit.server.player.BukkitPlayerManager;
import net.aurika.auspice.server.core.Server;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * 单例模式
 */
public class BukkitServer implements Server {

  private static BukkitServer instance;
  protected final JavaPlugin plugin;
  protected final TickTracker tickTracker = new TickTracker();
  private final BukkitEventHandler eventHandler = new BukkitEventHandler(this);
  private final BukkitWorldRegistry worldRegistry = new BukkitWorldRegistry(this);
  private final BukkitPlayerManager playerManager = new BukkitPlayerManager(this);

  public BukkitServer(JavaPlugin plugin) {
    instance = this;
    this.plugin = plugin;
  }

  @Override
  public @NotNull BukkitEventHandler getEventHandler() {
    return this.eventHandler;
  }

  public JavaPlugin getPlugin() {
    return this.plugin;
  }

  @Override
  public void onStartup() {
    BukkitNBTAdapter.registerAll();
  }

  @Override
  public void onEnable() {
    tickTracker.start(this.plugin);
    this.eventHandler.onLoad();
  }

  @Override
  public int getTicks() {
    return tickTracker.getTicks();
  }

  @Override
  public boolean isMainThread() {
    return Bukkit.isPrimaryThread();
  }

  @Override
  public @NotNull BukkitWorldRegistry getWorldRegistry() {
    return this.worldRegistry;
  }

  @Override
  public @NotNull BukkitPlayerManager getPlayerManager() {
    return this.playerManager;
  }

  public static BukkitServer get() {
    if (instance == null) (new IllegalStateException("BukkitServer has not been initialized")).printStackTrace();
    return instance;
  }

}
