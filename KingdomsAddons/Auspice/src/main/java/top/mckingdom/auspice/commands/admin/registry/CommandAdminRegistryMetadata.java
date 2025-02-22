package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRegistryMetadata extends KingdomsParentCommand {
    public CommandAdminRegistryMetadata(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryMetadata(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
        super("metadata", parent, permissionDefault);
    }
}
