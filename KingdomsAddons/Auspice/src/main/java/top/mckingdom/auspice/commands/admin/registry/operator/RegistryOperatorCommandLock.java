package top.mckingdom.auspice.commands.admin.registry.operator;

import net.aurika.validate.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.kingdoms.commands.CommandContext;
import org.kingdoms.commands.CommandResult;
import org.kingdoms.commands.KingdomsParentCommand;
import org.kingdoms.constants.namespace.Lockable;
import org.kingdoms.constants.namespace.Namespaced;
import org.kingdoms.constants.namespace.NamespacedRegistry;
import org.kingdoms.locale.messenger.DefaultedMessenger;
import org.kingdoms.locale.messenger.Messenger;
import top.mckingdom.auspice.commands.CommandName;
import top.mckingdom.auspice.configs.AuspiceLang;
import top.mckingdom.auspice.util.LazyMessenger;

public class RegistryOperatorCommandLock<T extends Namespaced, R extends NamespacedRegistry<T>> extends RegistryOperatorCommand<T, R> {

    protected final Messenger failed_not_lockable = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "not-lockable"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_LOCK_FAILED_NOT_LOCKABLE));
    protected final Messenger failed_exception = new LazyMessenger(() -> new DefaultedMessenger(this.lang("failed", "exception"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_LOCK_FAILED_EXCEPTION));
    protected final Messenger success = new LazyMessenger(() -> new DefaultedMessenger(this.lang("success"), () -> AuspiceLang.COMMAND_ADMIN_REGISTRY_LOCK_SUCCESS));

    public RegistryOperatorCommandLock(@CommandName final @NotNull String name,
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
        if (!(registry() instanceof Lockable lockable)) {
            failed_not_lockable.sendError(messageReceiver);
            return CommandResult.FAILED;
        }
        try {
            lockable.lock();
            success.sendMessage(messageReceiver);
            return CommandResult.SUCCESS;
        } catch (Exception e) {
            failed_exception.sendError(messageReceiver);
            return CommandResult.FAILED;
        }
    }
}
