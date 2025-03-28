package top.mckingdom.auspice.commands.admin.registry;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.player.KingdomPermission;
import org.kingdoms.constants.player.KingdomPermissionRegistry;
import org.kingdoms.main.Kingdoms;

public class CommandAdminRegistryKingdomPermission extends CommandAdminRegistryTemplate<KingdomPermission, KingdomPermissionRegistry> {

  public CommandAdminRegistryKingdomPermission(@Nullable KingdomsParentCommand parent) {
    this(parent, PermissionDefault.OP);
  }

  public CommandAdminRegistryKingdomPermission(@Nullable KingdomsParentCommand parent, PermissionDefault permissionDefault) {
    super("kingdom" + "permission", parent, permissionDefault, Kingdoms.get().getPermissionRegistery(), KingdomPermission.class);
  }

}
