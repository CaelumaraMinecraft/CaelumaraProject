package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.group.model.logs.AuditLogProvider;
import org.kingdoms.constants.group.model.logs.AuditLogRegistry;
import org.kingdoms.main.Kingdoms;

public class CommandAdminRegistryAuditLog extends CommandAdminRegistryTemplate<AuditLogProvider, AuditLogRegistry> {

  public CommandAdminRegistryAuditLog(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryAuditLog(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super("audit" + "log", parent, permissionDefault, Kingdoms.get().getAuditLogRegistry(), AuditLogProvider.class);
  }

}
