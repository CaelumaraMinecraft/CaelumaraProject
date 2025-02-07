package top.auspice.permission;

import org.jetbrains.annotations.NotNull;
import top.auspice.permission.registry.PermissionRegistry;
import top.auspice.server.command.CommandSender;

public enum DefaultAuspicePluginPermissions implements AuspicePluginPermission {

    DEVELOPER(PermissionDefaultValue.NO_ONE),
    DEBUG,


    ;

    private final @NotNull PermissionKey key;
    private @NotNull PermissionDefaultValue defaultValue;

    DefaultAuspicePluginPermissions() {
        this(PermissionDefaultValue.OP);
    }

    DefaultAuspicePluginPermissions(@NotNull PermissionDefaultValue defaultValue) {
        this.defaultValue = defaultValue;
        this.key = PermissionKey.fromEnum(this.getNamespace(), this);
        PermissionRegistry.registerToAllRegistries(this, true);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(this);
    }

    @NotNull
    public PermissionKey getKey() {
        return this.key;
    }

    @NotNull
    public PermissionDefaultValue getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(@NotNull PermissionDefaultValue defaultValue) {
        this.defaultValue = defaultValue;
    }

    public static void init() {
    }
}
