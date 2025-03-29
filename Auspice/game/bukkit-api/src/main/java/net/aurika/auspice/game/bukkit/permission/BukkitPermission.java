package net.aurika.auspice.game.bukkit.permission;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.permission.Permission;
import net.aurika.auspice.permission.PermissionDefaultValue;
import net.aurika.auspice.permission.PermissionKey;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.auspice.translation.messenger.StaticMessenger;
import net.aurika.util.checker.Checker;
import org.jetbrains.annotations.NotNull;

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
  public @NotNull PermissionKey permKey() {
    return this.key;
  }

  @Override
  public @NotNull PermissionDefaultValue defaultValue() {
    return PermissionDefaultConverter.convert(this.perm.getDefault());  // TODO
  }

  @Override
  public void defaultValue(@NotNull PermissionDefaultValue defaultValue) {
    this.perm.setDefault(PermissionDefaultConverter.convert(defaultValue));
  }

  @Override
  public @NotNull Messenger description() {
    return new StaticMessenger(this.perm.getDescription());
  }

  @Override
  public @NotNull String toString() {
    StringBuilder sb = (new StringBuilder("BukkitPermission(permission=")).append(this.perm).append(
        ", namespace='").append(this.namespace()).append("', name=");
    String var10001 = Arrays.toString(this.name());
    Intrinsics.checkNotNullExpressionValue(var10001, "");
    return sb.append(var10001).append(", defaultValue=").append(this.defaultValue()).append(", description=").append(
        this.description()).append(", split=").append(this.key.toFullName()).append(')').toString();
  }

}