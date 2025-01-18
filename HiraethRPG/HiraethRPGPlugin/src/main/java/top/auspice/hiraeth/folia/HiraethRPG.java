package top.auspice.hiraeth.folia;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.user.AuspiceUser;
import top.auspice.key.NSKey;
import top.auspice.key.NSedKey;

public final class HiraethRPG extends JavaPlugin implements BukkitPluginAuspiceUser {
    private static HiraethRPG instance;
    @NSKey.Namespace
    public static final String NAMESPACE = "HiraethRPG";

    private AuspiceUser.State state;

    public HiraethRPG() {
        this.state = AuspiceUser.State.INITIATING;
        instance = this;
        registerAuspiceUser();

        this.state = AuspiceUser.State.INITIATED;
    }

    public void onLoad() {
        this.state = AuspiceUser.State.LOADING;


        this.state = AuspiceUser.State.LOADED;
    }

    public void onEnable() {
        this.state = AuspiceUser.State.ENABLING;


        this.state = AuspiceUser.State.ENABLED;
    }

    public void onDisable() {
        this.state = AuspiceUser.State.DISABLING;

        this.state = AuspiceUser.State.DISABLED;
    }

    public @NotNull String getAuspiceUserName() {
        return "Hiraeth-RPG";
    }

    @NSKey.Namespace
    public @NotNull String getNamespace() {
        return NAMESPACE;
    }

    public @NotNull AuspiceUser.State getState() {
        return this.state;
    }

    public static NSedKey buildNS(String key) {
        return new NSedKey(NAMESPACE, key);
    }

    public static HiraethRPG get() {
        return instance;
    }
}
