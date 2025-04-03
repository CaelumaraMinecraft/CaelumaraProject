package net.aurika.auspice.translation.messenger;

import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.translation.message.provider.SingleMessageProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

public final class ProcessedMessenger implements Messenger {

  private final Messenger a;
  private final Function<MessageProvider, MessageProvider> b;

  public ProcessedMessenger(Messenger var1, Function<MessageProvider, MessageProvider> var2) {
    this.a = Objects.requireNonNull(var1);
    this.b = Objects.requireNonNull(var2);
  }

  public MessageProvider getProvider(@NotNull Locale locale) {
    return this.b.apply(this.a.getProvider(locale));
  }

  public static final class RepeatProcessor implements Function<MessageProvider, MessageProvider> {

    private final int a;

    public RepeatProcessor(int var1) {
      this.a = var1;
    }

    public MessageProvider apply(MessageProvider var1) {
      TextObject var4 = var1.message();
      TextObject var2 = var4;

      for (int var3 = 1; var3 < this.a; ++var3) {
        var2 = var2.merge(new TextObject(var4.pieces(), Boolean.FALSE, var4.getCompilerSettings()));
      }

      return new SingleMessageProvider(var2);
    }

  }

}

