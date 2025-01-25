package top.auspice.utils.adventure;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentDecoder;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.compiler.builders.context.ComplexTextBuilderContextProvider;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import net.aurika.utils.Checker;

public class AuspiceComponentDecoder implements ComponentDecoder<TextObject, TextComponent> {
    public static final AuspiceComponentDecoder INSTANCE = new AuspiceComponentDecoder();

    @Override
    public @NotNull TextComponent deserialize(@NotNull TextObject input) {  // TODO
        Checker.Argument.checkNotNull(input, "input");
        ComplexTextBuilderContextProvider builderProvider = new ComplexTextBuilderContextProvider(new TextPlaceholderProvider());
        for (TextPiece piece : input.getPieces()) {
            piece.build(builderProvider);
        }
        return builderProvider.merge();
    }
}
