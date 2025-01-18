package top.auspice.configs.texts;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.messenger.Messenger;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.texts.context.provider.TextContextProvider;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.utils.Checker;

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
        Checker.Argument.checkNotNull(messenger, "messenger");
        Objects.requireNonNull(edits);
        this.sendMessage(this.getMessageReceiver(), messenger, Arrays.copyOf(edits, edits.length));
    }

    @NonExtendable
    default void sendMessage(@NotNull Messenger messenger, @NotNull TextPlaceholderProvider textPlaceholderProvider) {
        Checker.Argument.checkNotNull(messenger, "messenger");
        Checker.Argument.checkNotNull(textPlaceholderProvider, "messagePlaceholderProvider");
        messenger.sendMessage(this.getMessageReceiver(), textPlaceholderProvider);
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, @NotNull Messenger messenger, @NotNull Object... edits) {
        Checker.Argument.checkNotNull(messageReceiver, "messageReceiver");
        Checker.Argument.checkNotNull(messenger, "messenger");
        Objects.requireNonNull(edits);
        if (edits.length != 0) {
            this.getTextContext().placeholders(Arrays.copyOf(edits, edits.length));
        }

        messenger.sendMessage(messageReceiver, this.getTextContext());
    }
}

