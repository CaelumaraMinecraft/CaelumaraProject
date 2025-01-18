package top.auspice.configs.texts.messenger;

import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.compiler.TextCompiler;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.configs.messages.provider.SingleMessageProvider;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityManager;
import top.auspice.server.command.CommandSender;
import top.auspice.utils.Checker;

public final class StaticMessenger implements Messenger {
    private final MessageProvider messageProvider;

    public StaticMessenger(@NotNull String str) {
        this(TextCompiler.compile(Checker.Argument.checkNotNull(str, "str")));
    }

    public StaticMessenger(MessageProvider msgProvider) {
        this.messageProvider = msgProvider;
    }

    public StaticMessenger(TextObject msgObj) {
        this.messageProvider = new SingleMessageProvider(msgObj);
    }

    public MessageProvider getProvider(@NotNull Diversity diversity) {
        return this.messageProvider;
    }

    public void sendMessage(@NotNull CommandSender messageReceiver, TextPlaceholderProvider textPlaceholderProvider) {
        this.getProvider(DiversityManager.localeOf(messageReceiver)).send(messageReceiver, textPlaceholderProvider);
    }
}
