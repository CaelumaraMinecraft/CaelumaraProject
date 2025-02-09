package top.auspice.bukkit.permission;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.messages.messenger.Messenger;
import top.auspice.configs.messages.messenger.StaticMessenger;
import top.auspice.permission.Permission;
import top.auspice.permission.PermissionDefaultValue;
import top.auspice.permission.PermissionKey;
import net.aurika.utils.checker.Checker;

import java.util.Arrays;

public final class BukkitPermission implements Permission {
    private final @NotNull org.bukkit.permissions.Permission perm;
    private final @NotNull PermissionKey key;

    public BukkitPermission(@NotNull org.bukkit.permissions.Permission perm) {
        Checker.Arg.notNull(perm, "perm");
        this.perm = perm;
        this.key = PermissionKey.fromFullName(perm.getName());
    }

    @Override
    public @NotNull PermissionKey getKey() {
        return this.key;
    }

    @Override
    public @NotNull PermissionDefaultValue getDefaultValue() {
        return PermissionDefaultConverter.convert(this.perm.getDefault());  // TODO
    }

    @Override
    public void setDefaultValue(@NotNull PermissionDefaultValue defaultValue) {
        this.perm.setDefault(PermissionDefaultConverter.convert(defaultValue));
    }

    @Override
    public @NotNull Messenger getDescription() {
        return new StaticMessenger(this.perm.getDescription());
    }

    @Override
    public @NotNull String toString() {
        StringBuilder sb = (new StringBuilder("BukkitPermission(permission=")).append(this.perm).append(", namespace='").append(this.getNamespace()).append("', name=");
        String var10001 = Arrays.toString(this.getName());
        Intrinsics.checkNotNullExpressionValue(var10001, "");
        return sb.append(var10001).append(", defaultValue=").append(this.getDefaultValue()).append(", description=").append(this.getDescription()).append(", split=").append(this.key.toFullName()).append(')').toString();
    }
}