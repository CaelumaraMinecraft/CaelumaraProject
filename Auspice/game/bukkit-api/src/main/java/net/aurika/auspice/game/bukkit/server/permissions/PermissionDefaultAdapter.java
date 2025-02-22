package net.aurika.auspice.game.bukkit.server.permissions;

import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.permission.PermissionDefaultValue;
import net.aurika.util.Checker;

public final class PermissionDefaultAdapter {
    public static @NotNull PermissionDefault auspiceToBukkit(@NotNull PermissionDefaultValue value) {
        Checker.Arg.notNull(value, "value");
        return switch (value) {
            case EVERYONE -> PermissionDefault.TRUE;
            case NO_ONE -> PermissionDefault.FALSE;
            case OP -> PermissionDefault.OP;
            case NOT_OP -> PermissionDefault.NOT_OP;
        };
    }

    public static @NotNull PermissionDefaultValue bukkitToAuspice(@NotNull PermissionDefault value) {
        Checker.Arg.notNull(value, "value");
        return switch (value) {
            case TRUE -> PermissionDefaultValue.EVERYONE;
            case FALSE -> PermissionDefaultValue.NO_ONE;
            case OP -> PermissionDefaultValue.OP;
            case NOT_OP -> PermissionDefaultValue.NOT_OP;
        };
    }
}
