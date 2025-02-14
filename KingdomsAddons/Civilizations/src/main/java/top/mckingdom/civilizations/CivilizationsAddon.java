package top.mckingdom.civilizations;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.constants.namespace.Namespace;
import top.mckingdom.civilizations.config.CivilizationsAddonConfig;
import top.mckingdom.civilizations.constants.civilization.member.CivilizationMemberTypeRegistry;
import top.mckingdom.civilizations.constants.civilization.member.types.CivilizationMemberTypeNation;
import top.mckingdom.civilizations.constants.civilization.permission.StandardCivilizationPermission;
import top.mckingdom.civilizations.constants.civilization.relation.StandardCivilizationRelation;

import java.io.File;

public class CivilizationsAddon extends JavaPlugin implements Addon {

    private static CivilizationsAddon instance;

    public CivilizationsAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        CivilizationsAddonConfig.init();
        init();
        reloadConfigurationThings();

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void reloadAddon() {

    }

    @Override
    public void uninstall() {

    }

    @Override
    public String getAddonName() {
        return "civilizations";
    }

    @NotNull
    @Override
    public File getFile() {
        return super.getFile();
    }

    public static Namespace buildNS(String key) {
        return new Namespace("Civilizations", key);
    }

    private static void init() {
        CivilizationMemberTypeRegistry.registerDefaults();
        CivilizationsKP.init();
    }
    private static void reloadConfigurationThings() {
        StandardCivilizationRelation.init();
    }


    public static CivilizationsAddon get() {
        return instance;
    }

}
