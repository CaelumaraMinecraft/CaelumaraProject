package net.aurika.auspice.text.compiler.placeholders;

import net.aurika.auspice.text.compiler.TextLexer;
import net.aurika.util.collection.CursorList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public final class TokenCursor extends CursorList<TextLexer.Token> {

  public TokenCursor(@NotNull List<TextLexer.Token> tokens) {
    super(tokens);
  }

  @Nullable
  public String expectString() {
    if (this.hasNext(1)) {
      Object var1 = this.next();
      TextLexer.StringToken var10000 = var1 instanceof TextLexer.StringToken ? (TextLexer.StringToken) var1 : null;
      if (var10000 != null) {
        return var10000.getString();
      }
    }

    return null;
  }

  @NotNull
  public String expectString(@NotNull String var1) {
    Objects.requireNonNull(var1, "");
    String var10000 = this.expectString();
    if (var10000 == null) {
      throw new IllegalStateException(var1);
    } else {
      return var10000;
    }
  }

  @Nullable
  public Character expectChar() {
    if (this.hasNext(1)) {
      TextLexer.Token token = this.next();
      if (token instanceof TextLexer.CharToken charToken) {
        return charToken.getChar();
      }
    }
    return null;
  }

  public char expectChar(@NotNull String var1) {
    Objects.requireNonNull(var1);
    Character var10000 = this.expectChar();
    if (var10000 != null) {
      return var10000;
    } else {
      throw new IllegalStateException(var1);
    }
  }

}

