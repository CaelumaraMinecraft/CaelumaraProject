package top.mckingdom.props;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;
import top.mckingdom.auspice.util.permission.XKingdomPermissionFactory;
import top.mckingdom.auspice.util.permission.XKingdomPermission;

import java.io.File;

public class PropsAddon extends JavaPlugin implements Addon {

    public static final XKingdomPermission PERMISSION_USE_PROPS = XKingdomPermissionFactory.create("PropsAddon", "USE_PROPS");


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
        return "PropsAddon";
    }

    @NotNull
    @Override
    public File getFile() {
        return super.getFile();
    }


    public void registerHandlers() {

    }

}
