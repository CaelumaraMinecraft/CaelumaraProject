package net.aurika.auspice.configs.messages;

import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SimpleMessenger implements ContextualMessenger {

  protected final @NotNull CommandSender sender;
  protected final @NotNull MessageContext messageSettings;

  public SimpleMessenger(@NotNull CommandSender sender, @NotNull MessageContext messageSettings) {
    this.sender = sender;
    this.messageSettings = messageSettings;
  }

  public @NotNull Player senderAsPlayer() {
    return (Player) this.sender;
  }

  @Override
  public @NotNull MessageContext messageContext() {
    return this.messageSettings;
  }

  @Override
  public @NotNull CommandSender messageReceiver() {
    return this.sender;
  }

}
