package top.auspice.configs.texts;

import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;

public class SimpleMessenger implements ContextualMessenger {
    protected final CommandSender sender;
    protected final TextPlaceholderProvider messageSettings;

    public SimpleMessenger(CommandSender var1, TextPlaceholderProvider var2) {
        this.sender = var1;
        this.messageSettings = var2;
    }

    public Player senderAsPlayer() {
        return (Player) this.sender;
    }

    public @NotNull TextPlaceholderProvider getTextContext() {
        return this.messageSettings;
    }

    public @NotNull CommandSender getMessageReceiver() {
        return this.sender;
    }
}
