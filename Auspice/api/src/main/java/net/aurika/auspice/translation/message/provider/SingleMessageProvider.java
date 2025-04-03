package net.aurika.auspice.translation.message.provider;

import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.platform.command.CommandSender;
import org.jetbrains.annotations.Nullable;

public class SingleMessageProvider implements MessageProvider {

  private final @Nullable MessageObject message;

  public SingleMessageProvider(@Nullable MessageObject message) {
    this.message = message;
  }

  public @Nullable MessageObject message() {
    return this.message;
  }

  public void send(CommandSender sender, MessageContextImpl provider) {
    if (this.message != null) {
      sender.sendMessage(this.message);
    }

    this.handleExtraServices(sender, provider);
  }

}
