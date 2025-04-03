package net.aurika.auspice.platform.permission;

import net.aurika.auspice.permission.Permission;
import net.aurika.auspice.permission.PermissionKey;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;

public interface Permissible extends ServerOperator {

  default boolean hasPermission(@NotNull Permission permission) {
    Checker.Arg.notNull(permission, "permission");
    return this.hasPermission(permission.permKey());
  }

  boolean hasPermission(@NotNull PermissionKey permKey);

  default @NotNull Permission.State permissionState(@NotNull Permission permission) {
    Checker.Arg.notNull(permission, "permission");
    return this.permissionState(permission.permKey());
  }

  @NotNull Permission.State permissionState(@NotNull PermissionKey permKey);

}
