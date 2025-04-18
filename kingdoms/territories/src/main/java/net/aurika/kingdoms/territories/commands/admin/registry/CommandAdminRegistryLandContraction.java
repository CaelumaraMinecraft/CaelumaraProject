package net.aurika.kingdoms.territories.commands.admin.registry;

import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistryTemplate;
import net.aurika.kingdoms.territories.TerritoriesAddon;
import net.aurika.kingdoms.territories.constant.land.contraction.LandContraction;
import net.aurika.kingdoms.territories.constant.land.contraction.LandContractionRegistry;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRegistryLandContraction extends CommandAdminRegistryTemplate<LandContraction, LandContractionRegistry> {

  public CommandAdminRegistryLandContraction(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryLandContraction(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super(
        "land" + "contraction",
        parent,
        permissionDefault,
        TerritoriesAddon.get().landContractionRegistry(),
        LandContraction.class
    );
  }

}
