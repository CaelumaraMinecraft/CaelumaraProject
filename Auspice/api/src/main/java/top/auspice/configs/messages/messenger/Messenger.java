package top.auspice.configs.messages.messenger;

import net.aurika.validate.Validate;
import net.aurika.text.TextObject;
import net.aurika.config.placeholders.context.MessagePlaceholderProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.messages.MessageObject;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityManager;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.server.player.OfflinePlayer;

import java.util.Objects;

/**
 * 通过 {@linkplain Diversity} 来获取一个 {@link TextObject} 对象
 */
public interface Messenger {
    default String parse(@Nullable Player player, Object... edits) {
        return this.parse(DiversityManager.localeOf(player), (new MessagePlaceholderProvider()).raws(edits).withContext(player));
    }

    default String parse(MessagePlaceholderProvider textPlaceholderProvider) {
        return this.parse(textPlaceholderProvider.getLanguage(), textPlaceholderProvider);
    }

    default String parse(@Nullable OfflinePlayer offlinePlayer, Object... edits) {
        return this.parse(DiversityManager.localeOf(offlinePlayer), (new MessagePlaceholderProvider()).raws(edits).withContext(offlinePlayer));
    }

    default String parse(Diversity diversity, @NonNull MessagePlaceholderProvider textPlaceholderProvider) {
        MessageObject message = this.getMessageObject(diversity);
        return message == null ? null : message.build(textPlaceholderProvider);
    }

    default String parse(CommandSender commandSender, Object... edits) {
        return !(commandSender instanceof OfflinePlayer) ? this.parse(edits) : this.parse((OfflinePlayer) commandSender, edits);
    }

    @Nullable MessageProvider getProvider(@NotNull Diversity diversity);

    default MessageObject getMessageObject(@NotNull Diversity diversity) {
        Validate.Arg.notNull(diversity, "diversity");
        MessageProvider msgProvider = this.getProvider(diversity);
        return msgProvider == null ? null : msgProvider.getMessage();
    }

    default String parse(Object... edits) {
        return this.parse((CommandSender) null, edits);
    }

    default void sendError(@NotNull CommandSender messageReceiver, MessagePlaceholderProvider textPlaceholderProvider) {
        if (messageReceiver instanceof Player) {
            AuspiceGlobalConfig.errorSound((Player) messageReceiver);
        }

        this.sendMessage(messageReceiver, textPlaceholderProvider);
    }

    default void sendError(@NotNull CommandSender messageReceiver, Object... edits) {
        this.sendError(messageReceiver, (new MessagePlaceholderProvider()).placeholders(edits).withContext(messageReceiver));
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, Object... edits) {
        this.sendMessage(messageReceiver, (new MessagePlaceholderProvider()).placeholders(edits).withContext(messageReceiver));
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, MessagePlaceholderProvider textPlaceholderProvider) {
        if (Boolean.TRUE.equals(AuspiceGlobalConfig.PREFIX.getBoolean())) {
            textPlaceholderProvider.usePrefix(Boolean.TRUE);
        }

        Diversity diversity = textPlaceholderProvider.getLanguage();
        if (messageReceiver instanceof Player player) {
            textPlaceholderProvider.lang(diversity = DiversityManager.localeOf(player));
        }

        Objects.requireNonNull(this.getProvider(diversity), "Message for texts '" + diversity + "' is not defined: " + this).send(messageReceiver, textPlaceholderProvider);
    }

    default void sendMessage(@NotNull CommandSender commandSender) {
        this.sendMessage(commandSender, (new MessagePlaceholderProvider()).withContext(commandSender));
    }
}
