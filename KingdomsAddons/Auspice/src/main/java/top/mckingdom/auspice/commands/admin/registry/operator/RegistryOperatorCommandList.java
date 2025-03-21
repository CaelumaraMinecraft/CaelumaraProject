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
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.Messenger;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.util.KingdomsNamingContract;
import top.mckingdom.auspice.util.LazyMessenger;

import java.util.List;
import java.util.Map;

public abstract class RegistryOperatorCommandList<T extends Namespaced, R extends NamespacedRegistry<T>> extends RegistryOperatorCommandForeach<T, R> {

    public static final String VAR_LIST_ENTRY = "entry";

    protected final Messenger success_head = new LazyMessenger(() -> new DefaultedMessenger(this.lang("success", "head"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_LIST_SUCCESS_HEAD));
    protected final Messenger success_entry = new LazyMessenger(() -> new DefaultedMessenger(this.lang("success", "entry"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_LIST_SUCCESS_ENTRY));
    protected final Messenger success_end = new LazyMessenger(() -> new DefaultedMessenger(this.lang("success", "end"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_LIST_SUCCESS_END));

    public RegistryOperatorCommandList(@KingdomsNamingContract.CommandName final @NotNull String name, @Nullable KingdomsParentCommand parent, @Nullable PermissionDefault permissionDefault, @NotNull R registry, @NotNull Class<T> valueType) {
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
            success_head.sendMessage(messageReceiver);
            for (Map.Entry<Namespace, T> entry : registry.getRegistry().entrySet()) {
                try {
                    success_entry.sendMessage(messageReceiver, VAR_LIST_ENTRY, op.execute(entry.getKey(), entry.getValue(), commandContext));
                } catch (Exception e) {  // TODO
                    return CommandResult.FAILED;
                }
            }
            success_end.sendMessage(messageReceiver);
            return CommandResult.SUCCESS;
        } else {
            failed_unknown_op.sendError(messageReceiver, VAR_OP, opName);
            return CommandResult.FAILED;
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandTabContext commandTabContext) {
        return super.tabComplete(commandTabContext);
    }

    protected abstract @NotNull Map<String, ? extends ListOperation> availableOperations();

    public class Info extends ListOperation {

        public Info() {
            super("info");
        }

        @Override
        public String execute(@NotNull Namespace ns, T obj, CommandContext commandContext) {
            return obj.getClass().getSimpleName() + "{ namespace=" + obj.getNamespace().asString() + ", hashCode=" + obj.hashCode() + " }";
        }
    }

    public class KeyToConfigString extends ListOperation {

        public KeyToConfigString() {
            super("key-config-string");
        }

        @Override
        public String execute(@NotNull Namespace ns, T obj, CommandContext commandContext) {
            return ns.getConfigOptionName();
        }
    }

    public class KeyToNormalString extends ListOperation {

        public KeyToNormalString() {
            super("key-normal-string");
        }

        @Override
        public String execute(@NotNull Namespace ns, T obj, CommandContext commandContext) {
            return ns.asNormalizedString();
        }
    }

    public class KeyToFullString extends ListOperation {

        public KeyToFullString() {
            super("key-full-string");
        }

        @Override
        public String execute(@NotNull Namespace ns, T obj, CommandContext commandContext) {
            return ns.asString();
        }
    }

    public class ValueHashCode extends ListOperation {

        public ValueHashCode() {
            super("value-hashcode");
        }

        @Override
        public String execute(@NotNull Namespace ns, T obj, CommandContext commandContext) {
            return String.valueOf(obj.hashCode());
        }
    }

    public class ValueToString extends ListOperation {

        public ValueToString() {
            super("value-to-string");
        }

        @Override
        public String execute(@NotNull Namespace ns, T obj, CommandContext commandContext) {
            return obj.toString();
        }
    }

    public abstract class ListOperation extends ForeachOperation {
        public ListOperation(@NotNull String name) {
            super(name);
        }

        @Override
        public abstract String execute(@NotNull Namespace ns, T obj, CommandContext commandContext);
    }
}
