package top.mckingdom.props;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.abstraction.processor.KingdomsProcess;
import org.kingdoms.addons.Addon;
import org.kingdoms.managers.land.InteractProcessor;
import top.mckingdom.auspice.utils.permissions.KingdomPermissionRegister;
import top.mckingdom.auspice.utils.permissions.XKingdomPermission;

import java.io.File;

public class PropsAddon extends JavaPlugin implements Addon {

    public static final XKingdomPermission PERMISSION_USE_PROPS = KingdomPermissionRegister.register("PropsAddon", "USE_PROPS");


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
