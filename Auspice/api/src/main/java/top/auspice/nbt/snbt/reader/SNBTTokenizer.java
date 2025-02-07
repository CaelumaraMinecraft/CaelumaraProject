package top.auspice.nbt.snbt.reader;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.internal.AbstractIterator;
import top.auspice.nbt.snbt.Elusion;
import top.auspice.nbt.snbt.reader.SNBTToken.*;
import top.auspice.nbt.stream.exception.NBTParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;

public final class SNBTTokenizer extends AbstractIterator<SNBTTokenWithMetadata> {
    @NotNull
    private final Reader input;
    private int charIndex;
//    private final boolean eatAllWhitespaceAfter;

    public SNBTTokenizer(@NotNull Reader input) {
        Intrinsics.checkNotNullParameter(input, "input");
        this.input = input.markSupported() ? input : new BufferedReader(input);
        this.charIndex = -1;
    }

    private String errorPrefix() {
        return "At character index " + this.charIndex + ": ";
    }

    private void skipWhitespace() throws IOException {
        while (true) {
            this.input.mark(1);
            int next = this.input.read();
            if (next != -1) {
                int var2 = this.charIndex++;
                if (Character.isWhitespace(next)) {
                    continue;
                }

                this.input.reset();
                var2 = this.charIndex;
                this.charIndex = var2 + -1;
            }

            return;
        }
    }

    @Nullable
    protected SNBTTokenWithMetadata computeNext() {
        try {
            this.skipWhitespace();
            this.input.mark(1);
            int next = this.input.read();
            if (next == -1) {
                return this.end();
            } else {
                int var2 = this.charIndex++;
                char var6 = (char) next;
                SNBTTokenWithMetadata var10000;
                if (var6 == '{') {
                    var10000 = new SNBTTokenWithMetadata(CompoundStart.INSTANCE, this.charIndex);
                } else if (var6 == '}') {
                    var10000 = new SNBTTokenWithMetadata(CompoundEnd.INSTANCE, this.charIndex);
                } else if (var6 == '[') {
                    var10000 = new SNBTTokenWithMetadata(ListLikeStart.INSTANCE, this.charIndex);
                } else if (var6 == ']') {
                    var10000 = new SNBTTokenWithMetadata(ListLikeEnd.INSTANCE, this.charIndex);
                } else if (var6 == ':') {
                    var10000 = new SNBTTokenWithMetadata(EntrySeparator.INSTANCE, this.charIndex);
                } else if (var6 == ';') {
                    var10000 = new SNBTTokenWithMetadata(ListTypeSeparator.INSTANCE, this.charIndex);
                } else if (var6 == ',') {
                    var10000 = new SNBTTokenWithMetadata(Separator.INSTANCE, this.charIndex);
                } else {
                    int initialCharIndex;
                    if (var6 == '"' || var6 == '\'') {
                        initialCharIndex = this.charIndex;
                        var10000 = new SNBTTokenWithMetadata(new Text(true, this.readQuotedText((char) next)), initialCharIndex);
                    } else {
                        this.input.reset();
                        initialCharIndex = this.charIndex;
                        this.charIndex = initialCharIndex + -1;
                        var10000 = this.readSimpleValue();
                    }
                }

                return var10000;
            }
        } catch (IOException var4) {
            throw new UncheckedIOException(var4);
        }
    }

    private SNBTTokenWithMetadata readSimpleValue() throws IOException {
        int initialCharIndex = this.charIndex + 1;
        StringBuilder builder = new StringBuilder();
        boolean wasWhitespace = false;

        while (true) {
            this.input.mark(1);
            int next = this.input.read();
            if (next == -1) {
                break;
            }

            int var5 = this.charIndex++;
            if (next == 44 || next == 58 || next == 59 || next == 125 || next == 93) {
                this.input.reset();
                var5 = this.charIndex;
                this.charIndex = var5 + -1;
                break;
            }

            if (Character.isWhitespace(next)) {
                wasWhitespace = true;
            } else {
                if (!Elusion.isSafeCharacter((char) next)) {
                    throw new NBTParseException(this.errorPrefix() + "Unexpected character: " + (char) next);
                }

                if (wasWhitespace) {
                    throw new NBTParseException(this.errorPrefix() + "Found non-terminator after whitespace");
                }

                builder.append((char) next);
            }
        }

        String var10005 = builder.toString();
        Intrinsics.checkNotNullExpressionValue(var10005, "toString(...)");
        return new SNBTTokenWithMetadata(new Text(false, var10005), initialCharIndex);
    }

    private String readQuotedText(char quoteChar) throws IOException {
        StringBuilder sb = new StringBuilder();
        boolean escaped = false;

        while (true) {
            int c = this.input.read();
            if (c == -1) {
                throw new NBTParseException(this.errorPrefix() + "Unexpected end of input in quoted value");
            }

            if (!escaped) {
                if (c == quoteChar) {
                    String var10000 = sb.toString();
                    Intrinsics.checkNotNullExpressionValue(var10000, "toString(...)");
                    return var10000;
                }

                if (c == 0x5c) {
                    escaped = true;
                    continue;
                }
            } else {
                if (c != quoteChar && c != 92) {
                    throw new NBTParseException(this.errorPrefix() + "Invalid escape: \\" + (char) c);
                }

                escaped = false;
            }

            sb.append((char) c);
        }
    }
}
