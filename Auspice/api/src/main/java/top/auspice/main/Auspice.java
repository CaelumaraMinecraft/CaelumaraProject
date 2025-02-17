package top.auspice.main;

import net.aurika.dependency.DependencyManager;
import net.aurika.dependency.classpath.BootstrapProvider;
import net.aurika.namespace.NSKey;
import net.aurika.namespace.NSedKey;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.api.user.AuspiceUser;
import top.auspice.data.centers.AuspiceDataCenter;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.StandardDiversity;
import top.auspice.loader.AuspiceLoader;
import top.auspice.permission.DefaultAuspicePluginPermissions;

import java.io.File;
import java.nio.file.Path;

public final class Auspice implements AuspiceUser {
    public static final @KeyPattern.Namespace String NAMESPACE = "auspice";
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
    public @NotNull State getState() {
        return this.state;
    }

    @AuspiceUserName
    @Override
    public @NotNull String getAuspiceUserName() {
        return "Auspice";
    }

    @Override
    public @NotNull Diversity getDefaultDiversity() {
        return StandardDiversity.SIMPLIFIED_CHINESE;
    }

    @Override
    @KeyPattern.Namespace
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

    public static @NotNull NSedKey namespacedKey(@NotNull @NSKey.Key String key) {
        return NSedKey.of(NAMESPACE, key);
    }

    public static @NotNull Key key_advtr(@KeyPattern.Value @NotNull String value) {
        return Key.key(NAMESPACE, value);
    }

    public static void init() {
        StandardDiversity.init();
        DefaultAuspicePluginPermissions.init();
    }

    static {
        init();
    }
}
