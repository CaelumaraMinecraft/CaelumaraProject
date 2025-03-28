package top.mckingdom.powerfulterritory.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import top.mckingdom.auspice.commands.admin.registry.CommandAdminRegistryTemplate;
import top.mckingdom.powerfulterritory.PowerfulTerritoryAddon;
import top.mckingdom.powerfulterritory.constants.land_contractions.LandContraction;
import top.mckingdom.powerfulterritory.constants.land_contractions.LandContractionRegistry;

public class CommandAdminRegistryLandContraction extends CommandAdminRegistryTemplate<LandContraction, LandContractionRegistry> {

  public CommandAdminRegistryLandContraction(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryLandContraction(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super("land" + "contraction", parent, permissionDefault, PowerfulTerritoryAddon.get().getLandContractionRegistry(), LandContraction.class);
  }

}
