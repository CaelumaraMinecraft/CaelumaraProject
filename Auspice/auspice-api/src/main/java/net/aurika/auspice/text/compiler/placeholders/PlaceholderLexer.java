package net.aurika.auspice.text.compiler.placeholders;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings({"", "BooleanMethodIsAlwaysInverted"})
public final class PlaceholderLexer {
    private final int a;
    private final char[] chars;
    private final boolean c;
    @NotNull
    private final List<Token> tokens;
    private final int e;
    private int index;
    @Nullable
    private Result result;

    public PlaceholderLexer(int var1, char[] chars, boolean var3) {
        Objects.requireNonNull(chars);
        this.a = var1;
        this.chars = chars;
        this.c = var3;
        this.tokens = new ArrayList<>();
        this.e = this.chars.length;
        this.index = this.a;
        PlaceholderLexer var4 = this;
        PlaceholderLexer var5 = this;

        do {
            int var6 = var5.chars[var5.index];
            CharToken var10000 = access$getCharToken(var4, (char) var6);
            if (var10000 != null) {
                access$addToken(var4, var10000);
            } else {
                access$untilNextChar(var4);
            }

            boolean var7;
            label44:
            {
                if (var4.getTokens().size() == 1 && var4.getTokens().get(0) instanceof CharToken) {
                    var4.setResult(PlaceholderLexer.Result.NOT_A_PLACEHOLDER);
                } else if (var4.getResult() == null) {
                    var7 = true;
                    break label44;
                }

                var7 = false;
            }

            if (!var7) {
                break;
            }

            var6 = var5.index++;
        } while (var5.index < var5.chars.length);

        if (var4.result == null) {
            if (var4.c) {
                var4.result = PlaceholderLexer.Result.CLOSING_CLOSURE_NOT_FOUND;
                return;
            }

            var4.result = PlaceholderLexer.Result.SUCCESS;
        }

    }

