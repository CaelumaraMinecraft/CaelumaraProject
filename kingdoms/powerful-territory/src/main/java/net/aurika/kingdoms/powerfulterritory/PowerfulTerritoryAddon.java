package net.aurika.kingdoms.powerfulterritory;

import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistry;
import net.aurika.kingdoms.auspice.util.AddonTemplate;
import net.aurika.kingdoms.auspice.util.KingdomsNamingContract;
import net.aurika.kingdoms.powerfulterritory.commands.admin.land.CommandAdminLand;
import net.aurika.kingdoms.powerfulterritory.commands.admin.registry.CommandAdminRegistryLandCategory;
import net.aurika.kingdoms.powerfulterritory.commands.admin.registry.CommandAdminRegistryLandContraction;
import net.aurika.kingdoms.powerfulterritory.commands.admin.registry.CommandAdminRegistryLandLease;
import net.aurika.kingdoms.powerfulterritory.commands.general.land.CommandLand;
import net.aurika.kingdoms.powerfulterritory.configs.PowerfulTerritoryConfig;
import net.aurika.kingdoms.powerfulterritory.configs.PowerfulTerritoryLang;
import net.aurika.kingdoms.powerfulterritory.configs.PowerfulTerritoryPlaceholder;
import net.aurika.kingdoms.powerfulterritory.constant.invade_protection.StandardInvadeProtection;
import net.aurika.kingdoms.powerfulterritory.constant.land.category.LandCategoryRegistry;
import net.aurika.kingdoms.powerfulterritory.constant.land.category.StandardLandCategory;
import net.aurika.kingdoms.powerfulterritory.constant.land.contraction.LandContractionRegistry;
import net.aurika.kingdoms.powerfulterritory.constant.land.contraction.std.StandardLandContraction;
import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.LandLeaseProjectRegistry;
import net.aurika.kingdoms.powerfulterritory.constant.land.structure.type.StructureTypeSturdyCore;
import net.aurika.kingdoms.powerfulterritory.data.*;
import net.aurika.kingdoms.powerfulterritory.managers.*;
import net.aurika.kingdoms.powerfulterritory.util.GroupExt;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.kingdoms.commands.admin.CommandAdmin;
import org.kingdoms.config.KingdomsConfig;
import org.kingdoms.constants.land.structures.StructureRegistry;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.locale.LanguageManager;
import org.kingdoms.main.Kingdoms;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class PowerfulTerritoryAddon extends AddonTemplate {

  public static final String KEY = "powerful-territory";

  private static PowerfulTerritoryAddon instance;

  public static @NotNull Namespace buildNS(@KingdomsNamingContract.Namespace.Key final @NotNull String key) {
    // noinspection PatternValidation
    return new Namespace("PowerfulTerritory", key);
  }

  private final LandCategoryRegistry landCategoryRegistry = new LandCategoryRegistry();
  private final LandContractionRegistry landContractionRegistry = new LandContractionRegistry();
  private final LandLeaseProjectRegistry landLeaseProjectRegistry = new LandLeaseProjectRegistry();
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

    landLeaseProjectRegistry.registerDefaults();

    StandardInvadeProtection.init();
    StandardLandCategory.init();
    LandCategories.registerLandCategoryExternalMessageContextEdit();
    StandardLandContraction.init();
  }

  @Override
  public void onEnable0() {
    registerAllEvents();
    registerAllCommands();
    reloadAddon0();
  }

  @Override
  public void onDisable0() { }

  @Override
  public void reloadAddon0() {
    registerAllBuildings();
  }

  @Override
  public @NotNull String getAddonName() {
    return "powerful-territory";
  }

  public void registerAllBuildings() {
    StructureRegistry.get().registerType(StructureTypeSturdyCore.INSTANCE);
  }

  public static void printStructures() {
    System.out.println(Arrays.toString((StructureRegistry.get().getTypes().keySet()).toArray()));
  }

  public void registerAllEvents() {

    if (PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_ELYTRA.getManager().getBoolean()) {
      registerEvents(ElytraManager.INSTANCE);
    }
    if (PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_ENDER_PEARL.getManager().getBoolean()) {
      registerEvents(EnderPearlTeleportManager.INSTANCE);
      PlayerTeleportEvent.getHandlerList().unregister(Kingdoms.get());
    }
    if (
        KingdomsConfig.Claims.BEACON_PROTECTED_EFFECTS.getManager().getBoolean()
            && PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_RELATION_ATTRIBUTE_BEACON_EFFECTS.getManager().getBoolean()
    ) {
      registerEvents(BeaconEffectListener.INSTANCE);
      EntityPotionEffectEvent.getHandlerList().unregister(Kingdoms.get());
    }
    if (PowerfulTerritoryConfig.METICULOUS_LAND_PROTECTION_KINGDOM_PERMISSION_BOAT.getManager().getBoolean()) {
      registerEvents(BoatUseListener.INSTANCE);
    }

    registerEvents(ExplosionManager.INSTANCE);
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
    new CommandAdminRegistryLandLease(command_admin_registry);
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

  public @NotNull LandCategoryRegistry landCategoryRegistry() {
    return landCategoryRegistry;
  }

  public @NotNull LandContractionRegistry landContractionRegistry() {
    return landContractionRegistry;
  }

  public @NotNull LandLeaseProjectRegistry landLeaseProjectRegistry() {
    return landLeaseProjectRegistry;
  }

  public static @NotNull PowerfulTerritoryAddon get() {
    if (instance == null) {
      throw new IllegalStateException("PowerfulTerritory has not been initialized yet.");
    }
    return instance;
  }

}
