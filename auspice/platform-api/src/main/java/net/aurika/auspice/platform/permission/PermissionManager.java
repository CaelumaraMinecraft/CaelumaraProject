package net.aurika.auspice.platform.permission;

import net.aurika.auspice.permission.Permission;
import net.aurika.auspice.permission.PermissionKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PermissionManager {

  @Nullable Permission getPermission(@NotNull PermissionKey permKey);

  void addPermission(@NotNull Permission permission);

  @Nullable Permission removePermission(PermissionKey permKey);

}
