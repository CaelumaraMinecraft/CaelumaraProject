package net.aurika.auspice.game.bukkit.permission;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.permission.PermissionDefaultValue;
import net.aurika.util.checker.Checker;

public final class PermissionDefaultConverter {
    public static @NotNull PermissionDefault convert(@NotNull PermissionDefaultValue auspicePermissionDefault) {
        Checker.Arg.notNull(auspicePermissionDefault, "auspicePermissionDefault");
        return switch (auspicePermissionDefault) {
            case EVERYONE -> PermissionDefault.TRUE;
            case NO_ONE -> PermissionDefault.FALSE;
            case OP -> PermissionDefault.OP;
            case NOT_OP -> PermissionDefault.NOT_OP;
        };
    }

    public static @NotNull PermissionDefaultValue convert(@NotNull PermissionDefault bukkitPermissionDefault) {
        Checker.Arg.notNull(bukkitPermissionDefault, "bukkitPermissionDefault");
        return switch (bukkitPermissionDefault) {
            case TRUE -> PermissionDefaultValue.EVERYONE;
            case FALSE -> PermissionDefaultValue.NO_ONE;
            case OP -> PermissionDefaultValue.OP;
            case NOT_OP -> PermissionDefaultValue.NOT_OP;
        };
    }
}
