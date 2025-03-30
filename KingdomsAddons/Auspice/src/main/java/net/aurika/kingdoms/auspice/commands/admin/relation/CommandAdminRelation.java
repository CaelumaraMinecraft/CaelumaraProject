package net.aurika.kingdoms.auspice.commands.admin.relation;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRelation extends KingdomsParentCommand {
  public CommandAdminRelation(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRelation(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super("relation", parent, permissionDefault);
    new CommandAdminRelationAttribute(this);
  }

}
