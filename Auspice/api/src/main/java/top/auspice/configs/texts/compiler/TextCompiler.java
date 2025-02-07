package top.auspice.configs.texts.compiler;

import com.cryptomorin.xseries.reflection.XReflection;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.Locale;
import top.auspice.configs.texts.MessageHandler;
import top.auspice.configs.texts.compiler.pieces.TextPiece;
import top.auspice.configs.texts.compiler.placeholders.Placeholder;
import top.auspice.configs.texts.compiler.placeholders.PlaceholderParser;
import top.auspice.configs.texts.compiler.placeholders.TokenCursor;
import top.auspice.configs.texts.compiler.placeholders.types.LanguagePathPlaceholder;
import top.auspice.configs.texts.compiler.placeholders.types.MacroPlaceholder;
import top.auspice.configs.texts.messenger.LanguageEntryMessenger;
import top.auspice.configs.texts.placeholders.StandardKingdomsPlaceholder;
import top.auspice.utils.Pair;
import top.auspice.utils.chat.CodedChatFormats;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.string.Strings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class TextCompiler {
    public static final TextCompilerSettings DEFAULT_COMPILER_SETTINGS = defaultSettingsWithErrorHandler(null);
    public static final boolean CONTENT = XReflection.supports(16);
    private final @NotNull List<TextPiece> pieces;
    private final @NotNull StringBuilder plain;
    private final char @NotNull [] chars;
    private final int length;
    private int index;
    private char pointerChar;

    private final TextCompilerSettings settings;

    private final List<TextCompilerException> exceptions;
    private boolean isCompiled;
    private int backRefIndex;

    public char @NotNull [] getChars() {
        return this.chars;
    }

    public @NotNull String getRemainingString() {
        return new String(this.chars, this.index, this.chars.length - this.index);
    }

    public static TextCompilerSettings defaultSettingsWithErrorHandler(Consumer<TextCompiler> errorHandler) {
        return (new TextCompilerSettings(true, false, true, true, true, null)).withErrorHandler(errorHandler);
    }

    public static TextCompilerSettings defaultSettings() {
        return new TextCompilerSettings(true, false, true, true, true, null);
    }

    public List<TextCompilerException> getExceptions() {
        return this.exceptions;
    }

    public boolean hasErrors() {
        return !this.exceptions.isEmpty();
    }

    public String joinExceptions() {
        StringBuilder builder = new StringBuilder(this.exceptions.size() * this.length << 1);
        int count = this.exceptions.size();

        for (TextCompilerException exception : this.exceptions) {
            builder.append(exception.getMessage());
            --count;
            if (count > 0) {
                builder.append('\n');
            }
        }

        return builder.toString();
    }

    public TextCompilerSettings getSettings() {
        return settings;
    }

    public int getIndex() {
        return this.index;
    }

    public char getChar() {
        return this.pointerChar;
    }

    public boolean isCompiled() {
        return this.isCompiled;
    }

    public TextCompiler(String str) {
        this(str, DEFAULT_COMPILER_SETTINGS);
    }

    public TextCompiler(String str, TextCompilerSettings settings) {
        this(str.toCharArray(), settings);
    }

    public TextCompiler(char[] chars, TextCompilerSettings settings) {
        this.pieces = new ArrayList<>(10);
        this.exceptions = new ArrayList<>();
        this.isCompiled = false;
        this.backRefIndex = -1;
        this.settings = Objects.requireNonNull(settings);
        this.chars = chars;
        this.length = chars.length;
        this.plain = new StringBuilder(this.length);
    }

    public @NotNull List<TextPiece> getPieces() {
        return this.pieces;
    }

    private void validate_repeatedColor() {
        if (this.settings.validate) {
            if (this.pieces.size() >= 2) {
                TextPiece next = this.pieces.get(this.pieces.size() - 1);
                TextPiece prev = this.pieces.get(this.pieces.size() - 2);
                if (next instanceof TextPiece.Color && prev instanceof TextPiece.Color) {
                    if (next.equals(prev)) {
                        this.exception(this.index - 2, "Repeated chat code", "||||");
                    } else {
                        boolean nextIsHex = next instanceof TextPiece.HexTextColor;
                        boolean prevIsHex = prev instanceof TextPiece.HexTextColor;
                        if (!nextIsHex) {
                            nextIsHex = ((TextPiece.SimpleFormat) next).getColor().isColor();
                        }

                        if (!prevIsHex) {
                            prevIsHex = ((TextPiece.SimpleFormat) prev).getColor().isColor();
                        }

                        if (nextIsHex && prevIsHex) {
                            this.exception(this.index - 2, "Two non-formatting colors cannot follow each other as it's overridden by the later.", "||||");
                        } else {
                            if (!prevIsHex && nextIsHex) {
                                this.exception(this.index - 2, "A formatting chat cannot follow a non-formatting chat as it's overridden by the later. Consider putting the formatting chat after the non-formatting one.", "||||");
                            }
                        }
                    }
                }
            }
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(30);

        for (TextPiece piece : this.pieces) {
            builder.append(" | ");
            builder.append(piece.toString());
        }

        return builder.toString();
    }

    public static void test() {
        compile("&e%other*kingdoms_kingdom_name%'s &cchampion was brutally slashed.");
    }

    public static TextObject compile(String str) {
        return compile(str, true, false);
    }

    public static TextObject compile(String str, boolean validate, boolean plainOnly) {
        return compile(str, validate, plainOnly, null);
    }

    public static TextObject compile(String str, boolean validate, boolean plainOnly, TextTokenHandler tokenHandler) {
        TextTokenHandler[] tokenHandlers = tokenHandler == null ? null : new TextTokenHandler[]{tokenHandler};
        return compile(str, new TextCompilerSettings(validate, plainOnly, true, true, true, tokenHandlers));
    }

    public static TextObject compile(@NotNull String str, @NotNull TextCompilerSettings settings) {
        Objects.requireNonNull(str, "Cannot compile a null message");
        Objects.requireNonNull(settings, "Cannot compile with null compiler settings");
        TextCompiler compiler = new TextCompiler(str.toCharArray(), settings);
        TextObject message = compiler.compileObject();
        if (compiler.hasErrors()) {
            if (settings.errorHandler == null) {
                throw new TextCompilerException("{UNCAUGHT}", "[UNCAUGHT]", 0, compiler.joinExceptions());
            }

            settings.errorHandler.accept(compiler);
        }

        return message;
    }

    public TextPiece[] compileToArray() {
        this.compile();
        return this.pieces.toArray(new TextPiece[0]);
    }

    public TextObject compileObject() {
        return new TextObject(this.compileToArray(), this.settings);
    }

    public static HoverEvent<Component> constructHoverEvent(@NotNull Component @NotNull [] components) {
        TextComponent component = Component.textOfChildren(components);
        return HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT, component);
    }

    /**
     * 主流程控制方法
     */
    public void compile() {
        if (!this.isCompiled) {
            rawChars:
            for (this.isCompiled = true; this.index < this.length; ++this.index) {
                this.pointerChar = this.chars[this.index];
                int secondPointer;

                if (this.settings.tokenHandlers != null) {

                    for (TextTokenHandler handler : this.settings.tokenHandlers) {
                        TextTokenResult result = handler.consumeUntil(this);
                        if (result != null) {
                            if (result.index <= this.index) {
                                throw new IllegalStateException("Less or no characters consumed for token: " + this.index + " -> " + result.index + " using token handler " + handler);
                            }

                            this.compile_clearPlain();
                            this.pieces.add(result.piece);
                            this.index = result.index;
                            continue rawChars;
                        }
                    }
                }

                int cache;
                String errMsg;
                switch (this.pointerChar) {
                    case '\n':
                        if (!this.settings.allowNewLines) {
                            this.plain.append('\n');
                        } else {
                            this.compile_clearPlain();
                            this.pieces.add(new TextPiece.NewLine());
                        }
                        break;
                    case '%':
                        if (!this.settings.translatePlaceholders) {
                            this.plain.append('%');
                        } else {
                            if (this.compile_end()) {
                                break rawChars;
                            }

                            PlaceholderParser placeholderParser = PlaceholderParser.parse(this.index + 1, this.chars, true);
                            if (placeholderParser != null) {
                                secondPointer = placeholderParser.getEndIndex();

                                try {
                                    Placeholder placeholder = placeholderParser.getPlaceholder();
                                    this.compile_clearPlain();
                                    this.pieces.add(new TextPiece.Variable(placeholder));
                                    this.index = secondPointer;
                                } catch (Throwable throwable) {
                                    if (this.settings.validate) {
                                        this.exception(this.index, throwable.getMessage(), new String(this.chars, this.index, secondPointer - this.index));
                                    }

                                    int var10004 = this.index;
                                    this.plain.append(this.chars, var10004, secondPointer - var10004 + 1);
                                }
                            } else {
                                this.plain.append('%');
                            }
                        }
                        break;
                    case '&':
                    case '§':
                        if (!this.settings.colorize) {
                            this.plain.append('&');
                        } else {
                            if (this.compile_end()) {
                                break rawChars;
                            }

                            if (MessageHandler.isColorCode((char) (cache = this.chars[this.index + 1]))) {
                                this.compile_clearPlain();
                                this.pieces.add(new TextPiece.SimpleFormat(Objects.requireNonNull(CodedChatFormats.getByChar((char) (cache | 32)))));
                                this.validate_repeatedColor();
                                ++this.index;
                            } else {
                                StringBuilder var17;
                                if (this.pointerChar == '&' && cache == 35) {
                                    if (this.index + 1 >= this.length - 6) {
                                        this.exception(this.index, "Unfinished hex chat", new String(this.chars, this.index, this.length - this.index));
                                        break rawChars;
                                    }

                                    var17 = new StringBuilder(6);
                                    ++this.index;

                                    while (var17.length() != 6) {
                                        if (!MessageHandler.isColorCode((char) (cache = this.chars[++this.index]))) {
                                            int var10001 = this.index;
                                            errMsg = "Invalid hex chat character '" + cache + "' or possibly unfinished hex chat";
                                            secondPointer = var10001;
                                            this.exception(secondPointer, errMsg, null);
                                            continue rawChars;
                                        }

                                        var17.append((char) cache);
                                    }

                                    this.compile_clearPlain();
                                    TextColor color = TextColor.color(Integer.parseInt(var17.toString(), 16));
                                    this.pieces.add(new TextPiece.HexTextColor(color));
                                    this.validate_repeatedColor();
                                } else if (this.pointerChar == 167 && cache == 120) {
                                    if (this.index + 1 >= this.length - 12) {
                                        break rawChars;
                                    }

                                    var17 = new StringBuilder(6);
                                    ++this.index;
                                    boolean var19 = true;

                                    while (var17.length() != 6) {
                                        cache = this.chars[++this.index];
                                        if (var19) {
                                            var19 = false;
                                            if (cache != 167) {
                                                continue rawChars;
                                            }
                                        } else {
                                            var19 = true;
                                            if (!MessageHandler.isColorCode((char) cache)) {
                                                continue rawChars;
                                            }

                                            var17.append((char) cache);
                                        }
                                    }

                                    this.compile_clearPlain();
                                    TextColor var21 = TextColor.color(Integer.parseInt(var17.toString(), 16));
                                    this.pieces.add(new TextPiece.HexTextColor(var21));
                                    this.validate_repeatedColor();
                                } else {
                                    this.plain.append('&');
                                }
                            }
                        }
                        break;
                    case '{':
                        if (this.compile_end()) {
                            break rawChars;
                        }

                        TextPiece afterBracePiece = this.compile_afterBrace();
                        if (afterBracePiece != null) {
                            this.pieces.add(afterBracePiece);
                        } else {
                            this.plain.append('{');
                        }
                        break;
                    default:
                        if (!this.settings.plainOnly && this.pointerChar == 'h') {
                            int hover_head_index = 1;
                            cache = 1;
                            if (this.index + 7 <= this.length) {
                                while (hover_head_index < 7) {
                                    this.pointerChar = this.chars[++this.index];
                                    if (this.pointerChar != "hover:{".charAt(hover_head_index)) {
                                        this.plain.append("hover:{", 0, hover_head_index);
                                        --this.index;
                                        continue rawChars;
                                    }

                                    ++cache;
                                    ++hover_head_index;
                                }

                                ++this.index;
                                TextCompiler textCompiler = this;
                                HoverParserState hover_state = TextCompiler.HoverParserState.NORMAL_MESSAGE;
                                StringBuilder hover_normalMessage = new StringBuilder();
                                StringBuilder hover_hoverMessage = new StringBuilder();
                                StringBuilder hover_clickAction = new StringBuilder();
                                ClickEvent.Action action = null;
                                int var7 = 0;
                                int var8 = 0;
                                int var9 = 0;
                                int var10 = this.index;

                                TextPiece.Hover hover;
                                label162:
                                while (true) {
                                    if (var10 >= textCompiler.length) {
                                        String var33 = var9 == 0 ? "" : ". There are also " + var9 + " remaining curly bracket(s) that need to closed.";
                                        textCompiler.exception(textCompiler.index, "Unclosed hover message" + var33, "hover:{");
                                        hover = null;
                                        break;
                                    }

                                    char hover_indexChar;
                                    label159:
                                    switch (hover_indexChar = textCompiler.chars[var10]) {
                                        case ';':
                                            var9 = 0;
                                            switch (hover_state) {
                                                case NORMAL_MESSAGE:
                                                    var7 = var10;
                                                    if (hover_normalMessage.isEmpty()) {
                                                        errMsg = "Normal message is empty";
                                                        textCompiler.exception(var10, errMsg, null);
                                                        hover = null;
                                                        break label162;
                                                    }

                                                    hover_state = TextCompiler.HoverParserState.HOVER_MESSAGE;
                                                    break label159;
                                                case HOVER_MESSAGE:
                                                    var8 = var10;
                                                    hover_state = TextCompiler.HoverParserState.ACTION;
                                                    break label159;
                                                case ACTION:
                                                    errMsg = "An extra separator found. Hover texts only take two separators";
                                                    textCompiler.exception(var10, errMsg, null);
                                                    hover = null;
                                                    break label162;
                                                default:
                                                    break label159;
                                            }
                                        case '}':
                                            if (var9 == 0) {
                                                if (hover_state == TextCompiler.HoverParserState.NORMAL_MESSAGE && hover_hoverMessage.isEmpty()) {
                                                    errMsg = "Hover message is empty";
                                                    textCompiler.exception(var10, errMsg, null);
                                                    hover = null;
                                                    break label162;
                                                }

                                                if (hover_state == TextCompiler.HoverParserState.ACTION && action == null) {
                                                    errMsg = "Hover message action is empty";
                                                    textCompiler.exception(var10, errMsg, null);
                                                    hover = null;
                                                    break label162;
                                                }

                                                TextCompiler normalTextCompiler = new TextCompiler(copyChars(hover_normalMessage), textCompiler.settings);
                                                TextCompiler hoverTextCompiler = new TextCompiler(copyChars(hover_hoverMessage), textCompiler.settings);
                                                TextCompiler clickActionCompiler = new TextCompiler(copyChars(hover_clickAction), textCompiler.settings);
                                                TextPiece.Hover var31 = new TextPiece.Hover(action, normalTextCompiler.compileToArray(), hoverTextCompiler.compileToArray(), clickActionCompiler.compileToArray());
                                                textCompiler.mergeExceptions(textCompiler.index, normalTextCompiler);
                                                textCompiler.mergeExceptions(var7, hoverTextCompiler);
                                                textCompiler.mergeExceptions(var8, clickActionCompiler);
                                                textCompiler.index = var10;
                                                hover = var31;
                                                break label162;
                                            }

                                            --var9;
                                            switch (hover_state) {
                                                case NORMAL_MESSAGE:
                                                    hover_normalMessage.append('}');
                                                    break label159;
                                                case HOVER_MESSAGE:
                                                    hover_hoverMessage.append('}');
                                                    break label159;
                                                case ACTION:
                                                    hover_clickAction.append('}');
                                                default:
                                                    break label159;
                                            }
                                        default:
                                            if (hover_indexChar == '{') {
                                                ++var9;
                                            }

                                            switch (hover_state) {
                                                case NORMAL_MESSAGE:
                                                    hover_normalMessage.append(hover_indexChar);
                                                    break;
                                                case HOVER_MESSAGE:
                                                    hover_hoverMessage.append(hover_indexChar);
                                                    break;
                                                case ACTION:
                                                    if (action == null) {
                                                        switch (hover_indexChar) {
                                                            case '@':
                                                                action = ClickEvent.Action.OPEN_URL;
                                                                break;
                                                            case '|':
                                                                action = ClickEvent.Action.SUGGEST_COMMAND;
                                                                break;
                                                            default:
                                                                action = ClickEvent.Action.RUN_COMMAND;
                                                                hover_clickAction.append(hover_indexChar);
                                                        }
                                                    } else {
                                                        hover_clickAction.append(hover_indexChar);
                                                    }
                                            }
                                    }

                                    ++var10;
                                }

                                TextPiece.Hover var29 = hover;
                                if (hover != null) {
                                    this.compile_clearPlain();
                                    this.pieces.add(var29);
                                }
                                continue;
                            }
                        }

                        this.plain.append(this.pointerChar);
                }
            }

            this.compile_clearPlain();
        }
    }

    private String validate_specialType(SpecialType spType) {
        this.compile_clearPlain();
        StringBuilder builder = new StringBuilder(10);
        int i = this.index;
        ++i;

        while (i + 1 < this.length) {
            ++i;
            char pointChar = this.chars[i];
            String msg;
            if (pointChar == '}') {
                this.index = i;
                if (builder.isEmpty()) {
                    msg = "Empty " + spType.name;
                    this.exception(i, msg, null);
                    return null;
                }

                return builder.toString();
            }

            if (spType == TextCompiler.SpecialType.BACKREF && this.backRefIndex != -1) {
                if (pointChar != '-' && (pointChar < '0' || pointChar > '9') && (pointChar != ' ' || !builder.isEmpty())) {
                    msg = "Invalid character '" + pointChar + "' in " + TextCompiler.SpecialType.BACKREF.name + " chat specifier expected a number";
                    this.exception(i, msg, null);
                    return null;
                }

                builder.append(pointChar);
            } else if (pointChar == '&') {
                if (spType != TextCompiler.SpecialType.BACKREF) {
                    msg = "Invalid character '&' in " + spType.name + " specifier";
                    this.exception(i, msg, null);
                    return null;
                }

                this.backRefIndex = i;
            } else {
                if (
                        ((spType != SpecialType.HEX) || (pointChar < '0') || (pointChar > '9'))
                                && ((pointChar < 'a') || (pointChar > 'z')) // Not a-z
                                && ((pointChar < 'A') || (pointChar > 'Z')) // Not A-Z
                                && ((spType != SpecialType.MACRO) || ((pointChar != '-') && (pointChar != '_')))
                                && ((spType != SpecialType.LANGUAGE_PATH) || ((pointChar != '-') && (pointChar != '_') && (pointChar != '.') && ((pointChar < '0') || (pointChar > '9'))))
                                && ((spType != SpecialType.BACKREF) || (pointChar != ' '))) {
                    if (pointChar == ' ') {
                        msg = "Spaces aren't allowed in " + spType.name + " (Did you forget to close the braces with '}'?)";
                        this.exception(i, msg, null);
                    } else {
                        msg = "Invalid character '" + pointChar + "' in " + spType.name;
                        this.exception(i, msg, null);
                    }

                    return null;
                }

                builder.append(pointChar);
            }
        }

        this.exception(this.index, "Cannot find end of " + spType.name, "{" + this.chars[this.index]);
        return null;
    }

    /**
     * 主流程控制
     * <p>
     * 完成 '{' 后面的内容时调用, 此时指针应该在 '{' 前面
     * <p>
     * {?      Condition
     * <p>
     * {$$     Language path
     * <p>
     * {$      Macro
     * <p>
     * {#      Hex chat
     * <p>
     * {&      Advance chat   //TODO
     * <p>
     * {math:  Math           //TODO
     * <p>
     * {hover: Hover message  //TODO
     * <p>
     * <p>
     * <p>
     * <p>
     * <p>
     */
    private TextPiece compile_afterBrace() {
        int pointer_2 = this.index + 2;
        String msg;
        String str_after_pt_2;
        switch (this.chars[this.index + 1]) {
            case '#':
                if (!this.settings.colorize) {
                    return null;
                } else {
                    String hexColorCode = this.validate_specialType(TextCompiler.SpecialType.HEX);
                    if (hexColorCode == null) {
                        return null;
                    } else if (hexColorCode.length() != 3 && hexColorCode.length() != 6) {
                        this.exception(pointer_2, "Invalid hex chat length. 3 digit and 6 digit formats are supported", hexColorCode);
                        return null;
                    } else {
                        try {
                            java.awt.Color color = new java.awt.Color(Integer.parseInt(hexColorCode, 16));
                            return new TextPiece.HexTextColor(color);
                        } catch (NumberFormatException exc) {
                            this.exception(pointer_2, "Invalid hex chat", hexColorCode);
                            return null;
                        }
                    }
                }
            case '$':     //Macro or language path
                if (!this.settings.translatePlaceholders) {
                    return null;
                } else {
                    boolean isLanguagePath = false;
                    if (this.chars[pointer_2] == '$') {
                        isLanguagePath = true;
                        ++this.index;
                    }

                    if ((str_after_pt_2 = this.validate_specialType(isLanguagePath ? TextCompiler.SpecialType.LANGUAGE_PATH : TextCompiler.SpecialType.MACRO)) == null) {
                        return null;
                    } else if (isLanguagePath) {
                        return new TextPiece.Variable(new LanguagePathPlaceholder(this.getString(pointer_2 - 2, this.index), LanguageEntryMessenger.of(str_after_pt_2), null, new ArrayList<>()));
                    } else {
                        Object var22 = StandardKingdomsPlaceholder.getRawMacro(str_after_pt_2);
                        if (var22 == null) {
                            var22 = Locale.getDefault().getVariableRaw(str_after_pt_2);
                        }

                        if (var22 == null) {
                            String var24;
                            if ((var24 = Strings.findSimilar(str_after_pt_2, StandardKingdomsPlaceholder.getGlobalMacros().keySet())) == null) {
                                var24 = "";
                            } else {
                                var24 = " Did you mean '" + var24 + "'?";
                            }

                            this.exception(pointer_2, "Unknown macro '" + str_after_pt_2 + '\'' + var24, str_after_pt_2);
                            return null;
                        }

                        return new TextPiece.Variable(new MacroPlaceholder(this.getString(pointer_2 - 2, this.index), str_after_pt_2, null, new ArrayList<>()));
                    }
                }
            case '%':
                if (!this.settings.colorize) {
                    return null;
                } else {
                    PlaceholderParser placeholderParser = PlaceholderParser.parse(this.index + 2, this.chars, true);
                    Placeholder placeholder = null;
                    boolean isPlaceholder = placeholderParser != null;
                    Throwable placeholder_exc = null;
                    if (isPlaceholder) {
                        try {
                            placeholder = placeholderParser.getPlaceholder();
                        } catch (Throwable throwable) {
                            placeholder_exc = throwable;
                            isPlaceholder = false;
                        }
                    }

                    if (!isPlaceholder) {
                        msg = "Could not parse placeholder for chat accessor " + (placeholder_exc == null ? "" : placeholder_exc.getMessage());
                        this.exception(pointer_2, msg, null);
                        return null;
                    } else {
                        TextPiece.Variable var25 = new TextPiece.Variable(placeholder);
                        this.index = placeholderParser.getEndIndex();
                        if ((str_after_pt_2 = this.validate_specialType(TextCompiler.SpecialType.BACKREF)) == null) {
                            return null;
                        } else {
                            str_after_pt_2 = str_after_pt_2.trim();
                            int colorAccessorIndex = -1;
                            if (!str_after_pt_2.isEmpty()) {
                                try {
                                    colorAccessorIndex = Integer.parseInt(str_after_pt_2);   // ten radix
                                } catch (NumberFormatException exception) {
                                    this.exception(this.backRefIndex + 1, "Invalid chat accessor index '" + str_after_pt_2.trim() + '\'', str_after_pt_2);
                                    return null;
                                }
                            }

                            if (colorAccessorIndex == 0) {
                                this.exception(this.backRefIndex + 1, "Color accessor cannot have an index of 0", str_after_pt_2);
                                return null;
                            }

                            return new TextPiece.ColorAccessor(colorAccessorIndex, var25);
                        }
                    }
                }
            case '?':
                TextLexer textLexer = new TextLexer(pointer_2, this.length, this.chars);

                try {
                    textLexer.lex();
                } catch (Exception exception) {
                    this.exception(textLexer.getIndex(), exception.getMessage(), null);
                }

                List<TextLexer.Token> tokens = textLexer.getTokens();
                ArrayList<Pair<ConditionCompiler.LogicalOperand, TextPiece[]>> var4 = new ArrayList<>();
                TokenCursor var5 = new TokenCursor(tokens);
                boolean condition_failed = false;

                while (var5.hasNext()) {
                    if ((str_after_pt_2 = var5.expectString()) == null) {
                        var5.previous();
                        break;
                    }

                    if (var5.expectChar("Expected conditional message ? conditional separator") != '?') {
                        msg = "Expected conditional message ? condition separator";
                        this.exception(pointer_2, msg, null);
                    }

                    String var7 = var5.expectString("Expected conditional message string");
                    var4.add(Pair.of(ConditionCompiler.compile(str_after_pt_2).evaluate(), compile(var7).getPieces()));
                    Character var18 = var5.expectChar();
                    if (var18 != null && var18 != ';') {
                        if (var18 == ':') {
                            condition_failed = true;
                            break;
                        }

                        msg = "Unknown character in conditional message: " + var18;
                        this.exception(pointer_2, msg, null);
                    }
                }

                if (condition_failed) {
                    str_after_pt_2 = var5.expectString("Expected otherwise message for conditional message");
                    var4.add(Pair.of(ConditionCompiler.ConstantLogicalOperand.TRUE, compile(str_after_pt_2).getPieces()));
                }

                this.compile_clearPlain();
                this.index = textLexer.getIndex();
                return new TextPiece.Conditional(var4);
            default:
                return null;
        }
    }

    private boolean compile_end() {
        if (this.index == this.length - 1) {
            this.plain.append(this.pointerChar);
            return true;
        } else {
            return false;
        }
    }

    public String getString(int startIndex, int endIndex) {
        if (startIndex < 0) {
            throw new IndexOutOfBoundsException("String start index is not positive: " + startIndex + " (" + new String(this.chars) + ')');
        } else if (endIndex >= this.chars.length) {
            throw new IndexOutOfBoundsException("String end index is greater than the actual size: " + endIndex + " >= " + this.chars.length + " (" + new String(this.chars) + ')');
        } else if (endIndex <= startIndex) {
            throw new IndexOutOfBoundsException("String end index is less than the start index: " + endIndex + " <= " + startIndex + " (" + new String(this.chars) + ')');
        } else {
            return new String(this.chars, startIndex, endIndex - startIndex);
        }
    }

    static char[] copyChars(StringBuilder sb) {
        char[] chars = new char[sb.length()];
        sb.getChars(0, sb.length(), chars, 0);
        return chars;
    }

    /**
     * 增加一条问题
     *
     * @param index   问题在待完成字符串数组中的位置
     * @param problem 出现的问题
     * @param target  在哪出了问题
     */
    public void exception(int index, String problem, String target) {
        if (this.settings.validate) {
            String var4 = new String(this.chars);
            String message = problem + " at offset " + index;
            int var6 = 0;
            List<String> raws = Strings.split(var4, '\n', true);

            int var7;
            for (var7 = 0; var7 < raws.size(); ++var7) {
                String var8 = raws.get(var7);
                if (var6 + var8.length() >= index) {
                    if (raws.size() == 1) {
                        message = message + " in message:\n";
                    } else {
                        message = message + " in " + Strings.toOrdinalNumeral(var7 + 1) + " line of message:\n";
                    }

                    if (var7 != 0) {
                        message = message + "...\n";
                    }

                    message = message + '"' + var8 + '"';
                    break;
                }

                var6 += var8.length() + 1;
            }

            int var14 = 0;
            Collection<Integer> var9 = TextCompilerException.pointerToName(index - var6, target);
            var9.add(index - var6);

            for (Integer integer : var9) {
                if (integer > var14) {
                    var14 = integer;
                }
            }

            StringBuilder var13 = new StringBuilder(TextCompilerException.spaces(var14 + 2));
            var9.forEach((var1x) -> var13.setCharAt(var1x + 1, '^'));
            message = message + '\n' + var13;
            if (var7 + 1 != raws.size()) {
                message = message + "\n...";
            }

            this.exceptions.add(new TextCompilerException(target, problem, index, message));
        }
    }

    private void compile_clearPlain() {
        if (!this.plain.isEmpty()) {
            this.pieces.add(new TextPiece.Plain(this.plain.toString()));
            this.plain.setLength(0);
        }
    }

    /**
     * 检查 {@link TextCompiler} 是否以某字符串开头
     *
     * @param prefix 前缀
     */
    public boolean startsWith(String prefix) {
        if (this.length < prefix.length()) {
            return false;
        } else {
            for (int i = 0; i < prefix.length(); ++i) {
                if (this.chars[i] != prefix.charAt(i)) {
                    return false;
                }
            }

            return true;
        }
    }

    public void mergeExceptions(int i, TextCompiler other) {
        if (other.hasErrors()) {

            for (TextCompilerException exception : other.exceptions) {
                this.exception(i + exception.getIndex(), exception.getProblem(), exception.getTarget());
            }
        }
    }

    private enum HoverParserState {
        NORMAL_MESSAGE,     // 0
        HOVER_MESSAGE,      // 1
        ACTION;             // 2

        HoverParserState() {
        }
    }

    private enum SpecialType {
        HEX("hex"),                     // {#
        MACRO("macro"),                 // {$
        LANGUAGE_PATH("language path"), // {$$
        BACKREF("back reference"),      //
        CONDITIONAL("Conditional");     // {?

        private final String name;

        SpecialType(String var3) {
            this.name = var3;
        }
    }
}

