package top.auspice.configs.messages.provider;

import org.checkerframework.checker.nullness.qual.NonNull;
import top.auspice.configs.messages.MessageObject;
import top.auspice.configs.messages.PrefixProvider;
import top.auspice.configs.texts.compiler.TextCompilerSettings;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
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

    public void send(CommandSender messageReceiver, TextPlaceholderProvider textPlaceholderProvider) {
    }

    static {
        nullMessage = new MessageObject(new TextObject(new TextPiece[0], TextCompilerSettings.none()), PrefixProvider.alwaysEmpty(), Boolean.FALSE);
    }
}