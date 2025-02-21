package net.aurika.auspice.permission;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.auspice.translation.messenger.StaticMessenger;
import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.server.permission.Permissible;
import net.aurika.auspice.server.permission.PermissionManager;


import java.util.function.BooleanSupplier;

public interface Permission {
    char STD_SEPARATOR = '.';

    @NotNull PermissionKey permKey();

    default @NotNull String namespace() {
        return this.permKey().namespace();
    }

    default @NotNull String @NotNull [] name() {
        return this.permKey().name();
    }

    default @NotNull String toFullName() {
        return this.namespace() + STD_SEPARATOR + String.join(String.valueOf(STD_SEPARATOR), this.name());
    }

    /**
     * Gets the permission default value.
     */
    @NotNull PermissionDefaultValue defaultValue();

    /**
     * Sets the permission default value.
     */
    void defaultValue(@NotNull PermissionDefaultValue defaultValue);

    /**
     * Gets the description.
     */
    default @NotNull Messenger description() {
        return new StaticMessenger("The description was not implemented");
    }

    /**
     * Gets the display name.
     */
    default @NotNull Messenger displayName() {
        return new StaticMessenger("The displayName was not implemented");
    }

    default boolean hasPermission(@NotNull Permissible permissible) {
        Validate.Arg.notNull(permissible, "permissible");
        return permissible.hasPermission(this.permKey());
    }

    static void register(@NotNull Permission permission) {
        Validate.Arg.notNull(permission, "permission");
        final class Holder {
            static @Nullable PermissionManager INSTANCE;

            public static @NotNull PermissionManager get() {
                if (INSTANCE == null) {
                    INSTANCE = Server.get().permissionManager();
                }
                return Holder.INSTANCE;
            }
        }
        Holder.get().addPermission(permission);
    }

    static @NotNull Permission fromFullName(@NotNull String fullName, @NotNull PermissionDefaultValue defaultValue) {
        Validate.Arg.notNull(defaultValue, "defaultValue");
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
