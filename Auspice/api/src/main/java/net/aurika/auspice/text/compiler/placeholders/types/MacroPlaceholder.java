package net.aurika.auspice.text.compiler.placeholders.types;

import jline.internal.Nullable;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.placeholders.modifiers.PlaceholderModifier;
import net.aurika.text.messenger.StaticMessenger;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class MacroPlaceholder extends AbstractPlaceholder {

  @NotNull
  private final String macro;

  public MacroPlaceholder(@NotNull String var1, @NotNull String var2, @Nullable String var3, @NotNull List<PlaceholderModifier> var4) {
    super(var1, var3, var4);
    Objects.requireNonNull(var2, "");
    this.macro = var2;
  }

  @NotNull
  public String asString(boolean surround) {
    String var2 = this.getCommonString(false, this.macro);
    return surround ? "{$" + var2 + '}' : var2;
  }

  @Nullable
  public Object request(@NotNull PlaceholderProvider provider) {
    Objects.requireNonNull(provider, "");
    if (!(provider instanceof MessagePlaceholderProvider)) {
      return TextCompiler.compile("&cUnsupported provided context: " + provider);
    } else {
      Object var10000 = ((MessagePlaceholderProvider) provider).getLanguage().getVariable(
          (MessagePlaceholderProvider) provider, this.macro, false);
      if (var10000 == null) {
        var10000 = new StaticMessenger("&8(&4Unknown macro/variable &e'" + this.macro + "'&8)");
      }

      return var10000;
    }
  }

}
