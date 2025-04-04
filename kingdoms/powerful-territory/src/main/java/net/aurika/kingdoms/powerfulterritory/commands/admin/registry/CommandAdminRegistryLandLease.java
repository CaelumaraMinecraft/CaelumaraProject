package net.aurika.kingdoms.powerfulterritory.commands.admin.registry;

import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistryTemplate;
import net.aurika.kingdoms.powerfulterritory.PowerfulTerritoryAddon;
import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.LandLeaseProjectRegistry;
import net.aurika.kingdoms.powerfulterritory.constant.land.lease.project.type.LandLeaseProjectType;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRegistryLandLease extends CommandAdminRegistryTemplate<LandLeaseProjectType<?>, LandLeaseProjectRegistry> {

  public CommandAdminRegistryLandLease(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryLandLease(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    // noinspection unchecked, rawtypes
    super(
        "land" + "lease",
        parent,
        permissionDefault,
        PowerfulTerritoryAddon.get().landLeaseProjectRegistry(),
        (Class) LandLeaseProjectType.class
    );
  }

}
