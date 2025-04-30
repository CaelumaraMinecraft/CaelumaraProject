package net.aurika.auspice.user;

import net.aurika.auspice.data.centers.AuspiceDataCenter;
import net.aurika.auspice.loader.AuspiceLoader;
import net.aurika.auspice.permission.DefaultAuspicePluginPermissions;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.diversity.StandardDiversity;
import net.aurika.common.dependency.DependencyManager;
import net.aurika.common.dependency.classpath.BootstrapProvider;
import net.aurika.common.ident.Group;
import net.aurika.common.ident.Ident;
import net.aurika.common.ident.IdentPatterns;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public final class Auspice implements AuspiceUser {

  public static final String NAMESPACE = "auspice";
  @IdentPatterns.Group
  public static final @NotNull String GROUP_STRING = "net.aurika." + NAMESPACE;
  public static final Group GROUP = Group.group(GROUP_STRING);
  private static final Auspice INSTANCE = new Auspice();
  public static @NotNull Auspice get() {
    return INSTANCE;
  }
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
    return NAMESPACE;
  }

  @Override
  public @NotNull Diversity defaultDiversity() {
    return StandardDiversity.SIMPLIFIED_CHINESE;
  }

  @Override
  public @NotNull Group group() { return GROUP; }

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

  public @NotNull Path getPath(String path) {
    return this.getDataFolder().toPath().resolve(path);
  }

  public static @NotNull Ident createKey(@IdentPatterns.IdentPath final @NotNull String keyPath) {
    return Ident.ident(GROUP_STRING, keyPath);
  }

  static {
    StandardDiversity.init();
    DefaultAuspicePluginPermissions.init();
  }
}
