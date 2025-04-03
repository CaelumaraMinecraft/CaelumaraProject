package net.aurika.auspice.text.compiler.placeholders.types;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.permission.Permission;
import net.aurika.auspice.permission.PermissionDefaultValue;
import net.aurika.auspice.permission.registry.PermissionRegistry;
import net.aurika.auspice.platform.entity.Player;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import net.aurika.text.placeholders.target.BasePlaceholderTargetProvider;
import net.aurika.text.placeholders.target.PlaceholderTargetProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class PermissionPlaceholder extends AbstractPlaceholder {

  @NotNull
  private final String a;
  @NotNull
  private final Permission permission;

  public PermissionPlaceholder(@NotNull String originalString, @NotNull String var2, @Nullable String pointer, @NotNull List<PlaceholderModifier> modifiers) {
    super(originalString, pointer, modifiers);

    this.a = Objects.requireNonNull(var2);
    this.permission = Permission.fromFullName(this.a.replace('_', '.'), PermissionDefaultValue.OP);
    PermissionRegistry.registerToAllRegistries(this.permission, false);
  }

  @NotNull
  public String asString(boolean surround) {
    return this.getCommonString(surround, "perm_" + this.a);
  }

  @NotNull
  public Object request(@NotNull PlaceholderProvider provider) {
    Objects.requireNonNull(provider);
    if (!(provider instanceof PlaceholderTargetProvider)) {
      throw this.error("Cannot use permission placeholders here with no player context: " + provider);
    } else {
      BasePlaceholderTargetProvider var2 = ((PlaceholderTargetProvider) provider).getTargetProviderFor(
          this.getPointer());
      if (!(var2.getPrimaryTarget() instanceof Player)) {
        throw this.error("Cannot use permission placeholders here with no player context: " + var2.getPrimaryTarget());
      } else {
        Object var10000 = var2.getPrimaryTarget();
        Intrinsics.checkNotNull(var10000);
        return String.valueOf(((Player) var10000).hasPermission(this.permission));
      }
    }
  }

}

