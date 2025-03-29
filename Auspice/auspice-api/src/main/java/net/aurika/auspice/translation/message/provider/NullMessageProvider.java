package net.aurika.auspice.translation.message.provider;

import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.configs.messages.PrefixProvider;
import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.server.command.CommandSender;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class NullMessageProvider implements MessageProvider {

  public static final NullMessageProvider INSTANCE = new NullMessageProvider();
  private static final MessageObject nullMessage;

  private NullMessageProvider() {
  }

  public static NullMessageProvider getInstance() {
    return INSTANCE;
  }

  public @NonNull MessageObject message() {
    return nullMessage;
  }

  public void send(CommandSender messageReceiver, MessageContextImpl textPlaceholderProvider) {
  }

  static {
    nullMessage = new MessageObject(
        new TextObject(new TextPiece[0], TextCompilerSettings.none()), PrefixProvider.alwaysEmpty(), Boolean.FALSE);
  }
}