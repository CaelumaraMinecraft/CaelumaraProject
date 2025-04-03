package net.aurika.kingdoms.auspice.commands.admin.registry.operator;

import net.aurika.kingdoms.auspice.util.KingdomsNamingContract;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.*;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.constants.namespace.NamespacedRegistry;

import java.util.List;

public abstract class RegistryOperatorCommand<T extends Namespaced, R extends NamespacedRegistry<T>> extends KingdomsCommand {

  private final @NotNull R registry;
  private final @NotNull Class<T> valueType;

  public RegistryOperatorCommand(
      @KingdomsNamingContract.CommandName final @NotNull String name,
      @Nullable KingdomsParentCommand parent,
      @Nullable PermissionDefault permissionDefault,
      @NotNull R registry,
      @NotNull Class<T> valueType
  ) {
    super(name, parent, permissionDefault);
    this.registry = registry;
    this.valueType = valueType;
  }

  @Override
  public abstract @NotNull CommandResult execute(@NotNull CommandContext commandContext);

  @Override
  public @NotNull List<String> tabComplete(@NotNull CommandTabContext commandTabContext) {
    return super.tabComplete(commandTabContext);
  }

  public @NotNull R registry() {
    return registry;
  }

  public @NotNull Class<T> valueType() {
    return valueType;
  }

}
