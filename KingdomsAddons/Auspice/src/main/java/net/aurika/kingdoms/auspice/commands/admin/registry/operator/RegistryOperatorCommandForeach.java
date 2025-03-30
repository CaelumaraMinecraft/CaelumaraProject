package net.aurika.kingdoms.auspice.commands.admin.registry.operator;

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
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.Messenger;
import net.aurika.kingdoms.auspice.configs.AuspiceLang;
import net.aurika.kingdoms.auspice.util.KingdomsNamingContract;
import net.aurika.kingdoms.auspice.util.LazyMessenger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RegistryOperatorCommandForeach<T extends Namespaced, R extends NamespacedRegistry<T>> extends RegistryOperatorCommand<T, R> {

  public static final String VAR_OP = "op";

  protected final @NotNull Messenger failed_noop = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "noop"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_FOREACH_FAILED_NOOP));
  protected final @NotNull Messenger failed_unknown_op = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "unknown-op"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_FOREACH_FAILED_UNKNOWN_OP));

  public RegistryOperatorCommandForeach(@KingdomsNamingContract.CommandName final @NotNull String name, @Nullable KingdomsParentCommand parent, @Nullable PermissionDefault permissionDefault, @NotNull R registry, @NotNull Class<T> valueType) {
    super(name, parent, permissionDefault, registry, valueType);
  }

  @Override
  public @NotNull CommandResult execute(@NotNull CommandContext commandContext) {
    Validate.Arg.notNull(commandContext, "commandContext");
    CommandSender messageReceiver = commandContext.getMessageReceiver();
    if (commandContext.args.length < 1) {
      failed_noop.sendError(messageReceiver);
      return CommandResult.FAILED;
    }
    Map<String, ? extends ForeachOperation> availableOperations = availableOperations();
    String opName = commandContext.arg(0);
    if (availableOperations.containsKey(opName)) {
      ForeachOperation op = availableOperations.get(opName);
      R registry = registry();
      for (Map.Entry<Namespace, T> entry : registry.getRegistry().entrySet()) {
        try {
          op.execute(entry.getKey(), entry.getValue(), commandContext);
        } catch (Exception e) {  // TODO
          return CommandResult.FAILED;
        }
      }
      return CommandResult.SUCCESS;
    } else {
      failed_unknown_op.sendError(messageReceiver, VAR_OP, opName);
      return CommandResult.FAILED;
    }
  }

  @Override
  public @NotNull List<String> tabComplete(@NotNull CommandTabContext commandTabContext) {
    var availableOps = this.availableOperations();
    return new ArrayList<>(availableOps.keySet());
  }

  /**
   * Gets available {@linkplain ForeachOperation} s.
   */
  protected abstract @NotNull Map<String, ? extends ForeachOperation> availableOperations();

  public abstract class ForeachOperation {
    public final @NotNull String name;

    public ForeachOperation(@NotNull String name) {
      Validate.Arg.notEmpty(name, "name");
      this.name = name;
    }

    public abstract Object execute(@NotNull Namespace ns, T obj, CommandContext context);

    /**
     * Gets the operation name.
     *
     * @return the operation name
     */
    public final @NotNull String name() {
      return name;
    }

  }

}
