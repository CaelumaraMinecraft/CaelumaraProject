package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.group.model.relationships.RelationAttributeRegistry;
import org.kingdoms.main.Kingdoms;
import top.mckingdom.auspice.commands.admin.registry.operator.RegistryOperatorCommandList;

import java.util.Map;

public class CommandAdminRegistryRelationAttribute extends KingdomsParentCommand {

    public CommandAdminRegistryRelationAttribute(@Nullable KingdomsParentCommand parent) {
        this(parent, PermissionDefault.OP);
    }

    public CommandAdminRegistryRelationAttribute(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
        super("relationattribute", parent, permissionDefault);
        new RegistryOperatorCommandList<>("list", this, permissionDefault, Kingdoms.get().getRelationAttributeRegistry(), RelationAttribute.class) {

            @Override
            protected @NotNull Map<String, ? extends RegistryOperatorCommandList<RelationAttribute, RelationAttributeRegistry>.ListOperation> availableOperations() {
                return Map.of(
                        "hashcode", new HashCode(),
                        "key-full-string", new KeyToFullString()
                );
            }
        };
    }
}
