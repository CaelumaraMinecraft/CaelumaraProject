package net.aurika.auspice.loader.bukkit;

import net.aurika.auspice.api.user.AuspiceUser;
import net.aurika.auspice.dependencies.DependencyManager;
import net.aurika.auspice.main.Auspice;
import net.aurika.auspice.permission.DefaultAuspicePluginPermissions;
import net.aurika.auspice.server.bukkit.loader.PluginAuspiceLoader;
import net.aurika.auspice.server.bukkit.scheduler.BukkitSchedulerAdapter;
import net.aurika.namespace.NSedKey;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;

public final class BukkitAuspiceLoader extends JavaPlugin implements PluginAuspiceLoader {

  public static boolean QUANTUM_STATE = true;
  private static Auspice auspice;
  private static BukkitAuspiceLoader instance;
  private final JavaPlugin loader;
  private AuspiceUser.State state;
  private DependencyManager dependencyManager;

  public BukkitAuspiceLoader() {
    instance = this;
    this.init();
    this.state = AuspiceUser.State.INITIATING;
    auspice = Auspice.get();


    this.loader = this;
    a bootstrapProvider = new a(this);
    auspice.setBootstrapProvider(bootstrapProvider);
    this.dependencyManager = new DependencyManager(bootstrapProvider);

    this.dependencyManager.loadDependencies(DependencyManager.REQUIRED_DEPENDENCIES);


    AuspiceUser.setTaskScheduler(new BukkitSchedulerAdapter(this, bootstrapProvider));

    if (instance != null || this.state != AuspiceUser.State.INITIATING) {
      throw new IllegalStateException("Plugin loaded twice");
    }


    this.state = AuspiceUser.State.INITIATED;
  }

  @Override
  public void onLoad() {
    this.state = AuspiceUser.State.LOADING;
    auspice.onLoad();

    DefaultAuspicePluginPermissions.init();


    this.state = AuspiceUser.State.LOADED;
  }

  @Override
  public void onEnable() {
    this.state = AuspiceUser.State.ENABLING;


    this.state = AuspiceUser.State.ENABLING;
  }

  @Override
  public void onDisable() {
    this.state = AuspiceUser.State.DISABLING;


    this.state = AuspiceUser.State.DISABLED;
  }

  public DependencyManager getDependencyManager() {
    return this.dependencyManager;
  }

  public static BukkitAuspiceLoader get() {
    return instance;
  }

  public @NotNull AuspiceUser.State getState() {
    return this.state;
  }

  public static NSedKey buildNS(String key) {
    return new NSedKey("Auspice", key);
  }

  private void initConfigs() {

  }

  public @NotNull Plugin loaderPlugin() {
    return this.loader;
  }

  public static Path getFolder() {
    return get().loader.getDataFolder().toPath();
  }

}
