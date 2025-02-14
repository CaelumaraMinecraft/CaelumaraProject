package top.mckingdom.auspice;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.configs.AuspicePlaceholder;
import top.mckingdom.auspice.configs.CustomConfigValidators;
import top.mckingdom.auspice.costs.CurrencyRegistry;
import top.mckingdom.auspice.services.ServiceBStats;
import top.mckingdom.auspice.utils.permissions.KingdomPermissionRegister;
import top.mckingdom.auspice.utils.permissions.RelationAttributeRegister;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class AuspiceAddon extends JavaPlugin implements Addon {

    public static ServiceBStats B_STATS;
    private static AuspiceAddon instance;

    private final Set<KingdomMetadataHandler> landMetadataHandlers = new HashSet<>();   // Land 的元数据存储器


    private static boolean enabled = false;

    public AuspiceAddon() {
        instance = this;
    }

    @Override
    public void onLoad() {
        if (!isKingdomsLoaded()) return;

        landMetadataHandlers.addAll(Arrays.asList(

        ));
        landMetadataHandlers.forEach(metaHandler -> {
            Kingdoms.get().getMetadataRegistry().register(metaHandler);
        });

        LanguageManager.registerMessenger(AuspiceLang.class);

        KingdomPermissionRegister.init();
        RelationAttributeRegister.init();

        CurrencyRegistry.init();

        CustomConfigValidators.init();

        getLogger().info("Addon is loading...");
    }


    @Override
    public void onEnable() {

        if (!isKingdomsEnabled()) {
            getLogger().severe("Kingdoms plugin didn't load correctly, disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

//        B_STATS = new ServiceBStats(this, 22764);


        getLogger().info("Addon is enabling...");

        getLogger().info("Registering event listeners...");

        getLogger().info("Registering commands...");
        registerAllCommands();

        AuspicePlaceholder.init();

        registerAddon();

        enabled = true;
    }

    @Override
    public void onDisable() {
        getLogger().info("Addon is disabling...");
//        B_STATS.shutdown();
        signalDisable();
    }

    @Override
    public void reloadAddon() {
        getLogger().info("Registering event listeners...");

        getLogger().info("Registering commands...");
        registerAllCommands();
    }

    @Override
    public void uninstall() {
        getLogger().info("Removing auspice addon metadata...");
        KingdomMetadataRegistry.removeMetadata(Kingdoms.get().getDataCenter().getLandManager(), landMetadataHandlers);

        Kingdoms.get().getDataCenter().getKingdomManager().getKingdoms().forEach(kingdom -> {

            kingdom.getGroup().getAttributes().values().forEach(attrSet -> {
                attrSet.remove(RelationAttributeRegister.DIRECTLY_TRANSFER_MEMBERS);
            });

            kingdom.getRanks().forEach(rank -> {
                rank.getPermissions().remove(KingdomPermissionRegister.PERMISSION_TRANSFER_MEMBERS);
            });

            ;
        });

        //TODO get all lands of the server


    }

    @Override
    public String getAddonName() {
        return "auspice";
    }

    @NonNull
    @Override
    public File getFile() {
        return super.getFile();
    }


    private void registerAllCommands() {
    }


    public static AuspiceAddon get() {
        return instance;
    }

    public static boolean isAuspiceAddonEnabled() {
        return enabled;
    }


    /**
     * Only use for this addon
     */
    public static Namespace buildNS(String s) {
        return new Namespace("AuspiceAddon", s);
    }
}
