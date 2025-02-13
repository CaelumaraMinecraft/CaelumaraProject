package top.auspice.configs.messages;

import net.aurika.config.placeholders.context.MessagePlaceholderProvider;
import org.jetbrains.annotations.NotNull;
import top.auspice.server.command.CommandSender;
import top.auspice.server.entity.Player;

public class SimpleMessenger implements ContextualMessenger {
    protected final CommandSender sender;
    protected final MessagePlaceholderProvider messageSettings;

    public SimpleMessenger(CommandSender sender, MessagePlaceholderProvider var2) {
        this.sender = sender;
        this.messageSettings = var2;
    }

    public Player senderAsPlayer() {
        return (Player) this.sender;
    }

    public @NotNull MessagePlaceholderProvider getTextContext() {
        return this.messageSettings;
    }

    public @NotNull CommandSender getMessageReceiver() {
        return this.sender;
    }
}
