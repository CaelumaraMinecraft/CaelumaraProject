package net.aurika.auspice.translation.messenger;

import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.translation.diversity.Diversity;
import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.translation.message.provider.SingleMessageProvider;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;

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

  public void sendMessage(@NotNull CommandSender messageReceiver, MessageContextImpl textPlaceholderProvider) {
    this.getProvider(MessageManager.diversityOf(messageReceiver)).send(messageReceiver, textPlaceholderProvider);
  }

}
