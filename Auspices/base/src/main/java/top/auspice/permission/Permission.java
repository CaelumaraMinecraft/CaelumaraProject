package top.auspice.permission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.messenger.Messenger;
import top.auspice.configs.texts.messenger.StaticMessenger;
import top.auspice.server.core.Server;
import top.auspice.server.permissions.Permissible;
import top.auspice.server.permissions.PermissionManager;
import net.aurika.utils.Checker;

import java.util.function.BooleanSupplier;

public interface Permission {
    char STD_SEPARATOR = '.';

    @NotNull PermissionKey getKey();

    default @NotNull String getNamespace() {
        return this.getKey().getNamespace();
    }

    default @NotNull String @NotNull [] getName() {
        return this.getKey().getName();
    }

    default @NotNull String toFullName() {
        return this.getNamespace() + STD_SEPARATOR + String.join(String.valueOf(STD_SEPARATOR), this.getName());
    }

    @NotNull PermissionDefaultValue getDefaultValue();

    void setDefaultValue(@NotNull PermissionDefaultValue defaultValue);

    default @NotNull Messenger getDescription() {
        return new StaticMessenger("The description was not implemented");
    }

    default @NotNull Messenger getDisplayName() {
        return new StaticMessenger("The displayName was not implemented");
    }

    default boolean hasPermission(@NotNull Permissible permissible) {
        Checker.Argument.checkNotNull(permissible, "permissible");
        return permissible.hasPermission(this.getKey());
    }

    public static void register(@NotNull Permission permission) {
        Checker.Argument.checkNotNull(permission, "permission");
        final class Holder {
            static @Nullable PermissionManager INSTANCE;

            public static @NotNull PermissionManager get() {
                if (INSTANCE == null) {
                    INSTANCE = Server.get().getPermissionManager();
                }
                return Holder.INSTANCE;
            }
        }
        Holder.get().addPermission(permission);
    }

    public static @NotNull Permission fromFullName(@NotNull String fullName, @NotNull PermissionDefaultValue defaultValue) {
        Checker.Argument.checkNotNull(defaultValue, "defaultValue");
        PermissionKey key = PermissionKey.fromFullName(fullName);
        return new AbstractPermission(key, defaultValue);
    }

    enum State {
        /**
         * 未设置状态
         */
        NOT_SET,
        /**
         * 已经设置了 {@code false}
         */
        FALSE,
        /**
         * 已经设置了 {@code true}
         */
        TRUE;

        public @Nullable Boolean toBoolean() {
            return switch (this) {
                case TRUE -> Boolean.TRUE;
                case FALSE -> Boolean.FALSE;
                default -> null;
            };
        }

        public boolean toBooleanOrElse(final boolean other) {
            return switch (this) {
                case TRUE -> true;
                case FALSE -> false;
                default -> other;
            };
        }

        public boolean toBooleanOrElseGet(final @NotNull BooleanSupplier supplier) {
            return switch (this) {
                case TRUE -> true;
                case FALSE -> false;
                default -> supplier.getAsBoolean();
            };
        }

        public static @NotNull Permission.State byBoolean(final boolean value) {
            return value ? TRUE : FALSE;
        }

        public static @NotNull Permission.State byBoolean(final @Nullable Boolean value) {
            return value == null ? NOT_SET : byBoolean(value.booleanValue());
        }
    }
}
