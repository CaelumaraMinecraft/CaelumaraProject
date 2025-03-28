package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.group.model.relationships.RelationAttribute;
import org.kingdoms.constants.group.model.relationships.RelationAttributeRegistry;
import org.kingdoms.main.Kingdoms;

public class CommandAdminRegistryRelationAttribute extends CommandAdminRegistryTemplate<RelationAttribute, RelationAttributeRegistry> {

  public CommandAdminRegistryRelationAttribute(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryRelationAttribute(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super("relation" + "attribute", parent, permissionDefault, Kingdoms.get().getRelationAttributeRegistry(), RelationAttribute.class);
  }

}
