package top.mckingdom.auspice.commands.admin.registry.operator;

import net.aurika.validate.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.CommandContext;
import org.kingdoms.commands.CommandResult;
import org.kingdoms.commands.CommandTabContext;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.namespace.Namespace;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.constants.namespace.NamespacedRegistry;
import org.kingdoms.constants.namespace.UnregistrableNamespaceRegistry;
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.Messenger;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.util.KingdomsNamingContract;
import top.mckingdom.auspice.util.LazyMessenger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class RegistryOperatorCommandUnregister<T extends Namespaced, R extends NamespacedRegistry<T> & UnregistrableNamespaceRegistry<T>> extends RegistryOperatorCommand<T, R> {
  public static final String VAR_KEY = "key";  // TODO inline

  protected final Messenger failed_not_unregisterable = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "not-unregisterable"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_UNREGISTER_FAILED_NOT_UNREGISTERABLE));
  protected final Messenger failed_no_key = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "no-key"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_UNREGISTER_FAILED_NO_KEY));
  protected final Messenger failed_wrong_key = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "wrong-key"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_UNREGISTER_FAILED_WRONG_KEY));
  protected final Messenger success = new LazyMessenger(() -> new DefaultedMessenger(this.lang("success"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_UNREGISTER_SUCCESS));

  public RegistryOperatorCommandUnregister(@KingdomsNamingContract.CommandName final @NotNull String name,
                                           @Nullable KingdomsParentCommand parent,
                                           @Nullable PermissionDefault permissionDefault,
                                           @NotNull R registry,
                                           @NotNull Class<T> valueType
  ) {
    super(name, parent, permissionDefault, registry, valueType);
  }

  @Override
  public @NotNull CommandResult execute(@NotNull CommandContext commandContext) {
    Validate.Arg.notNull(commandContext, "commandContext");
    CommandSender messageReceiver = commandContext.getMessageReceiver();
    if (commandContext.args.length < 1) {
      failed_no_key.sendError(messageReceiver);
      return CommandResult.FAILED;
    }
    String nsStr = commandContext.arg(0);
    Namespace key;
    try {
      key = Namespace.fromConfigString(nsStr);
    } catch (IllegalArgumentException e) {
      failed_wrong_key.sendError(messageReceiver, VAR_KEY, nsStr);
      return CommandResult.FAILED;
    }
    NamespacedRegistry<T> registry = registry();
    if (!(registry instanceof UnregistrableNamespaceRegistry<?> unregsiterable)) {
      failed_not_unregisterable.sendError(messageReceiver);
      return CommandResult.FAILED;
    } else {
      unregsiterable.unregister(key);
      success.sendMessage(messageReceiver);
      return CommandResult.SUCCESS;
    }
  }

  @Override
  public @NotNull List<String> tabComplete(@NotNull CommandTabContext context) {
    Validate.Arg.notNull(context, "context");
    if (context.isAtArg(0)) {
      Map<Namespace, T> registryMap = registry().getRegistry();
      List<String> results = new ArrayList<>();
      for (Namespace namespace : registryMap.keySet()) {
        results.add(namespace.asNormalizedString());
      }
      return results;
    }
    return Collections.emptyList();
  }

}
