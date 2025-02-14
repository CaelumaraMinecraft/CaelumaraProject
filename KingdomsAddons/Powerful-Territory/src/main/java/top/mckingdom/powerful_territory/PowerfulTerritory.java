package top.mckingdom.powerful_territory;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.addons.Addon;
import org.kingdoms.commands.admin.CommandAdmin;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.admin.relation_attribute.CommandAdminRelationAttribute;
import top.mckingdom.powerful_territory.commands.admin.land.CommandAdminLand;
import top.mckingdom.powerful_territory.commands.general.land.CommandLand;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryConfig;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerful_territory.configs.PowerfulTerritoryPlaceholders;
import top.mckingdom.powerful_territory.data.Categories;
import top.mckingdom.powerful_territory.data.Contractions;
import top.mckingdom.powerful_territory.data.LandCategoryMetaHandler;
import top.mckingdom.powerful_territory.data.LandContractionsMetaHandler;
import top.mckingdom.powerful_territory.constants.invade_protection.StandardInvadeProtection;
import top.mckingdom.powerful_territory.constants.land_categories.LandCategoryRegistry;
import top.mckingdom.powerful_territory.constants.land_categories.StandardLandCategory;
import top.mckingdom.powerful_territory.constants.land_contractions.LandContractionRegistry;
import top.mckingdom.powerful_territory.constants.land_contractions.std.StandardLandContraction;
import top.mckingdom.powerful_territory.managers.BeaconEffectsManager;
import top.mckingdom.powerful_territory.managers.BoatUseManager;
import top.mckingdom.powerful_territory.managers.ElytraManager;
import top.mckingdom.powerful_territory.managers.EnderPearlTeleportManager;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PowerfulTerritory extends JavaPlugin implements Addon {
    private static PowerfulTerritory instance;

    private final LandCategoryRegistry landCategoryRegistry = new LandCategoryRegistry();
    private final LandContractionRegistry landContractionRegistry = new LandContractionRegistry();

    private final Set<KingdomMetadataHandler> landMetadataHandlers = new HashSet<>();

    public static Namespace buildNS(String key) {
        return new Namespace("PowerfulTerritory", key);
    }

    public PowerfulTerritory() {
        instance = this;
    }

    @Override
    public void onLoad() {

        GroupExt.init();

        PowerfulTerritoryPlaceholders.init();

        LanguageManager.registerMessenger(PowerfulTerritoryLang.class);

        registerAllMetadataHandlers();

        StandardInvadeProtection.init();
        StandardLandCategory.init();
        StandardLandContraction.init();

    }

    @Override
    public void onEnable() {

        registerAllEvents();

        registerAllCommands();

        registerAddon();
    }

    @Override
    public void onDisable() {

        signalDisable();
        // Plugin shutdown logic
    }

    @Override
    public void reloadAddon() {
        registerAllCommands();

        registerAllEvents();
    }

    @Override
    public String getAddonName() {
        return "powerful-territory";
    }


    @NotNull
    @Override
    public File getFile() {
        return super.getFile();
    }





    public void registerAllEvents() {

        if (PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_ELYTRA.getManager().getBoolean()) {
            registerEvents(new ElytraManager());
        }
        if (PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_ENDER_PEARL.getManager().getBoolean()) {
            registerEvents(new EnderPearlTeleportManager());
            PlayerTeleportEvent.getHandlerList().unregister(Kingdoms.get());
        }
        if (
                KingdomsConfig.Claims.BEACON_PROTECTED_EFFECTS.getManager().getBoolean()
                && PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_BEACON_EFFECTS.getManager().getBoolean()
        ) {
            registerEvents(new BeaconEffectsManager());
            EntityPotionEffectEvent.getHandlerList().unregister(Kingdoms.get());
        }
        if (PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_KINGDOM_PERMISSION_BOAT.getManager().getBoolean()) {
            registerEvents(new BoatUseManager());
        }

    }

    private void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerAllCommands() {

        Categories.initialize();
        Contractions.initialize();


        new CommandAdminRelationAttribute(CommandAdmin.getInstance());
        new CommandLand();
        new CommandAdminLand(CommandAdmin.getInstance());

    }


    public void registerAllMetadataHandlers() {
        KingdomMetadataRegistry mr = Kingdoms.get().getMetadataRegistry();

        landMetadataHandlers.addAll(Arrays.asList(
                LandCategoryMetaHandler.INSTANCE,
                LandContractionsMetaHandler.INSTANCE
        ));
        landMetadataHandlers.forEach(mr::register);

    }





    public LandCategoryRegistry getLandCategoryRegistry() {
        return this.landCategoryRegistry;
    }
    public LandContractionRegistry getLandContractionRegistry() {
        return landContractionRegistry;
    }

    public static PowerfulTerritory get() {
        return instance;
    }

}
