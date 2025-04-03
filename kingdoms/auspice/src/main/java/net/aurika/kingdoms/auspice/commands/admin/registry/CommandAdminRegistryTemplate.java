package net.aurika.kingdoms.auspice.commands.admin.registry;

import net.aurika.kingdoms.auspice.commands.admin.registry.operator.RegistryOperatorCommandList;
import net.aurika.kingdoms.auspice.commands.admin.registry.operator.RegistryOperatorCommandLock;
import net.aurika.kingdoms.auspice.commands.admin.registry.operator.RegistryOperatorCommandUnregister;
import net.aurika.kingdoms.auspice.util.KingdomsNamingContract;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.namespace.Lockable;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.constants.namespace.NamespacedRegistry;
import org.kingdoms.constants.namespace.UnregistrableNamespaceRegistry;

import java.util.Map;

public abstract class CommandAdminRegistryTemplate<T extends Namespaced, R extends NamespacedRegistry<T>> extends KingdomsParentCommand {

  public CommandAdminRegistryTemplate(
      @KingdomsNamingContract.CommandName final @NotNull String name,
      @Nullable KingdomsParentCommand parent,
      PermissionDefault permissionDefault,
      @NotNull R registry,
      @NotNull Class<T> valueType
  ) {
    super(name, parent, permissionDefault);
    if (registry instanceof Lockable) {
      // generics hack
      // noinspection rawtypes, unchecked
      new RegistryOperatorCommandLock("lock", this, permissionDefault, registry, valueType);
    }
    if (registry instanceof UnregistrableNamespaceRegistry<?>) {
      // generics hack
      // noinspection rawtypes, unchecked
      new RegistryOperatorCommandUnregister("unregister", this, permissionDefault, registry, valueType);
    }
    new RegistryOperatorCommandList<>("list", this, permissionDefault, registry, valueType) {

      @Override
      protected @NotNull Map<String, ? extends RegistryOperatorCommandList<T, R>.ListOperation> availableOperations() {
        return Map.of(
            "value-hashcode", new ValueHashCode(),
            "value-to-string", new ValueHashCode(),
            "key-full-string", new KeyToFullString(),
            "key-config-string", new KeyToConfigString(),
            "info", new Info()
        );
      }
    };
  }

}
