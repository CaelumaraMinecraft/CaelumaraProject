package top.auspice.configs.messages;

import net.aurika.validate.Validate;
import net.aurika.text.context.provider.TextContextProvider;
import net.aurika.config.placeholders.context.MessagePlaceholderProvider;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.messenger.Messenger;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public interface ContextualMessenger extends TextContextProvider {
    @NotNull CommandSender getMessageReceiver();

    default void sendError(@NotNull Messenger messenger, @NotNull Object... edits) {
        Objects.requireNonNull(messenger);
        Objects.requireNonNull(edits);
        if (edits.length != 0) {
            this.getTextContext().placeholders(Arrays.copyOf(edits, edits.length));
        }

        messenger.sendError(this.getMessageReceiver(), this.getTextContext());
    }

    default @NotNull ContextualMessenger var(@NotNull String variable, @Nullable Object value) {
        Objects.requireNonNull(variable);
        if (value instanceof String) {
            this.getTextContext().parse(variable, value);
        } else {
            this.getTextContext().raw(variable, value);
        }

        return this;
    }

    default boolean isPlayer() {
        return this.getMessageReceiver() instanceof Player;
    }

    @NonExtendable
    default void sendMessage(@NotNull Messenger messenger, @NotNull Object... edits) {
        Validate.Arg.notNull(messenger, "messenger");
        Objects.requireNonNull(edits);
        this.sendMessage(this.getMessageReceiver(), messenger, Arrays.copyOf(edits, edits.length));
    }

    @NonExtendable
    default void sendMessage(@NotNull Messenger messenger, @NotNull MessagePlaceholderProvider textPlaceholderProvider) {
        Validate.Arg.notNull(messenger, "messenger");
        Validate.Arg.notNull(textPlaceholderProvider, "messagePlaceholderProvider");
        messenger.sendMessage(this.getMessageReceiver(), textPlaceholderProvider);
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, @NotNull Messenger messenger, @NotNull Object... edits) {
        Validate.Arg.notNull(messageReceiver, "messageReceiver");
        Validate.Arg.notNull(messenger, "messenger");
        Objects.requireNonNull(edits);
        if (edits.length != 0) {
            this.getTextContext().placeholders(Arrays.copyOf(edits, edits.length));
        }

        messenger.sendMessage(messageReceiver, this.getTextContext());
    }
}
