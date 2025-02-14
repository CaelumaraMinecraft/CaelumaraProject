package top.mckingdom.supreme_kingdom;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;

import java.io.File;

public class SupremeKingdomAddon extends JavaPlugin implements Addon {


    @Override
    public void onLoad() {

    }

    public void onEnable() {

    }


    @Override
    public void reloadAddon() {

    }

    @Override
    public void uninstall() {

    }

    @Override
    public String getAddonName() {
        return "supreme-kingdom";
    }

    @NotNull
    @Override
    public File getFile() {
        return super.getFile();
    }


    public void registerHandlers() {

    }

}
