package top.auspice.configs.messages.provider;

import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.MessageObject;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.server.command.CommandSender;

public class SingleMessageProvider implements MessageProvider {


    private final @Nullable MessageObject message;

    public SingleMessageProvider(@Nullable MessageObject message) {
        this.message = message;
    }

    public @Nullable MessageObject getMessage() {
        return this.message;
    }

    public void send(CommandSender sender, TextPlaceholderProvider provider) {
        if (this.message != null) {
            sender.sendMessage(this.message);
        }

        this.handleExtraServices(sender, provider);
    }
}
