package net.aurika.auspice.text.compiler.builders;

import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.HTMLTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.PlainTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.translation.messenger.EnumDefinedMessenger;
import net.aurika.configuration.path.ConfigEntry;

import java.util.ArrayList;
import java.util.function.Function;

public class RawLanguageEntryObjectBuilder implements TextObjectBuilder {

  private final ConfigEntry path;
  private final Function<String, String> b;

  public RawLanguageEntryObjectBuilder(EnumDefinedMessenger path, Function<String, String> var2) {
    this.path = path.m();
    this.b = var2;
  }

  private String a(MessagePlaceholderProvider var1) {
    MessageProvider messageProvider = var1.getLanguage().getMessage(this.path, false);
    if (messageProvider == null) {
      return null;
    } else {
      String var4 = messageProvider.getMessage().build(var1);

      try {
        return this.b.apply(var4);
      } catch (Throwable var3) {
        throw new RuntimeException(
            "Error while running raw language entry processor for entry: " + messageProvider, var3);
      }
    }
  }

  public void build(ComplexTextBuilderContextProvider provider) {
    TextCompiler.compile(this.a(provider.settings())).build(provider);
  }

  public void build(PlainTextBuilderContextProvider provider) {
    provider.getCurrentLine().append(this.a(provider.settings()));
  }

  public void build(HTMLTextBuilderContextProvider provider) {
    MessageProvider var2;
    (var2 = provider.settings().getLanguage().getMessage(this.path, false)).getMessage().build(provider);
    ArrayList<HTMLTextBuilderContextProvider.HTMLElement> var3 = new ArrayList<>(provider.elements);
    var3.add(provider.getCurrentElement());
    var3.forEach((var2x) -> {
      try {
        var2x.text = this.b.apply(var2x.text);
      } catch (Throwable t) {
        throw new RuntimeException("Error while running raw language entry processor for entry: " + var2, t);
      }
    });
  }

  public boolean isAvailable(MessagePlaceholderProvider provider) {
    return provider.getLanguage().getMessage(this.path, false) != null;
  }

  public TextObject evaluateDynamicPieces(MessagePlaceholderProvider provider) {
    return new TextObject(
        new TextPiece[]{new TextPiece.Plain(this.a(provider))}, Boolean.FALSE, TextCompilerSettings.all());
  }

}
