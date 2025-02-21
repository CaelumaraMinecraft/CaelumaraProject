package net.aurika.auspice.wandscraftsmanship.main;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.game.bukkit.api.BukkitPluginAuspiceUser;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.diversity.StandardDiversity;
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
    public @NotNull String auspiceUserName() {
        return "WandsCraftsmanship";
    }

    @Override
    @NSKey.Namespace
    public @NotNull String getNamespace() {
        return NAMESPACE;
    }

    @Override
    public @NotNull Diversity defaultDiversity() {
        return StandardDiversity.SIMPLIFIED_CHINESE;
    }

    @Override
    public @NotNull State state() {
        return this.state;
    }

    public static WandsCraftsmanship get() {
        return instance;
    }
}
