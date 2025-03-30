package top.mckingdom.powerfulterritory;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.admin.CommandAdmin;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;
import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistry;
import net.aurika.kingdoms.auspice.util.AddonTemplate;
import top.mckingdom.powerfulterritory.commands.admin.land.CommandAdminLand;
import top.mckingdom.powerfulterritory.commands.admin.registry.CommandAdminRegistryLandCategory;
import top.mckingdom.powerfulterritory.commands.admin.registry.CommandAdminRegistryLandContraction;
import top.mckingdom.powerfulterritory.commands.general.land.CommandLand;
import top.mckingdom.powerfulterritory.configs.PowerfulTerritoryConfig;
import top.mckingdom.powerfulterritory.configs.PowerfulTerritoryLang;
import top.mckingdom.powerfulterritory.configs.PowerfulTerritoryPlaceholder;
import top.mckingdom.powerfulterritory.constants.invade_protection.StandardInvadeProtection;
import top.mckingdom.powerfulterritory.constants.land_categories.LandCategoryRegistry;
import top.mckingdom.powerfulterritory.constants.land_categories.StandardLandCategory;
import top.mckingdom.powerfulterritory.constants.land_contractions.LandContractionRegistry;
import top.mckingdom.powerfulterritory.constants.land_contractions.std.StandardLandContraction;
import top.mckingdom.powerfulterritory.data.*;
import top.mckingdom.powerfulterritory.managers.BeaconEffectsManager;
import top.mckingdom.powerfulterritory.managers.BoatUseManager;
import top.mckingdom.powerfulterritory.managers.ElytraManager;
import top.mckingdom.powerfulterritory.managers.EnderPearlTeleportManager;
import top.mckingdom.powerfulterritory.util.GroupExt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PowerfulTerritoryAddon extends AddonTemplate {

  public static final String CONFIG_HEAD = "powerful-territory";

  private static PowerfulTerritoryAddon instance;

  public static @NotNull Namespace buildNS(@NotNull String key) {
    return new Namespace("PowerfulTerritory", key);
  }

  private final LandCategoryRegistry landCategoryRegistry = new LandCategoryRegistry();
  private final LandContractionRegistry landContractionRegistry = new LandContractionRegistry();
  private final Set<KingdomMetadataHandler> landMetadataHandlers = new HashSet<>();

  public PowerfulTerritoryAddon() {
    super();
  }

  @Override
  protected void onInit0() {
    instance = this;
  }

  @Override
  public void onLoad0() {

    GroupExt.init();

    PowerfulTerritoryPlaceholder.init();

    LanguageManager.registerMessenger(PowerfulTerritoryLang.class);

    registerAllMetadataHandlers();

    StandardInvadeProtection.init();
    StandardLandCategory.init();
    LandCategories.registerLandCategoryExternalMessageContextEdit();
    StandardLandContraction.init();
  }

  @Override
  public void onEnable0() {
    registerAllEvents();
    registerAllCommands();
  }

  @Override
  public void onDisable0() {

  }

  @Override
  public void reloadAddon0() {
    registerAllCommands();

    registerAllEvents();
  }

  @Override
  public @NotNull String getAddonName() {
    return "powerful-territory";
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

    new CommandLand();
    new CommandAdminLand(CommandAdmin.getInstance());

    CommandAdminRegistry command_admin_registry = CommandAdminRegistry.getInstance();
    new CommandAdminRegistryLandCategory(command_admin_registry);
    new CommandAdminRegistryLandContraction(command_admin_registry);
  }

  public void registerAllMetadataHandlers() {
    KingdomMetadataRegistry mr = Kingdoms.get().getMetadataRegistry();

    landMetadataHandlers.addAll(Arrays.asList(
        LandCategoryMetaHandler.INSTANCE,
        LandContractionsMetaHandler.INSTANCE
    ));
    landMetadataHandlers.forEach(mr::register);
  }

  public @NotNull LandCategoryRegistry getLandCategoryRegistry() {
    return this.landCategoryRegistry;
  }

  public @NotNull LandContractionRegistry getLandContractionRegistry() {
    return this.landContractionRegistry;
  }

  public static @NotNull PowerfulTerritoryAddon get() {
    if (instance == null) {
      throw new IllegalStateException("PowerfulTerritory has not been initialized yet.");
    }
    return instance;
  }

}
