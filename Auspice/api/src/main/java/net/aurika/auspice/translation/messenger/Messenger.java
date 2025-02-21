package net.aurika.auspice.translation.messenger;

import net.aurika.validate.Validate;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;
import net.aurika.auspice.server.player.OfflinePlayer;

import java.util.Objects;

/**
 * Messengers provide different messages between different diversities.
 */
public interface Messenger {
    default String parse(@Nullable Player player, Object... edits) {
        return this.parse(MessageManager.diversityOf(player), (new MessageContextImpl()).raws(edits).withContext(player));
    }

    default String parse(MessageContextImpl textPlaceholderProvider) {
        return this.parse(textPlaceholderProvider.diversity(), textPlaceholderProvider);
    }

    default String parse(@Nullable OfflinePlayer offlinePlayer, Object... edits) {
        return this.parse(MessageManager.diversityOf(offlinePlayer), (new MessageContextImpl()).raws(edits).withContext(offlinePlayer));
    }

    default String parse(Diversity diversity, @NonNull MessageContextImpl textPlaceholderProvider) {
        MessageObject message = this.getMessageObject(diversity);
        return message == null ? null : message.build(textPlaceholderProvider);
    }

    default String parse(CommandSender commandSender, Object... edits) {
        return !(commandSender instanceof OfflinePlayer) ? this.parse(edits) : this.parse((OfflinePlayer) commandSender, edits);
    }

    @Nullable MessageProvider getProvider(@NotNull Diversity diversity);

    default MessageObject getMessageObject(@NotNull Diversity diversity) {
        Validate.Arg.notNull(diversity, "translation");
        MessageProvider msgProvider = this.getProvider(diversity);
        return msgProvider == null ? null : msgProvider.message();
    }

    default String parse(Object... edits) {
        return this.parse((CommandSender) null, edits);
    }

    default void sendError(@NotNull CommandSender messageReceiver, MessageContextImpl textPlaceholderProvider) {
        if (messageReceiver instanceof Player) {
            AuspiceGlobalConfig.errorSound((Player) messageReceiver);
        }

        this.sendMessage(messageReceiver, textPlaceholderProvider);
    }

    default void sendError(@NotNull CommandSender messageReceiver, Object... edits) {
        this.sendError(messageReceiver, (new MessageContextImpl()).placeholders(edits).withContext(messageReceiver));
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, Object... edits) {
        this.sendMessage(messageReceiver, (new MessageContextImpl()).placeholders(edits).withContext(messageReceiver));
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, MessageContextImpl textPlaceholderProvider) {
        if (Boolean.TRUE.equals(AuspiceGlobalConfig.PREFIX.getBoolean())) {
            textPlaceholderProvider.usePrefix(Boolean.TRUE);
        }

        Diversity diversity = textPlaceholderProvider.diversity();
        if (messageReceiver instanceof Player player) {
            textPlaceholderProvider.lang(diversity = MessageManager.diversityOf(player));
        }

        Objects.requireNonNull(this.getProvider(diversity), "Message for texts '" + diversity + "' is not defined: " + this).send(messageReceiver, textPlaceholderProvider);
    }

    default void sendMessage(@NotNull CommandSender commandSender) {
        this.sendMessage(commandSender, (new MessageContextImpl()).withContext(commandSender));
    }
}
