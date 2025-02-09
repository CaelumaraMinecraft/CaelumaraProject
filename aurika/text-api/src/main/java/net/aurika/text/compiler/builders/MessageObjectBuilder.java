package net.aurika.text.compiler.builders;

import net.kyori.adventure.text.TextComponent;
import net.aurika.text.TextObject;
import net.aurika.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.text.compiler.builders.context.HTMLTextBuilderContextProvider;
import net.aurika.text.compiler.builders.context.PlainTextBuilderContextProvider;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;

public interface MessageObjectBuilder {
    void build(ComplexTextBuilderContextProvider provider);

    void build(PlainTextBuilderContextProvider provider);

    void build(HTMLTextBuilderContextProvider provider);

    boolean isAvailable(MessagePlaceholderProvider provider);

    TextObject evaluateDynamicPieces(MessagePlaceholderProvider provider);

    default TextComponent buildComplex(MessagePlaceholderProvider provider) {
        ComplexTextBuilderContextProvider complexBuilder = new ComplexTextBuilderContextProvider(provider);
        this.build(complexBuilder);
        return complexBuilder.merge();
    }

    default String buildPlain(MessagePlaceholderProvider provider) {
        PlainTextBuilderContextProvider plainBuilder = new PlainTextBuilderContextProvider(provider);
        this.build(plainBuilder);
        return plainBuilder.merge();
    }

    default String build(MessagePlaceholderProvider provider) {
        return this.buildPlain(provider);
    }
}
