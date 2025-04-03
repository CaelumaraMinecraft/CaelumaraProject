package net.aurika.auspice.utils.adventure;

import net.aurika.auspice.configs.messages.context.MessageContextImpl;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.util.Checker;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.ComponentDecoder;
import org.jetbrains.annotations.NotNull;

public class AuspiceComponentDecoder implements ComponentDecoder<TextObject, TextComponent> {

  public static final AuspiceComponentDecoder INSTANCE = new AuspiceComponentDecoder();

  @Override
  public @NotNull TextComponent deserialize(@NotNull TextObject input) {  // TODO
    Checker.Arg.notNull(input, "input");
    ComplexTextBuilderContextProvider builderProvider = new ComplexTextBuilderContextProvider(new MessageContextImpl());
    for (TextPiece piece : input.pieces()) {
      piece.build(builderProvider);
    }
    return builderProvider.merge();
  }

}
