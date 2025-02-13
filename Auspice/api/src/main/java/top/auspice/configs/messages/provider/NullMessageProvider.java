package top.auspice.configs.messages.provider;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.auspice.configs.messages.MessageObject;
import top.auspice.configs.messages.PrefixProvider;
import net.aurika.text.compiler.TextCompilerSettings;
import net.aurika.text.TextObject;
import net.aurika.text.compiler.pieces.TextPiece;
import net.aurika.config.placeholders.context.MessagePlaceholderProvider;
import top.auspice.server.command.CommandSender;

public final class NullMessageProvider implements MessageProvider {
    public static final NullMessageProvider INSTANCE = new NullMessageProvider();
    private static final MessageObject nullMessage;

    private NullMessageProvider() {
    }

    public static NullMessageProvider getInstance() {
        return INSTANCE;
    }

    public @NonNull MessageObject getMessage() {
        return nullMessage;
    }

    public void send(CommandSender messageReceiver, MessagePlaceholderProvider textPlaceholderProvider) {
    }

    static {
        nullMessage = new MessageObject(new TextObject(new TextPiece[0], TextCompilerSettings.none()), PrefixProvider.alwaysEmpty(), Boolean.FALSE);
    }
}