package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.metadata.KingdomMetadataHandler;
import org.kingdoms.constants.metadata.KingdomMetadataRegistry;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandList;

import java.util.Map;

public class CommandAdminRegistryMetadata extends KingdomsParentCommand {
    public CommandAdminRegistryMetadata(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryMetadata(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
        super("metadata", parent, permissionDefault);
        new RegistryOperatorCommandList<>("list", this, permissionDefault, Kingdoms.get().getMetadataRegistry(), KingdomMetadataHandler.class) {

            @Override
            protected @NotNull Map<String, ? extends RegistryOperatorCommandList<KingdomMetadataHandler, KingdomMetadataRegistry>.ListOperation> availableOperations() {
                return Map.of(
                        "hashcode", new HashCode(),
                        "key-full-string", new KeyToFullString()
                );
            }
        };
    }
}
