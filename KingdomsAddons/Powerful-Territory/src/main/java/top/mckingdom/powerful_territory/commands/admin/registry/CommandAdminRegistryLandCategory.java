package top.mckingdom.powerful_territory.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import top.mckingdom.auspice.commands.admin.registry.CommandAdminRegistryTemplate;
import top.mckingdom.powerful_territory.PowerfulTerritoryAddon;
import top.mckingdom.powerful_territory.constants.land_categories.LandCategory;
import top.mckingdom.powerful_territory.constants.land_categories.LandCategoryRegistry;

public class CommandAdminRegistryLandCategory extends CommandAdminRegistryTemplate<LandCategory, LandCategoryRegistry> {

    public CommandAdminRegistryLandCategory(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryLandCategory(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
        super("land" + "category", parent, permissionDefault, PowerfulTerritoryAddon.get().getLandCategoryRegistry(), LandCategory.class);
    }
}
