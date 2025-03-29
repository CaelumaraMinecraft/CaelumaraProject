package net.aurika.auspice.translation.message.provider;

import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.text.TextObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * The provider of {@linkplain TextObject}
 */
public interface MessageProvider {

  /**
   * @return The provided message
   */
  @Nullable MessageObject message();

  void send(CommandSender messageReceiver, MessageContextImpl textPlaceholderProvider);

  default void handleExtraServices(CommandSender messageReceiver, MessageContextImpl placeholderProvider) {
  }

  static @NotNull MessageProvider combine(@NotNull MessageProvider @NotNull [] providers) {
    MessageObject message = MessageObject.combine(
        Arrays.stream(providers).map(MessageProvider::message).toArray(MessageObject[]::new));  // TODO
    return new SingleMessageProvider(message);
  }

}
