package net.aurika.auspice.configs.messages;

import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.configs.messages.context.MessageContextProvider;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;
import net.aurika.auspice.translation.messenger.Messenger;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public interface ContextualMessenger extends MessageContextProvider {
    @NotNull CommandSender messageReceiver();

    default void sendError(@NotNull Messenger messenger, @NotNull Object... edits) {
        Objects.requireNonNull(messenger);
        Objects.requireNonNull(edits);
        if (edits.length != 0) {
            this.messageContext().placeholders(Arrays.copyOf(edits, edits.length));
        }

        messenger.sendError(this.messageReceiver(), this.messageContext());
    }

    default @NotNull ContextualMessenger var(@NotNull String name, @Nullable Object value) {
        Objects.requireNonNull(name, "name");
        if (value instanceof String) {
            this.messageContext().parse(name, value);
        } else {
            this.messageContext().raw(name, value);
        }

        return this;
    }

    default boolean isPlayer() {
        return this.messageReceiver() instanceof Player;
    }

    @NonExtendable
    default void sendMessage(@NotNull Messenger messenger, @NotNull Object... edits) {
        Validate.Arg.notNull(messenger, "messenger");
        Validate.Arg.notNull(edits, "edits");
        this.sendMessage(this.messageReceiver(), messenger, Arrays.copyOf(edits, edits.length));
    }

    @NonExtendable
    default void sendMessage(@NotNull Messenger messenger, @NotNull MessageContext messageContext) {
        Validate.Arg.notNull(messenger, "messenger");
        Validate.Arg.notNull(messageContext, "messageContext");
        messenger.sendMessage(this.messageReceiver(), messageContext);
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, @NotNull Messenger messenger, @NotNull Object... edits) {
        Validate.Arg.notNull(messageReceiver, "messageReceiver");
        Validate.Arg.notNull(messenger, "messenger");
        Validate.Arg.notNull(edits, "edits");
        if (edits.length != 0) {
            this.messageContext().placeholders(Arrays.copyOf(edits, edits.length));
        }

        messenger.sendMessage(messageReceiver, this.messageContext());
    }
}
