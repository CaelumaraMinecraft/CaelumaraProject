package top.auspice.configs.texts.compiler.builders;

import net.kyori.adventure.text.TextComponent;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.compiler.builders.context.ComplexTextBuilderContextProvider;
import top.auspice.configs.texts.compiler.builders.context.HTMLTextBuilderContextProvider;
import top.auspice.configs.texts.compiler.builders.context.PlainTextBuilderContextProvider;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

public interface MessageObjectBuilder {
    void build(ComplexTextBuilderContextProvider provider);

    void build(PlainTextBuilderContextProvider provider);

    void build(HTMLTextBuilderContextProvider provider);

    boolean isAvailable(TextPlaceholderProvider provider);

    TextObject evaluateDynamicPieces(TextPlaceholderProvider provider);

    default TextComponent buildComplex(TextPlaceholderProvider provider) {
        ComplexTextBuilderContextProvider complexBuilder = new ComplexTextBuilderContextProvider(provider);
        this.build(complexBuilder);
        return complexBuilder.merge();
    }

    default String buildPlain(TextPlaceholderProvider provider) {
        PlainTextBuilderContextProvider plainBuilder = new PlainTextBuilderContextProvider(provider);
        this.build(plainBuilder);
        return plainBuilder.merge();
    }

    default String build(TextPlaceholderProvider provider) {
        return this.buildPlain(provider);
    }
}
