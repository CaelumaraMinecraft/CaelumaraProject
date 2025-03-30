package net.aurika.kingdoms.auspice.commands.admin.registry;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;

public class CommandAdminRegistry extends KingdomsParentCommand {
  private static @Nullable CommandAdminRegistry instance;

  public static @Nullable CommandAdminRegistry getInstance() {
    return instance;
  }

  public CommandAdminRegistry(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistry(@Nullable KingdomsParentCommand parent, @NotNull PermissionDefault permissionDefault) {
    super("registry", parent, permissionDefault);
    new CommandAdminRegistryKingdomPermission(this);
    new CommandAdminRegistryRelationAttribute(this);
    new CommandAdminRegistryAuditLog(this);
    new CommandAdminRegistryMetadata(this);
    new CommandAdminRegistryEconomy(this);
    if (Bukkit.getPluginManager().isPluginEnabled("Kingdoms-Addon-Peace-Treaties")) {
      new CommandAdminRegistryTerm(this);
    }
    instance = this;
  }

}
