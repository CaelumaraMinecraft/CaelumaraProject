package net.aurika.auspice.utils.chat;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextFormat;
import net.kyori.adventure.text.serializer.legacy.Reset;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * A util for {@linkplain TextFormat}.
 */
public enum TextFormatCodes {
    BLACK('0') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.BLACK;
        }
    },
    DARK_BLUE('1') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.DARK_BLUE;
        }
    },
    DARK_GREEN('2') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.DARK_BLUE;
        }
    },
    DARK_AQUA('3') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.DARK_AQUA;
        }
    },
    DARK_RED('4') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.DARK_RED;
        }
    },
    DARK_PURPLE('5') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.DARK_PURPLE;
        }
    },
    GOLD('6') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.GOLD;
        }
    },
    GRAY('7') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.GRAY;
        }
    },
    DARK_GRAY('8') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.DARK_GRAY;
        }
    },
    BLUE('9') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.BLUE;
        }
    },
    GREEN('a') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.GREEN;
        }
    },
    AQUA('b') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.AQUA;
        }
    },
    RED('c') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.RED;
        }
    },
    LIGHT_PURPLE('d') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.LIGHT_PURPLE;
        }
    },
    YELLOW('e') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.YELLOW;
        }
    },
    WHITE('f') {
        @Override
        public @NotNull NamedTextColor value() {
            return NamedTextColor.WHITE;
        }
    },
    OBFUSCATED('k') {
        @Override
        public @NotNull TextDecoration value() {
            return TextDecoration.OBFUSCATED;
        }
    },
    BOLD('l') {
        @Override
        public @NotNull TextDecoration value() {
            return TextDecoration.BOLD;
        }
    },
    STRIKETHROUGH('m') {
        @Override
        public @NotNull TextDecoration value() {
            return TextDecoration.STRIKETHROUGH;
        }
    },
    UNDERLINED('n') {
        @Override
        public @NotNull TextDecoration value() {
            return TextDecoration.UNDERLINED;
        }
    },
    ITALIC('o') {
        @Override
        public @NotNull TextDecoration value() {
            return TextDecoration.ITALIC;
        }
    },
    RESET('r') {
        @Override
        public @NotNull Reset value() {
            return Reset.INSTANCE;
        }
    };

    private static final Map<Character, TextFormatCodes> BY_CHAR = new HashMap<>();

    private final char code;

    public char code() {
        return code;
    }

    public abstract @NotNull TextFormat value();

    public boolean isColor() {
        return this.value() instanceof NamedTextColor;
    }

    public int getRGBValue() {
        if (!(this.value() instanceof NamedTextColor)) {
            throw new IllegalArgumentException("The value is not a NamedTextColor");
        }
        return ((NamedTextColor) this.value()).value();
    }

    public boolean isDecoration() {
        return this.value() instanceof TextDecoration;
    }

    public boolean isReset() {
        return this.value() instanceof Reset;
    }

    TextFormatCodes(char code) {
        this.code = code;
    }

    public @NotNull String toString() {
        return "§" + this.code;
    }

    public static @Nullable TextFormatCodes getByChar(char code) {
        return BY_CHAR.get(code);
    }

    static {
        for (TextFormatCodes f : values()) {
            BY_CHAR.put(f.code, f);
        }
    }

    //

    public static boolean isTextFormatCode(char ch) {
        return isColorCode(ch) || isDecorationCode(ch) || isResetCode(ch);
    }

    public static boolean isColorCode(char ch) {
        return ((ch >= '0') && (ch <= '9')) || ((ch >= 'A') && (ch <= 'F')) || ((ch >= 'a') && (ch <= 'f'));
    }

    public static boolean isDecorationCode(char ch) {
        return ((ch >= 'K') && (ch <= 'O')) || ((ch >= 'k') && (ch <= 'o'));
    }

    public static boolean isResetCode(char ch) {
        return (ch == 'R') || (ch == 'r');
    }
}
