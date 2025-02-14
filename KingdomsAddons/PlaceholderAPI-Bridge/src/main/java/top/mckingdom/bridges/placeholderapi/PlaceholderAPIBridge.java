package top.mckingdom.bridges.placeholderapi;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.kingdoms.addons.Addon;
import org.kingdoms.locale.compiler.placeholders.Placeholder;
import org.kingdoms.locale.compiler.placeholders.PlaceholderLexer;
import org.kingdoms.locale.compiler.placeholders.PlaceholderParser;

import java.io.File;

public class PlaceholderAPIBridge extends JavaPlugin implements Addon {


    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        Placeholder
    }

    @Override
    public void reloadAddon() {
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public String getAddonName() {
        return "PlaceholderAPI-Bridge";
    }

    @Override
    public File getFile() {
        return super.getFile();
    }

}