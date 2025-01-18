package top.auspice.configs.texts.messenger;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import top.auspice.configs.texts.LanguageManager;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityManager;
import top.auspice.server.player.OfflinePlayer;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;
import top.auspice.utils.Checker;

import java.util.Objects;

/**
 * 通过 {@linkplain Diversity} 来获取一个 {@link TextObject} 对象
 */
public interface Messenger {
    default String parse(@Nullable Player player, Object... edits) {
        return this.parse(DiversityManager.localeOf(player), (new TextPlaceholderProvider()).raws(edits).withContext(player));
    }

    default String parse(TextPlaceholderProvider textPlaceholderProvider) {
        return this.parse(textPlaceholderProvider.getLanguage(), textPlaceholderProvider);
    }

    default String parse(@Nullable OfflinePlayer offlinePlayer, Object... edits) {
        return this.parse(LanguageManager.localeOf(offlinePlayer), (new TextPlaceholderProvider()).raws(edits).withContext(offlinePlayer));
    }

    default String parse(Diversity locale, @NonNull TextPlaceholderProvider textPlaceholderProvider) {
        TextObject message = this.getMessageObject(locale);
        return message == null ? null : message.build(textPlaceholderProvider);
    }

    default String parse(CommandSender commandSender, Object... edits) {
        return !(commandSender instanceof OfflinePlayer) ? this.parse(edits) : this.parse((OfflinePlayer) commandSender, edits);
    }

    @Nullable MessageProvider getProvider(@NotNull Diversity diversity);

    default TextObject getMessageObject(@NotNull Diversity diversity) {
        Checker.Argument.checkNotNull(diversity, "diversity");
        MessageProvider msgProvider = this.getProvider(diversity);
        return msgProvider == null ? null : msgProvider.getMessage();
    }

    default String parse(Object... edits) {
        return this.parse((CommandSender) null, edits);
    }

    default void sendError(@NotNull CommandSender messageReceiver, TextPlaceholderProvider textPlaceholderProvider) {
        if (messageReceiver instanceof Player) {
            AuspiceGlobalConfig.errorSound((Player) messageReceiver);
        }

        this.sendMessage(messageReceiver, textPlaceholderProvider);
    }

    default void sendError(@NotNull CommandSender messageReceiver, Object... edits) {
        this.sendError(messageReceiver, (new TextPlaceholderProvider()).placeholders(edits).withContext(messageReceiver));
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, Object... edits) {
        this.sendMessage(messageReceiver, (new TextPlaceholderProvider()).placeholders(edits).withContext(messageReceiver));
    }

    default void sendMessage(@NotNull CommandSender messageReceiver, TextPlaceholderProvider textPlaceholderProvider) {
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
        this.sendMessage(commandSender, (new TextPlaceholderProvider()).withContext(commandSender));
    }
}
