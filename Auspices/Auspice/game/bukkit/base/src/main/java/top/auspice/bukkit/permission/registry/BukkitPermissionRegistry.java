package top.auspice.bukkit.permission.registry;

import kotlin.NotImplementedError;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.bukkit.permission.BukkitPermission;
import top.auspice.permission.Permission;
import top.auspice.permission.registry.PermissionRegistry;

import java.util.List;

public final class BukkitPermissionRegistry implements PermissionRegistry<BukkitPermission, org.bukkit.permissions.Permission, Player> {
    public static final @NotNull BukkitPermissionRegistry INSTANCE = new BukkitPermissionRegistry();

    private BukkitPermissionRegistry() {
    }

    @NotNull
    public BukkitPermission convert0(@NotNull Permission permission) {
        Intrinsics.checkNotNullParameter(permission, "");
//        return permission instanceof BukkitPermission ? (BukkitPermission)permission : new BukkitPermission(new org.bukkit.permissionsPermission(permission.toFullName(), permission.getDescription().parse(new Object[0]), (PermissionDefault)BukkitPermission.DEFAULT_VALUE_MAPPING.get(permission.getDefault())));
        return permission instanceof BukkitPermission ? (BukkitPermission)permission : new BukkitPermission(new org.bukkit.permissions.Permission(permission.toFullName(), BukkitPermission.DEFAULT_VALUE_MAPPING.get(permission.getDefaultValue())));
    }

    @NotNull
    public BukkitPermission convert(@NotNull org.bukkit.permissions.Permission bukkitPerm) {
        Intrinsics.checkNotNullParameter(bukkitPerm, "");
        return new BukkitPermission(bukkitPerm);
    }

    @NotNull
    public org.bukkit.permissions.Permission convert(@NotNull BukkitPermission permission) {
        Intrinsics.checkNotNullParameter(permission, "");
        return permission.getSystematicObject();
    }

    @NotNull
    public List<Permission> getAllPermissions(@NotNull Player var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        String var2 = "Not yet implemented";
        throw new NotImplementedError("An operation is not implemented: " + var2);
    }

    public boolean hasPermission(@NotNull Player var1, @NotNull BukkitPermission var2) {
        Intrinsics.checkNotNullParameter(var1, "");
        Intrinsics.checkNotNullParameter(var2, "");
        return var1.hasPermission(var2.getSystematicObject());
    }

    @Nullable
    public BukkitPermission getRegistered(@NotNull Permission permission) {
        Intrinsics.checkNotNullParameter(permission, "");
        org.bukkit.permissions.Permission bukkitPerm = Bukkit.getPluginManager().getPermission(permission.toFullName());
        if (bukkitPerm == null) {
            return null;
        } else {
            return this.convert(bukkitPerm);
        }
    }

    public void unregister(@NotNull BukkitPermission var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Bukkit.getPluginManager().removePermission(var1.getSystematicObject());
    }

    public void register(@NotNull BukkitPermission var1) {
        Intrinsics.checkNotNullParameter(var1, "");
        Bukkit.getPluginManager().addPermission(var1.getSystematicObject());
    }
}
