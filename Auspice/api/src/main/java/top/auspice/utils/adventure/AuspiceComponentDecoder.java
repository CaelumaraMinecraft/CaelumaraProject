package top.auspice.utils.adventure;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentDecoder;
import org.jetbrains.annotations.NotNull;
import net.aurika.text.TextObject;
import net.aurika.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.text.compiler.pieces.TextPiece;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;
import net.aurika.utils.Checker;

public class AuspiceComponentDecoder implements ComponentDecoder<TextObject, TextComponent> {
    public static final AuspiceComponentDecoder INSTANCE = new AuspiceComponentDecoder();

    @Override
    public @NotNull TextComponent deserialize(@NotNull TextObject input) {  // TODO
        Checker.Arg.notNull(input, "input");
        ComplexTextBuilderContextProvider builderProvider = new ComplexTextBuilderContextProvider(new MessagePlaceholderProvider());
        for (TextPiece piece : input.getPieces()) {
            piece.build(builderProvider);
        }
        return builderProvider.merge();
    }
}
