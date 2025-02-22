package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.constants.player.KingdomPermissionRegistry;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandList;

import java.util.Map;

public class CommandAdminRegistryKingdomPermission extends KingdomsParentCommand {

    public CommandAdminRegistryKingdomPermission(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryKingdomPermission(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
        super("kingdompermission", parent, permissionDefault);
        new RegistryOperatorCommandList<>("list", this, permissionDefault, Kingdoms.get().getPermissionRegistery(), KingdomPermission.class) {

            @Override
            protected @NotNull Map<String, ? extends RegistryOperatorCommandList<KingdomPermission, KingdomPermissionRegistry>.ListOperation> availableOperations() {
                return Map.of(
                        "hashcode", new HashCode(),
                        "key-full-string", new KeyToFullString()
                );
            }
        };
    }
}
