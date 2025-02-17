package top.auspice.server.permissions;

import org.jetbrains.annotations.NotNull;
import top.auspice.permission.Permission;
import top.auspice.permission.PermissionKey;
import net.aurika.util.Checker;

public interface Permissible extends ServerOperator {

    default boolean hasPermission(@NotNull Permission permission) {
        Checker.Arg.notNull(permission, "permission");
        return this.hasPermission(permission.getKey());
    }

    boolean hasPermission(@NotNull PermissionKey permKey);

    default @NotNull Permission.State permissionState(@NotNull Permission permission) {
        Checker.Arg.notNull(permission, "permission");
        return this.permissionState(permission.getKey());
    }

    @NotNull Permission.State permissionState(@NotNull PermissionKey permKey);
}
