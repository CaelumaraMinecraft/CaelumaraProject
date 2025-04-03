package net.aurika.auspice.permission;

import net.aurika.auspice.permission.registry.PermissionRegistry;
import net.aurika.auspice.platform.command.CommandSender;
import org.jetbrains.annotations.NotNull;

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
    this.key = PermissionKey.fromEnum(this.namespace(), this);
    PermissionRegistry.registerToAllRegistries(this, true);
  }

  public boolean hasPermission(CommandSender sender) {
    return sender.hasPermission(this);
  }

  @NotNull
  public PermissionKey permKey() {
    return this.key;
  }

  @NotNull
  public PermissionDefaultValue defaultValue() {
    return this.defaultValue;
  }

  @Override
  public void defaultValue(@NotNull PermissionDefaultValue defaultValue) {
    this.defaultValue = defaultValue;
  }

  public static void init() {
  }
}
