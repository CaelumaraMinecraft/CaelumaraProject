package top.mckingdom.uninvade;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.commands.admin.CommandAdmin;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.uninvade.commands.admin.CommandAdminUninvade;
import top.mckingdom.uninvade.config.UninvadeAddonLang;
import top.mckingdom.uninvade.data.LandInvadeProtectionDataManager;
import top.mckingdom.uninvade.data.StandardInvadeProtection;
import top.mckingdom.uninvade.managers.InvadeManager;

import java.io.File;

import static top.mckingdom.uninvade.data.LandInvadeProtectionDataManager.PROTECTION_STATUS_META_HANDLER;

public class UninvadeAddon extends JavaPlugin implements Addon {

    private static UninvadeAddon instance;


    public UninvadeAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        LandInvadeProtectionDataManager.init();
        StandardInvadeProtection.init();
        Kingdoms.get().getMetadataRegistry().register(PROTECTION_STATUS_META_HANDLER);
        LanguageManager.registerMessenger(UninvadeAddonLang.class);
    }

    public void onEnable() {
        getServer().getPluginManager().registerEvents(new InvadeManager(), this);
        new CommandAdminUninvade(CommandAdmin.getInstance());
        registerAddon();
    }


    @Override
    public void reloadAddon() {
        getServer().getPluginManager().registerEvents(new InvadeManager(), this);
        new CommandAdminUninvade(CommandAdmin.getInstance());
    }

    @Override
    public void uninstall() {
        Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach((k) -> k.getMetadata().remove(PROTECTION_STATUS_META_HANDLER));
    }

    @Override
    public String getAddonName() {
        return "uninvade";
    }

    @NotNull
    @Override
    public File getFile() {
        return super.getFile();
    }


    public static UninvadeAddon get() {
        return instance;
    }



}
