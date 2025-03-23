package net.aurika.auspice.text.compiler.builders;

import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.HTMLTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.PlainTextBuilderContextProvider;
import net.aurika.auspice.text.context.TextContext;
import net.kyori.adventure.text.TextComponent;

public interface TextObjectBuilder {
    void build(ComplexTextBuilderContextProvider provider);

    void build(PlainTextBuilderContextProvider provider);

    void build(HTMLTextBuilderContextProvider provider);

    boolean isAvailable(TextContext provider);

    TextObject evaluateDynamicPieces(TextContext provider);

    default TextComponent buildComplex(TextContext provider) {
        ComplexTextBuilderContextProvider complexBuilder = new ComplexTextBuilderContextProvider(provider);
        this.build(complexBuilder);
        return complexBuilder.merge();
    }

    default String buildPlain(TextContext provider) {
        PlainTextBuilderContextProvider plainBuilder = new PlainTextBuilderContextProvider(provider);
        this.build(plainBuilder);
        return plainBuilder.merge();
    }

    default String build(TextContext provider) {
        return this.buildPlain(provider);
    }
}
