package top.auspice.bukkit.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import net.aurika.text.compiler.pieces.TextPiece;
import net.aurika.utils.checker.Checker;
import top.auspice.utils.chat.BaseColorUtils;
import top.auspice.utils.chat.CodedChatFormats;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BukkitColorUtils {

    public static int legacyColorToHex(@NotNull ChatColor bukkitChatColor) {
        Checker.Arg.notNull(bukkitChatColor, "bukkitChatColor");
        return switch (bukkitChatColor) {
            case WHITE -> 0xFF_FF_FF;
            case BLACK -> 0x00_00_00;
            case DARK_BLUE -> 0x00_00_AA;
            case DARK_GREEN -> 0x00_AA_00;
            case DARK_AQUA -> 0x00_AA_AA;
            case DARK_RED -> 0xAA_00_00;
            case DARK_PURPLE -> 0xAA_00_AA;
            case DARK_GRAY -> 0x55_55_55;
            case GRAY -> 0xAA_AA_AA;
            case GOLD -> 0xFF_AA_00;
            case BLUE -> 0x55_55_FF;
            case GREEN -> 0x55_FF_55;
            case AQUA -> 0x55_FF_FF;
            case RED -> 0xFF_55_55;
            case LIGHT_PURPLE -> 0xFF_55_FF;
            case YELLOW -> 0xFF_FF_55;
            default -> throw new IllegalArgumentException("Specified chat color is not a color: " + bukkitChatColor);
        };
    }

    public static ChatColor hexColorToLegacy(@NotNull Color color) {
        Checker.Arg.notNull(color, "color");
        switch (BaseColorUtils.getRGB(color)) {
            case 0x00_00_00 -> {
                return ChatColor.BLACK;
            }
            case 0xFF_FF_FF -> {
                return ChatColor.WHITE;
            }
            case 0x00_00_AA -> {
                return ChatColor.DARK_BLUE;
            }
            case 0x00_AA_00 -> {
                return ChatColor.DARK_GREEN;
            }
            case 0x00_AA_AA -> {
                return ChatColor.DARK_AQUA;
            }
            case 0xAA_00_00 -> {
                return ChatColor.DARK_RED;
            }
            case 0xAA_00_AA -> {
                return ChatColor.DARK_PURPLE;
            }
            case 0x55_55_55 -> {
                return ChatColor.DARK_GRAY;
            }
            case 0xAA_AA_AA -> {
                return ChatColor.GRAY;
            }
            case 0xFF_AA_00 -> {
                return ChatColor.GOLD;
            }
            case 0x55_55_FF -> {
                return ChatColor.BLUE;
            }
            case 0x55_FF_55 -> {
                return ChatColor.GREEN;
            }
            case 0x55_FF_FF -> {
                return ChatColor.AQUA;
            }
            case 0xFF_55_55 -> {
                return ChatColor.RED;
            }
            case 0xFF_55_FF -> {
                return ChatColor.LIGHT_PURPLE;
            }
            case 0xFF_FF_55 -> {
                return ChatColor.YELLOW;
            }
        }
        float[] hsb = BaseColorUtils.getHSB(color);
        float h = hsb[0];
        float s = hsb[1];
        float b = hsb[2];
        if (s < 40.0f) {
            if (b < 10.0f) {
                return ChatColor.BLACK;
            }
            if (b < 30.0f) {
                return ChatColor.DARK_GRAY;
            }
            if (b < 60.0f) {
                return ChatColor.GRAY;
            }
            return ChatColor.WHITE;
        }
        if (b < 20.0f) {
            return ChatColor.BLACK;
        }
        if (h > 340.0f || h >= 0.0f && h < 10.0f) {
            if (b <= 66.67f) {
                return ChatColor.DARK_RED;
            }
            return ChatColor.RED;
        }
        if (h >= 10.0f && h < 40.0f) {
            return ChatColor.GOLD;
        }
        if (h >= 40.0f && h < 70.0f) {
            return ChatColor.YELLOW;
        }
        if (h >= 70.0f && h < 150.0f) {
            if (b <= 66.67f) {
                return ChatColor.DARK_GREEN;
            }
            return ChatColor.GREEN;
        }
        if (h >= 150.0f && h < 190.0f) {
            if (b <= 66.67f) {
                return ChatColor.DARK_AQUA;
            }
            return ChatColor.AQUA;
        }
        if (h >= 190.0f && h < 250.0f) {
            if (b <= 66.67f) {
                return ChatColor.DARK_BLUE;
            }
            return ChatColor.BLUE;
        }
        if (h >= 250.0f && h < 280.0f) {
            return ChatColor.DARK_PURPLE;
        }
        if (h >= 280.0f && h < 340.0f) {
            return ChatColor.LIGHT_PURPLE;
        }
        throw new AssertionError("Undetectable legacy hex chat with properties " + h + " | " + s + " | " + b);
    }

    public static Color legacyColorToAwt(ChatColor bukkitChatFormat) {
        return new Color(legacyColorToHex(bukkitChatFormat));
    }

    public static List<TextPiece> gradient(List<TextPiece> pieces, Color start, Color end) {
        int length = 0;

        for (TextPiece piece : pieces) {
            if (piece instanceof TextPiece.Plain) {
                length += piece.length();
            } else if (!(piece instanceof TextPiece.Color)) {
                throw new IllegalArgumentException("Disallowed piece in gradient chat: " + piece);
            }
        }

        double var18 = Math.abs((double) (start.getRed() - end.getRed()) / (double) length);
        double var6 = Math.abs((double) (start.getGreen() - end.getGreen()) / (double) length);
        double var8 = Math.abs((double) (start.getBlue() - end.getBlue()) / (double) length);
        if (start.getRed() > end.getRed()) {
            var18 = -var18;
        }

        if (start.getGreen() > end.getGreen()) {
            var6 = -var6;
        }

        if (start.getBlue() > end.getBlue()) {
            var8 = -var8;
        }

        ArrayList<TextPiece> var16 = new ArrayList<>(length * 3);
        Color var1 = new Color(start.getRGB());
        boolean var17 = false;
        String var10 = null;
        int var11 = 0;

        for (TextPiece piece : pieces) {
            TextPiece var12;
            int var19;
            if ((var12 = piece) instanceof TextPiece.Plain) {
                if (var10 == null) {
                    var10 = ((TextPiece.Plain) var12).getMessage();
                }

                if (var17) {
                    var17 = false;
                    continue;
                }

                var19 = var10.charAt(var11++);
                var16.add(new TextPiece.HexTextColor(var1));
                var16.add(new TextPiece.Plain(String.valueOf((char) var19)));
            } else if (var12 instanceof TextPiece.Color) {
                if (var12 instanceof TextPiece.SimpleFormat) {
                    var17 = ((TextPiece.SimpleFormat) var12).getColor().isDecoration();
                }

                var16.add(var12);
            }

            var19 = (int) Math.round((double) var1.getRed() + var18);
            int var13 = (int) Math.round((double) var1.getGreen() + var6);
            int var15 = (int) Math.round((double) var1.getBlue() + var8);
            if (var19 > 255) {
                var19 = 255;
            }

            if (var19 < 0) {
                var19 = 0;
            }

            if (var13 > 255) {
                var13 = 255;
            }

            if (var13 < 0) {
                var13 = 0;
            }

            if (var15 > 255) {
                var15 = 255;
            }

            if (var15 < 0) {
                var15 = 0;
            }

            var1 = new Color(var19, var13, var15);
        }

        return var16;
    }

    public ChatColor convertToBukkit(CodedChatFormats stdChatColor) {
        Objects.requireNonNull(stdChatColor, "The provided argument is not a chat chat.");
        return switch (stdChatColor) {
            case BLACK -> ChatColor.BLACK;
            case DARK_BLUE -> ChatColor.DARK_BLUE;
            case DARK_GREEN -> ChatColor.DARK_GREEN;
            case DARK_AQUA -> ChatColor.DARK_AQUA;
            case DARK_RED -> ChatColor.DARK_RED;
            case DARK_PURPLE -> ChatColor.DARK_PURPLE;
            case GOLD -> ChatColor.GOLD;
            case GRAY -> ChatColor.GRAY;
            case DARK_GRAY -> ChatColor.DARK_GRAY;
            case BLUE -> ChatColor.BLUE;
            case GREEN -> ChatColor.GREEN;
            case AQUA -> ChatColor.AQUA;
            case RED -> ChatColor.RED;
            case LIGHT_PURPLE -> ChatColor.LIGHT_PURPLE;
            case YELLOW -> ChatColor.YELLOW;
            case WHITE -> ChatColor.WHITE;
            case OBFUSCATED -> ChatColor.MAGIC;
            case BOLD -> ChatColor.BOLD;
            case STRIKETHROUGH -> ChatColor.STRIKETHROUGH;
            case UNDERLINED -> ChatColor.UNDERLINE;
            case ITALIC -> ChatColor.ITALIC;
            case RESET -> ChatColor.RESET;
        };
    }
}
