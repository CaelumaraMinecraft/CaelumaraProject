package net.aurika.kingdoms.territories.commands.admin.registry;

import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistryTemplate;
import net.aurika.kingdoms.territories.TerritoriesAddon;
import net.aurika.kingdoms.territories.constant.land.lease.project.LandLeaseProjectRegistry;
import net.aurika.kingdoms.territories.constant.land.lease.project.type.LandLeaseProjectType;
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
        TerritoriesAddon.get().landLeaseProjectRegistry(),
        (Class) LandLeaseProjectType.class
    );
  }

}
