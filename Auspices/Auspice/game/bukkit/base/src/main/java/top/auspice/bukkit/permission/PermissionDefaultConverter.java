package top.auspice.bukkit.permission;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import top.auspice.permission.PermissionDefaultValue;
import top.auspice.utils.Checker;

public final class PermissionDefaultConverter {
    public static @NotNull PermissionDefault convert(@NotNull PermissionDefaultValue auspicePermissionDefault) {
        Checker.Argument.checkNotNull(auspicePermissionDefault, "auspicePermissionDefault");
        return switch (auspicePermissionDefault) {
            case EVERYONE -> PermissionDefault.TRUE;
            case NO_ONE -> PermissionDefault.FALSE;
            case OP -> PermissionDefault.OP;
            case NOT_OP -> PermissionDefault.NOT_OP;
        };
    }

    public static @NotNull PermissionDefaultValue convert(@NotNull PermissionDefault bukkitPermissionDefault) {
        Checker.Argument.checkNotNull(bukkitPermissionDefault, "bukkitPermissionDefault");
        return switch (bukkitPermissionDefault) {
            case TRUE -> PermissionDefaultValue.EVERYONE;
            case FALSE -> PermissionDefaultValue.NO_ONE;
            case OP -> PermissionDefaultValue.OP;
            case NOT_OP -> PermissionDefaultValue.NOT_OP;
        };
    }
}
