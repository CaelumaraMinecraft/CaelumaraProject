package top.auspice.configs.texts.compiler.builders;

import net.aurika.config.path.ConfigEntry;
import top.auspice.configs.texts.compiler.TextCompiler;
import top.auspice.configs.texts.compiler.TextCompilerSettings;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.compiler.builders.context.ComplexTextBuilderContextProvider;
import top.auspice.configs.texts.compiler.builders.context.HTMLTextBuilderContextProvider;
import top.auspice.configs.texts.compiler.builders.context.PlainTextBuilderContextProvider;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.messenger.EnumDefinedMessenger;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.configs.messages.provider.MessageProvider;

import java.util.ArrayList;
import java.util.function.Function;

public class RawLanguageEntryObjectBuilder implements MessageObjectBuilder {
    private final ConfigEntry path;
    private final Function<String, String> b;

    public RawLanguageEntryObjectBuilder(EnumDefinedMessenger path, Function<String, String> var2) {
        this.path = path.getLanguageEntry();
        this.b = var2;
    }

    private String a(TextPlaceholderProvider var1) {
        MessageProvider messageProvider = var1.getLanguage().getMessage(this.path, false);
        if (messageProvider == null) {
            return null;
        } else {
            String var4 = messageProvider.getMessage().build(var1);

            try {
                return this.b.apply(var4);
            } catch (Throwable var3) {
                throw new RuntimeException("Error while running raw language entry processor for entry: " + messageProvider, var3);
            }
        }
    }

    public void build(ComplexTextBuilderContextProvider provider) {
        TextCompiler.compile(this.a(provider.getSettings())).build(provider);
    }

    public void build(PlainTextBuilderContextProvider provider) {
        provider.getCurrentLine().append(this.a(provider.getSettings()));
    }

    public void build(HTMLTextBuilderContextProvider provider) {
        MessageProvider var2;
        (var2 = provider.getSettings().getLanguage().getMessage(this.path, false)).getMessage().build(provider);
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

    public boolean isAvailable(TextPlaceholderProvider provider) {
        return provider.getLanguage().getMessage(this.path, false) != null;
    }

    public TextObject evaluateDynamicPieces(TextPlaceholderProvider provider) {
        return new TextObject(new TextPiece[]{new TextPiece.Plain(this.a(provider))}, Boolean.FALSE, TextCompilerSettings.all());
    }
}
