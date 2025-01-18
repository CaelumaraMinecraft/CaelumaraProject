package top.auspice.configs.messages.provider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.MessageObject;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.server.command.CommandSender;

import java.util.Arrays;

/**
 * The provider of {@linkplain TextObject}
 */
public interface MessageProvider {

    /**
     * @return The provided message
     */
    @Nullable MessageObject getMessage();

    void send(CommandSender messageReceiver, TextPlaceholderProvider textPlaceholderProvider);

    default void handleExtraServices(CommandSender messageReceiver, TextPlaceholderProvider placeholderProvider) {
    }

    static @NotNull MessageProvider combine(@NotNull MessageProvider @NotNull [] providers) {
        MessageObject message = TextObject.combine(Arrays.stream(providers).map(MessageProvider::getMessage).toArray(TextObject[]::new));
        return new SingleMessageProvider(message);
    }
}
