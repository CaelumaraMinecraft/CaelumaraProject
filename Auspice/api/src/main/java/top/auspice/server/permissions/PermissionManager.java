package top.auspice.server.permissions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.permission.PermissionKey;
import top.auspice.permission.Permission;

public interface PermissionManager {
    @Nullable Permission getPermission(@NotNull PermissionKey permKey);

    void addPermission(@NotNull Permission permission);

    @Nullable Permission removePermission(PermissionKey permKey);
}
