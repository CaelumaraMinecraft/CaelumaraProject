package top.mckingdom.trade_point;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.constants.namespace.Namespace;

import java.io.File;

public final class TradePointAddon extends JavaPlugin implements Addon {

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public void reloadAddon() {

    }

    @Override
    public String getAddonName() {
        return "trade-point";
    }

    @NotNull
    @Override
    public File getFile() {
        return super.getFile();
    }

    public static Namespace buildNS(String key) {
        return new Namespace("TradePoint", key);
    }
}
