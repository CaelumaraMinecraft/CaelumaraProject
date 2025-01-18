package top.auspice.wandscraftsmanship.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.auspice.bukkit.api.BukkitPluginAuspiceUser;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.StandardDiversity;
import top.auspice.key.NSKey;

public final class WandsCraftsmanship extends JavaPlugin implements BukkitPluginAuspiceUser {
    private static WandsCraftsmanship instance;
    public static final String NAMESPACE = "WandsCraftsmanship";

    private State state;

    public WandsCraftsmanship() {
        instance = this;
        this.state = State.INITIATING;
        registerAuspiceUser();

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
    @AuspiceUserName
    public @NotNull String getAuspiceUserName() {
        return "WandsCraftsmanship";
    }

    @Override
    @NSKey.Namespace
    public @NotNull String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public @NotNull Diversity getDefaultDiversity() {
        return StandardDiversity.SIMPLIFIED_CHINESE;
    }

    @Override
    public @NotNull State getState() {
        return this.state;
    }

    public static WandsCraftsmanship get() {
        return instance;
    }
}
