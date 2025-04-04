package net.aurika.kingdoms.powerfulterritory.commands.admin.registry;

import net.aurika.kingdoms.auspice.commands.admin.registry.CommandAdminRegistryTemplate;
import net.aurika.kingdoms.powerfulterritory.PowerfulTerritoryAddon;
import net.aurika.kingdoms.powerfulterritory.constant.land.category.LandCategory;
import net.aurika.kingdoms.powerfulterritory.constant.land.category.LandCategoryRegistry;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRegistryLandCategory extends CommandAdminRegistryTemplate<LandCategory, LandCategoryRegistry> {

  public CommandAdminRegistryLandCategory(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryLandCategory(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super(
        "land" + "category", parent, permissionDefault, PowerfulTerritoryAddon.get().getLandCategoryRegistry(),
        LandCategory.class
    );
  }

}