    @NotNull
    public List<Token> getTokens() {
        return this.tokens;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Nullable
    public Result getResult() {
        return this.result;
    }

    public void setResult(@Nullable Result result) {
        this.result = result;
    }

    @NotNull
    public String getFullString() {
        if (this.result != PlaceholderLexer.Result.SUCCESS) {
            throw new IllegalArgumentException("Cannot get full string of unsuccessful placeholder lexicon");
        } else {
            return this.c ? new String(this.chars, this.a, this.index - this.a) : new String(this.chars);
        }
    }

    private void a(Token var1) {
        if (var1 instanceof CharToken) {
            if (((CharToken) var1).getType() == PlaceholderLexer.CharTokenType.WHITESPACE) {
                if (this.tokens.isEmpty()) {
                    this.result = PlaceholderLexer.Result.NOT_A_PLACEHOLDER;
                }

                return;
            }

            if (((CharToken) var1).getType() == PlaceholderLexer.CharTokenType.CLOSURE) {
                if (!this.c) {
                    throw new IllegalStateException("Not expecting to look for placeholder closure, but found one at " + this.index + " -> " + new String(this.chars));
                }

                boolean var10000;
                label50:
                {
                    List<Token> var3 = this.tokens;
                    if (!var3.isEmpty()) {

                        for (Object o : var3) {
                            if (o instanceof StringToken) {
                                var10000 = true;
                                break label50;
                            }
                        }
                    }

                    var10000 = false;
                }

                if (!var10000) {
                    this.result = PlaceholderLexer.Result.NOT_A_PLACEHOLDER;
                    return;
                }

                this.result = PlaceholderLexer.Result.SUCCESS;
                return;
            }
        }

        this.tokens.add(var1);
    }

    public static boolean isNormal(@NotNull CharSequence var1) {
        Objects.requireNonNull(var1, "");

        for (int var2 = 0; var2 < var1.length(); ++var2) {
            char var3 = var1.charAt(var2);
            if (!PlaceholderLexer.isNormal(var3)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNormal(char ch) {
        return 'a' <= ch && ch < '{' || ('A' <= ch && ch < '[') || ('0' <= ch && ch < ':') || ch == '-';
    }


    public static CharToken access$getCharToken(PlaceholderLexer placeholderLexer, char c) {
        CharTokenType charTokenType = c == '*' ? CharTokenType.POINTER : (c == '_' ? CharTokenType.SEPARATOR : (c == ':' ? CharTokenType.FUNCTION_START : (c == '=' ? CharTokenType.FUNCTION_ARGUMENT_EQUAL : (c == ',' ? CharTokenType.FUNCTION_ARGUMENT_SEPARATOR : (c == '@' ? CharTokenType.MODIFIER : (c == '%' ? CharTokenType.CLOSURE : (CharsKt.isWhitespace(c) ? CharTokenType.WHITESPACE : null)))))));
        if (charTokenType == null) {
            return null;
        }
        return new CharToken(charTokenType, placeholderLexer.index);
    }

    public static void access$addToken(PlaceholderLexer placeholderLexer, Token token) {
        placeholderLexer.a(token);
    }

    public static void access$untilNextChar(PlaceholderLexer placeholderLexer) {
        StringBuilder stringBuilder = new StringBuilder(placeholderLexer.e / 2);
        boolean bl = true;
        CharToken charToken = null;
        int n = placeholderLexer.index;
        do {
            boolean bl2;
            int n2 = placeholderLexer.chars[placeholderLexer.index];
            CharToken charToken2 = PlaceholderLexer.access$getCharToken(placeholderLexer, (char)n2);
            if (charToken2 != null) {
                charToken = charToken2;
                bl2 = false;
            } else {
                stringBuilder.append((char)n2);
                if (!isNormal((char)n2)) {
                    bl = false;
                }
                bl2 = true;
            }
            if (!bl2) break;
            n2 = placeholderLexer.index;
            placeholderLexer.index = n2 + 1;
        } while (placeholderLexer.index < placeholderLexer.chars.length);
        String string = stringBuilder.toString();
        Intrinsics.checkNotNullExpressionValue(string, "");
        placeholderLexer.a(new StringToken(string, bl, n));
        CharToken charToken3 = charToken;
        if (charToken3 != null) {
            placeholderLexer.a(charToken3);
        }
    }


    public static final class CharToken extends Token {
        @NotNull
        private final CharTokenType type;

        public CharToken(@NotNull CharTokenType type, int startIndex) {
            super(startIndex);
            Objects.requireNonNull(type);
            this.type = type;
        }

        @NotNull
        public CharTokenType getType() {
            return this.type;
        }

        @NotNull
        public String toString() {
            return "Char{" + this.type + '}';
        }
    }

    public enum CharTokenType {
        WHITESPACE,                   //
        POINTER,                      //
        SEPARATOR,                    //
        FUNCTION_START,               //
        FUNCTION_ARGUMENT_EQUAL,      //
        FUNCTION_ARGUMENT_SEPARATOR,  //
        MODIFIER,                     //
        CLOSURE                       //
    }

    public enum Result {
        SUCCESS,                      //
        CLOSING_CLOSURE_NOT_FOUND,    //
        NOT_A_PLACEHOLDER             //
    }

    public static final class StringToken extends Token {
        @NotNull
        private final String string;
        private final boolean standard;

        public StringToken(@NotNull String string, boolean standard, int var3) {
            super(var3);
            Objects.requireNonNull(string);
            this.string = string;
            this.standard = standard;
        }

        @NotNull
        public String getString() {
            return this.string;
        }

        public boolean getStandard() {
            return this.standard;
        }

        @NotNull
        public String toString() {
            return "String{" + this.string + '}';
        }
    }

    public abstract static class Token {
        private final int startIndex;

        public Token(int startIndex) {
            this.startIndex = startIndex;
        }

        public final int getStartIndex() {
            return this.startIndex;
        }
    }
}

