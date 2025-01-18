package top.auspice.utils.chat;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextFormat;
import net.kyori.adventure.text.serializer.legacy.Reset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum CodedChatFormats {
    BLACK(NamedTextColor.BLACK, '0'),
    DARK_BLUE(NamedTextColor.DARK_BLUE, '1'),
    DARK_GREEN(NamedTextColor.DARK_GREEN, '2'),
    DARK_AQUA(NamedTextColor.DARK_AQUA, '3'),
    DARK_RED(NamedTextColor.DARK_RED, '4'),
    DARK_PURPLE(NamedTextColor.DARK_PURPLE, '5'),
    GOLD(NamedTextColor.GOLD, '6'),
    GRAY(NamedTextColor.GRAY, '7'),
    DARK_GRAY(NamedTextColor.DARK_GRAY, '8'),
    BLUE(NamedTextColor.BLUE, '9'),
    GREEN(NamedTextColor.GREEN, 'a'),
    AQUA(NamedTextColor.AQUA, 'b'),
    RED(NamedTextColor.RED, 'c'),
    LIGHT_PURPLE(NamedTextColor.LIGHT_PURPLE, 'd'),
    YELLOW(NamedTextColor.YELLOW, 'e'),
    WHITE(NamedTextColor.WHITE, 'f'),
    OBFUSCATED(TextDecoration.OBFUSCATED, 'k'),
    BOLD(TextDecoration.BOLD, 'l'),
    STRIKETHROUGH(TextDecoration.STRIKETHROUGH, 'm'),
    UNDERLINED(TextDecoration.UNDERLINED, 'n'),
    ITALIC(TextDecoration.ITALIC, 'o'),
    RESET(Reset.INSTANCE, 'r');

    private static final Map<Character, CodedChatFormats> BY_CHAR = new HashMap<>();

    private final TextFormat value;

    private final char code;

    public @NotNull TextFormat getValue() {
        return value;
    }

    public char getCode() {
        return code;
    }

    public boolean isColor() {
        return this.value instanceof NamedTextColor;
    }

    public int getRGBValue() {
        if (!(this.value instanceof NamedTextColor)) {
            throw new IllegalArgumentException("The value is not a NamedTextColor");
        }
        return ((NamedTextColor) this.value).value();
    }

    public boolean isDecoration() {
        return this.value instanceof TextDecoration;
    }

    public boolean isReset() {
        return this.value instanceof Reset;
    }

    CodedChatFormats(TextFormat value, char code) {
        this.value = value;
        this.code = code;
    }

    public @NotNull String toString() {
        return "§" + this.code;
    }

    public static @Nullable CodedChatFormats getByChar(char code) {
        return BY_CHAR.get(code);
    }

    static {
        for (CodedChatFormats f : values()) {
            BY_CHAR.put(f.code, f);
        }
    }
}
