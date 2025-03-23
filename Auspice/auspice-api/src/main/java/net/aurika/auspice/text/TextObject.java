package net.aurika.auspice.text;

import net.aurika.auspice.configs.messages.AuspiceLang;
import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.text.compiler.ColorAccessor;
import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.builders.TextObjectBuilder;
import net.aurika.auspice.text.compiler.builders.context.ComplexTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.HTMLTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.builders.context.PlainTextBuilderContextProvider;
import net.aurika.auspice.text.compiler.pieces.TextPiece;
import net.aurika.auspice.text.context.TextContext;
import net.aurika.auspice.translation.message.provider.AdvancedMessageProvider;
import net.aurika.auspice.translation.message.provider.MessageProvider;
import net.aurika.auspice.translation.message.provider.SingleMessageProvider;
import net.aurika.util.collection.UnsafeArrayList;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TextObject implements TextObjectBuilder {
    public static final TextObject NULL = new TextObject(TextPiece.Null.arrayInstance(), TextCompilerSettings.none());

    protected final TextPiece[] pieces;
    private final TextCompilerSettings settings;

    public TextObject(TextPiece @NotNull [] pieces, TextCompilerSettings settings) {
        this.pieces = pieces;
        this.settings = settings;
    }

    public static TextObject textObject(TextCompilerSettings settings, TextPiece... pieces) {
        return new TextObject(pieces, settings);
    }

    public TextCompilerSettings getCompilerSettings() {
        return this.settings;
    }

    public TextPiece validateAll(Predicate<TextPiece> validator) {
        for (TextPiece piece : this.pieces) {
            if (!validator.test(piece)) {
                return piece;
            }
        }
        return null;
    }

    public TextPiece[] pieces() {
        return this.pieces;
    }

    public boolean isNull() {
        return (this.pieces.length == 1 && this.pieces[0] instanceof TextPiece.Null);
    }

    public List<TextObject> splitBy(boolean var1, Predicate<TextPiece> splitter) {
        ArrayList<TextObject> var3 = new ArrayList<>(1);
        ArrayList<TextPiece> var4 = new ArrayList<>(this.pieces.length / 2);

        for (TextPiece piece : this.pieces) {
            if (splitter.test(piece)) {
                if (var1) {
                    var4.add(piece);
                }

                TextObject var10 = new TextObject(var4.toArray(new TextPiece[0]), this.settings);
                var3.add(var10);
                var4.clear();
            } else {
                var4.add(piece);
            }
        }

        if (var4.isEmpty()) {
            var4.add(TextPiece.Plain.EMPTY);
        }

        TextObject var9 = new TextObject(var4.toArray(new TextPiece[0]), this.settings);
        var3.add(var9);
        return var3;
    }

    public boolean hasPiece(@NotNull Predicate<TextPiece> filter) {
        return Arrays.stream(this.pieces).anyMatch(filter);
    }

    public int count(@NotNull Predicate<TextPiece> predicate) {
        return (int) Arrays.stream(this.pieces).filter(predicate).count();
    }

    /**
     * 将多个 {@linkplain TextObject} 合并至一个 {@linkplain TextObject} 中.
     * <p>合并后的文本对象将使用数组形参第一个文本对象的 {@linkplain TextCompilerSettings}
     *
     * @param texts 要合并的多个 {@linkplain TextObject}
     * @return 合并过后的 {@linkplain TextObject}
     */
    public static @Nullable("texts.length == 0") TextObject combine(@NotNull TextObject @NotNull ... texts) {
        Validate.Arg.nonNullArray(texts, "texts");
        if (texts.length == 0) {
            return null;
        } else if (texts.length == 1) {
            return texts[0];
        } else {
            TextObject first = texts[0];
            texts = Arrays.stream(texts).skip(1L).toArray(TextObject[]::new);
            return first.merge(texts);
        }
    }

    public TextObject merge(@NotNull TextObject @NotNull ... others) {
        TextPiece[] pieces = new TextPiece[this.pieces.length + Arrays.stream(others).mapToInt((text) -> text.pieces.length).sum()];
        System.arraycopy(this.pieces, 0, pieces, 0, this.pieces.length);
        int this_pieces_length = this.pieces.length;

        for (TextObject textObject : others) {
            System.arraycopy(textObject.pieces, 0, pieces, this_pieces_length, textObject.pieces.length);
            this_pieces_length += textObject.pieces.length;
        }

        return new TextObject(pieces, this.settings);
    }

    public TextObject filter(Predicate<TextPiece> msgPieceFilter) {
        Validate.Arg.notNull(msgPieceFilter, "msgPieceFilter");
        TextPiece[] var2 = Arrays.stream(this.pieces).filter(msgPieceFilter).toArray(TextPiece[]::new);
        return new TextObject(var2, this.settings);
    }

    public TextObject findLastColors() {
        List<TextPiece> var1 = this.findColorPieces(1, true);
        return var1.isEmpty() ? null : new TextObject(var1.toArray(new TextPiece[0]), this.settings);
    }

    public List<TextObject> splitLines() {
        List<TextObject> var1 = this.splitBy(false, (var0) -> var0 instanceof TextPiece.NewLine);
        ArrayList<TextObject> var2 = new ArrayList<>(var1.size());
        TextObject var3 = null;

        for (TextObject textObject : var1) {
            TextObject var4;
            TextObject var5 = (var4 = textObject).findLastColors();
            if (var3 != null) {
                var4 = var3.merge(var4);
            }

            var3 = var5;
            var2.add(var4);
        }

        return var2;
    }

    public List<TextPiece> findColorPieces(int range, boolean var2) {
        return ColorAccessor.of(this.pieces, range, var2);
    }

    public void build(ComplexTextBuilderContextProvider provider) {
        TextContext var2 = provider.settings();
        TextPiece[] var3;
        int var4;
        TextPiece var6;
        if (this.b(var2)) {
            var4 = (var3 = a(var2).pieces).length;

            for (int i = 0; i < var4; ++i) {
                var6 = var3[i];
                provider.build(var6);
            }
        }

        var4 = (var3 = this.pieces).length;

        for (int i = 0; i < var4; ++i) {
            var6 = var3[i];
            if (!var2.ignoreColors() || !(var6 instanceof TextPiece.Color)) {
                provider.build(var6);
            }
        }
    }

    private static MessageObject a(TextContext provider) {
        return AuspiceLang.PREFIX.getMessageObject(provider.diversity());
    }

    private boolean b(TextContext var1) {
        if (this.usePrefix != null) {
            return this.usePrefix;
        } else {
            Boolean b = var1.usePrefix();
            return b != null ? b : false;
        }
    }

    public void build(PlainTextBuilderContextProvider provider) {
        TextPiece[] var2;
        int var3;
        int var4;
        if (this.b(provider.settings())) {
            var3 = (var2 = a(provider.settings()).pieces).length;

            for (var4 = 0; var4 < var3; ++var4) {
                var2[var4].build(provider);
            }
        }

        var3 = (var2 = this.pieces).length;

        for (var4 = 0; var4 < var3; ++var4) {
            TextPiece var5 = var2[var4];
            if (!provider.settings().ignoreColors() || !(var5 instanceof TextPiece.Color)) {
                var5.build(provider);
            }
        }
    }

    public void build(HTMLTextBuilderContextProvider provider) {
        TextPiece[] var2;
        int var3;
        int var4;
        if (this.b(provider.settings())) {
            var3 = (var2 = a(provider.settings()).pieces).length;

            for (var4 = 0; var4 < var3; ++var4) {
                var2[var4].build(provider);
            }
        }

        var3 = (var2 = this.pieces).length;

        for (var4 = 0; var4 < var3; ++var4) {
            TextPiece var5 = var2[var4];
            if (!provider.settings().ignoreColors() || !(var5 instanceof TextPiece.Color)) {
                var5.build(provider);
            }
        }
    }

    public boolean isAvailable(TextContext provider) {
        return true;
    }

    public TextObject evaluateDynamicPieces(TextContext provider) {
        UnsafeArrayList<TextPiece> var2 = UnsafeArrayList.withSize(new TextPiece[this.pieces.length]);

        for (TextPiece piece : this.pieces) {
            if (piece instanceof TextPiece.Variable variablePiece) {
                Object placeholder = variablePiece.getVariable(provider);
                if (placeholder == null) {
                    var2.add(new TextPiece.Plain(variablePiece.getPlaceholder().asString(true)));
                } else if (placeholder instanceof TextObjectBuilder var9) {
                    var2.addAll(var9.evaluateDynamicPieces(provider).pieces);
                } else if (!(placeholder instanceof PlaceholderTranslationContext)) {
                    var2.add(new TextPiece.Plain(placeholder.toString()));
                } else {
                    TextObject var10 = variablePiece.getCompiled((PlaceholderTranslationContext) placeholder);
                    var2.addAll(var10.pieces);
                }
            } else if (piece instanceof TextPiece.ColorAccessor) {
                var2.addAll(((TextPiece.ColorAccessor) piece).getLastColors(provider));
            } else if (piece instanceof TextPiece.Conditional) {
                TextPiece[] var11 = ((TextPiece.Conditional) piece).getPiece(provider);
                var2.addAll(var11);
            } else {
                var2.add(piece);
            }
        }

        return new TextObject(var2.toArray(), this.settings);
    }

    public MessageProvider getSimpleProvider() {
        return new SingleMessageProvider(this);
    }

    public AdvancedMessageProvider getExtraProvider() {
        return new AdvancedMessageProvider(this, null, null);
    }

    public String toString() {
        if (this.pieces.length == 0) {
            return "MessageObject{}";
        } else {
            StringBuilder builder = new StringBuilder(this.pieces.length * 50);
            builder.append("MessageObject{");

            for (TextPiece piece : this.pieces) {
                builder.append("| ").append(piece);
            }

            return builder.append('}').toString();
        }
    }
}
