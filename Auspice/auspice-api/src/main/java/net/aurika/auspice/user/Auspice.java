package net.aurika.auspice.user;

import net.aurika.auspice.data.centers.AuspiceDataCenter;
import net.aurika.auspice.loader.AuspiceLoader;
import net.aurika.auspice.permission.DefaultAuspicePluginPermissions;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.diversity.StandardDiversity;
import net.aurika.common.dependency.DependencyManager;
import net.aurika.common.dependency.classpath.BootstrapProvider;
import net.aurika.common.key.Key;
import net.aurika.common.key.KeyPatterns;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public final class Auspice implements AuspiceUser {

  public static @KeyPatterns.Namespace
  final @NotNull String NAMESPACE = "Auspice";
  private static final Auspice INSTANCE = new Auspice();
  private State state;
  private AuspiceLoader loader;
  private File dataFolder;
  private AuspiceDataCenter dataCenter;
  private BootstrapProvider bootstrapProvider;
  private DependencyManager dependencyManager;

  @ApiStatus.Internal
  public Auspice() {
    this.state = State.INITIATING;

    this.state = State.INITIATED;
  }

  @Override
  public void onLoad() {
    this.state = State.LOADING;

    this.state = State.LOADED;
  }

  @Override
  public void onEnable() {
    this.state = State.ENABLING;
    this.dataCenter = AuspiceDataCenter.createDefault();
    this.state = State.ENABLED;
  }

  @Override
  public void onReload() {
    this.state = State.RELOADING;

    this.state = State.RELOADED;
  }

  @Override
  public void onDisable() {
    this.state = State.DISABLING;

    this.state = State.DISABLED;
  }

  @Override
  public @NotNull State state() {
    return this.state;
  }

  @AuspiceUserName
  @Override
  public @NotNull String auspiceUserName() {
    return "Auspice";
  }

  @Override
  public @NotNull Diversity defaultDiversity() {
    return StandardDiversity.SIMPLIFIED_CHINESE;
  }

  @Override
  @KeyPatterns.Namespace
  public @NotNull String namespace() {
    return NAMESPACE;
  }

  public static @NotNull Auspice get() {
    return INSTANCE;
  }

  public DependencyManager getDependencyManager() {
    return this.dependencyManager;
  }

  @ApiStatus.Internal
  public void setDependencyManager(DependencyManager dependencyManager) {
    this.dependencyManager = dependencyManager;
  }

  public @Nullable BootstrapProvider getBootstrapProvider() {
    return this.bootstrapProvider;
  }

  @ApiStatus.Internal
  public void setBootstrapProvider(BootstrapProvider bootstrapProvider) {
    this.bootstrapProvider = bootstrapProvider;
  }

  public AuspiceDataCenter getDataCenter() {
    return this.dataCenter;
  }

  public AuspiceLoader getLoader() {
    return loader;
  }

  @ApiStatus.Internal
  public void setLoader(AuspiceLoader loader) {
    this.loader = loader;
  }

  public @NotNull File getDataFolder() {
    return this.dataFolder;
  }

  @ApiStatus.Internal
  public void setDataFolder(@NotNull File dataFolder) {
    this.dataFolder = dataFolder;
  }

  public Path getPath(String path) {
    return this.getDataFolder().toPath().resolve(path);
  }

  public static @NotNull Key createKey(@KeyPatterns.KeyValue final @NotNull String keyValue) {
    return Key.key(NAMESPACE, keyValue);
  }

  public static void init() {
    StandardDiversity.init();
    DefaultAuspicePluginPermissions.init();
  }

  static {
    init();
  }
}
