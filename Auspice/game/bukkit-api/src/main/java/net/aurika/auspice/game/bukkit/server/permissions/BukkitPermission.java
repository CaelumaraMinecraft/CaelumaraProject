package net.aurika.auspice.game.bukkit.server.permissions;

import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.permission.AbstractPermission;
import net.aurika.auspice.permission.Permission;
import net.aurika.auspice.permission.PermissionDefaultValue;

public class BukkitPermission extends AbstractPermission implements Permission {
    public BukkitPermission(@NotNull String namespace, @NotNull String[] name, @NotNull PermissionDefaultValue defaultValue) {
        super(namespace, name, defaultValue);
    }

    public static BukkitPermission of(@NotNull org.bukkit.permissions.Permission permission) {
        var perm = Permission.fromFullName(permission.getName());
        return new BukkitPermission(perm.getFirst(), perm.getSecond(), PermissionDefaultAdapter.bukkitToAuspice(permission.getDefault()));
    }
}
