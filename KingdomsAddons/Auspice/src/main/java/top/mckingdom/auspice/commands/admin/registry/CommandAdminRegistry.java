package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRegistry extends KingdomsParentCommand {

    public CommandAdminRegistry(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistry(@Nullable KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
        super("registry", parent, permissionDefault);
        new CommandAdminRegistryKingdomPermission(this);
        new CommandAdminRegistryMetadata(this);
        if (Bukkit.getPluginManager().isPluginEnabled("Kingdoms-Addon-Peace-Treaties")) {

        }
    }
}
