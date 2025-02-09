package net.aurika.text.compiler;

import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.auspice.utils.string.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Condition
 */
public final class TextLexer {
    private final int start;
    private final int end;
    private final char[] chars;
    private int index;
    private @NotNull List<Token> tokens;

    public TextLexer(int start, int end, char[] chars) {
        Objects.requireNonNull(chars, "");
        this.start = start;
        this.end = end;
        this.chars = chars;
        this.index = this.start;
        this.tokens = new ArrayList<>();
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public char[] getChars() {
        return this.chars;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NotNull
    public List<Token> getTokens() {
        return this.tokens;
    }

    public void setTokens(@NotNull List<Token> tokens) {
        Objects.requireNonNull(tokens);
        this.tokens = tokens;
    }

    private char getPointerChar() {
        return this.chars[this.index];
    }

    private int increaseIndex() {
        return this.index++;
    }

    @NotNull
    public String toString() {
        return "MessageLexer{" + CollectionsKt.joinToString(this.tokens, " - ", "", "", -1, "", null) + '}';
    }

    @Contract("_ -> fail")
    public void error(@NotNull String message) {
        Objects.requireNonNull(message);
        throw new IllegalArgumentException(message);
    }

    public void addToken(@NotNull Token token) {
        Objects.requireNonNull(token);
        this.tokens.add(token);
    }

    @NotNull
    public String subString(int var1, int var2) {
        if (var2 < var1) {
            throw new IllegalArgumentException("End is less than than start: " + var1 + " > " + var2);
        } else {
            return new String(ArraysKt.sliceArray(this.chars, RangesKt.until(var1, var2)));
        }
    }

    public int indexOf(char ch, int startIndex) {

        for (int var3 = this.chars.length; startIndex < var3; ++startIndex) {
            if (ch == this.chars[startIndex]) {
                return startIndex;
            }
        }

        return -1;
    }

    public void skipWhitespace() {
        TextLexer lexer = this;

        while (Strings.isWhitespace(lexer.getPointerChar())) {
            lexer.increaseIndex();
            if (lexer.index >= lexer.chars.length) {
                break;
            }
        }
    }

    @NotNull
    public String lexString() {
        boolean var1 = false;
        boolean var2 = false;
        StringBuilder var3 = new StringBuilder();
        TextLexer lexer = this;

        do {
            boolean var10000;
            label35:
            {
                label34:
                {
                    char pointerChar = lexer.getPointerChar();
                    boolean isBackSlash = false;
                    if (pointerChar == '\\') {
                        isBackSlash = true;
                    } else {
                        if (pointerChar == '"') {
                            if (!var1) {
                                var2 = true;
                                var10000 = false;
                                break label35;
                            }

                            var3.append('"');
                            var1 = false;
                            break label34;
                        }

                        var3.append(pointerChar);
                    }

                    if (isBackSlash) {
                        var1 = true;
                    } else if (var1) {
                        var3.append('\\');
                        var1 = false;
                    }
                }

                var10000 = true;
            }

            if (!var10000) {
                break;
            }

            lexer.increaseIndex();
        } while (lexer.index < lexer.chars.length);

        if (!var2) {
            this.error("Cannot find end of string");
        } else {
            this.increaseIndex();
        }

        return var3.toString();
    }

    public boolean expectStringLiteral() {
        this.skipWhitespace();
        if (this.getPointerChar() != '"') {
            return false;
        } else {
            this.increaseIndex();
            this.addToken(new StringToken(this.lexString()));
            return true;
        }
    }

    public void addCharToken(char ch) {
        this.addToken(new CharToken(ch));
    }

    public boolean hasNext() {
        return this.index < this.end;
    }

    public void lexConditional() {
        while (true) {
            this.skipWhitespace();
            int var1 = this.index;
            TextLexer lexer = this;

            while (lexer.getPointerChar() != '?') {
                lexer.increaseIndex();
                if (lexer.index >= lexer.chars.length) {
                    break;
                }
            }

            if (!this.hasNext()) {
                this.error("Cannot find end of condition for conditional message");
            }

            this.addToken(new StringToken(this.subString(var1, this.index)));
            this.addCharToken('?');
            this.increaseIndex();
            if (!this.expectStringLiteral()) {
                this.error("Expected a string literal for conditional message branch");
            }

            this.skipWhitespace();
            char var4;
            if ((var4 = this.getPointerChar()) != ';') {
                this.skipWhitespace();
                if ((var4 = this.getPointerChar()) == ':') {
                    this.addCharToken(var4);
                    this.increaseIndex();
                    if (!this.expectStringLiteral()) {
                        this.error("Expected a string literal for conditional message 'else' branch");
                    }
                }

                this.skipWhitespace();
                if (this.getPointerChar() != '}') {
                    this.error("Cannot find end of conditional message");
                }

                return;
            }

            this.addCharToken(var4);
            this.increaseIndex();
        }
    }

    public void lex() {
        this.lexConditional();
    }

    public static final class CharToken extends Token {
        private final char ch;

        public CharToken(char ch) {
            this.ch = ch;
        }

        public char getChar() {
            return this.ch;
        }

        @NotNull
        public String toString() {
            return "CharToken{" + this.ch + '}';
        }
    }

    public static final class StringToken extends Token {
        @NotNull
        private final String a;

        public StringToken(@NotNull String var1) {
            Objects.requireNonNull(var1);
            this.a = var1;
        }

        @NotNull
        public String getString() {
            return this.a;
        }

        @NotNull
        public String toString() {
            return "StringToken{" + this.a + '}';
        }
    }

    public abstract static class Token {
        public Token() {
        }
    }
}

