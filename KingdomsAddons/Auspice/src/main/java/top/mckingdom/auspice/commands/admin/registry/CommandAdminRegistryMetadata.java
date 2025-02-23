package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.main.Kingdoms;

public class CommandAdminRegistryMetadata extends CommandAdminRegistryTemplate<KingdomMetadataHandler, KingdomMetadataRegistry> {
    public CommandAdminRegistryMetadata(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryMetadata(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
        super("metadata", parent, permissionDefault, Kingdoms.get().getMetadataRegistry(), KingdomMetadataHandler.class);
    }
}
