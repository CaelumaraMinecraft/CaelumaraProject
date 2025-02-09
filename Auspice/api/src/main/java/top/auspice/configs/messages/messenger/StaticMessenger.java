package top.auspice.configs.messages.messenger;

import org.jetbrains.annotations.NotNull;
import net.aurika.text.compiler.TextCompiler;
import net.aurika.text.TextObject;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.configs.messages.provider.SingleMessageProvider;
import top.auspice.diversity.Diversity;
import top.auspice.diversity.DiversityManager;
import top.auspice.server.command.CommandSender;
import net.aurika.utils.Checker;

public final class StaticMessenger implements Messenger {
    private final MessageProvider messageProvider;

    public StaticMessenger(@NotNull String str) {
        this(TextCompiler.compile(Checker.Arg.notNull(str, "str")));
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

    public void sendMessage(@NotNull CommandSender messageReceiver, MessagePlaceholderProvider textPlaceholderProvider) {
        this.getProvider(DiversityManager.localeOf(messageReceiver)).send(messageReceiver, textPlaceholderProvider);
    }
}
