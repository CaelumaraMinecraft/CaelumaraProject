package net.aurika.auspice.utils.chat;

import net.aurika.util.string.Strings;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class BaseColorUtils {

    private static final byte[] CHAR_CODE_MAP;

    public static Color hex(String hexStr) {
        try {
            int var1;
            boolean var2 = (var1 = (hexStr = Strings.deleteWhitespace(Strings.remove(hexStr, '#'))).length()) == 4 || var1 == 8;
            if (var1 != 3 && var1 != 4) {
                if (var1 != 6 && var1 != 8) {
                    return null;
                }
            } else {
                hexStr = shortHexToLongHex(hexStr);
            }

            if (var1 == 8) {
                hexStr = hexStr.substring(6, 8) + hexStr.substring(0, 6);
            }

            var1 = 0;
            int var3 = hexStr.length();
            int var4 = 0;

            Integer var10000;
            while (true) {
                if (var1 >= var3) {
                    var10000 = -var4;
                    break;
                }

                var4 <<= 4;
                char var5;
                if ((var5 = hexStr.charAt(var1++)) >= CHAR_CODE_MAP.length) {
                    var10000 = null;
                    break;
                }

                byte var8;
                if ((var8 = CHAR_CODE_MAP[var5]) == -1) {
                    var10000 = null;
                    break;
                }

                var4 -= var8;
            }

            Integer var7 = var10000;
            return var10000 == null ? null : new Color(var7, var2);
        } catch (NumberFormatException var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static String toString(Color var0) {
        return "{ R:" + var0.getRed() + " | G:" + var0.getGreen() + " | B:" + var0.getBlue() + " | A:" + var0.getAlpha() + " }";
    }

    public static int getRGB(Color var0) {
        return (var0.getRed() & 255) << 16 | (var0.getGreen() & 255) << 8 | var0.getBlue() & 255;
    }

    public static Color parseColor(String var0) {
        Color color = hex(var0);
        return color == null ? rgb(var0) : color;
    }

    public static String shortHexToLongHex(String var0) {
        char var1 = var0.charAt(0);
        char var2 = var0.charAt(1);
        char var3 = var0.charAt(2);
        char var4 = var0.length() == 4 ? var0.charAt(3) : 70;
        return String.valueOf(var4) + var4 + var1 + var1 + var2 + var2 + var3 + var3;
    }

    public static int toHex(Color var0) {
        return var0.getRed() << 16 | var0.getGreen() << 8 | var0.getBlue();
    }

    public static int getHue(@Nonnull Color var0) {
        int var1 = var0.getRed();
        int var2 = var0.getGreen();
        int var5 = var0.getBlue();
        float var3 = (float) Math.min(Math.min(var1, var2), var5);
        float var4 = (float) Math.max(Math.max(var1, var2), var5);
        if (var3 == var4) {
            return 0;
        } else {
            var3 = var4 - var3;
            float var6;

            if (var4 == (float) var1) {
                var6 = (float) (var2 - var5) / var3;
            } else if (var4 == (float) var2) {
                var6 = 2.0F + (float) (var5 - var1) / var3;
            } else {
                var6 = 4.0F + (float) (var1 - var2) / var3;
            }

            if ((var6 *= 60.0F) < 0.0F) {
                var6 += 360.0F;
            }

            return Math.round(var6);
        }
    }

    public static float[] getHSB(Color color) {
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        hsb[0] = hsb[0] * 360.0F;
        hsb[1] *= 100.0F;
        hsb[2] *= 100.0F;
        return hsb;
    }

    public static String toHexString(Color var0) {
        Objects.requireNonNull(var0, "Cannot convert null chat to hex");
        return toHexString(var0.getRGB());
    }

    public static String toHexString(int var0) {
        return String.format("%06x", var0 & 0xFF_FF_FF);
    }

    public static Color rgb(String str) {
        List<String> compiled = Strings.cleanSplitManaged(str, str.contains(",") ? ',' : ' ');
        if (compiled.size() < 3) {
            return null;
        } else {
            try {
                int r = Integer.parseInt(compiled.get(0));
                int g = Integer.parseInt(compiled.get(1));
                int b = Integer.parseInt(compiled.get(2));
                int a = compiled.size() > 3 ? Integer.parseInt(compiled.get(3)) : 255;
                return new Color(r, g, b, a);
            } catch (NumberFormatException var4) {
                return null;
            }
        }
    }

    public static TextFormatCodes hexColorToStd(Color color) {
        switch (BaseColorUtils.getRGB(color)) {
            case 0x00_00_00 -> {
                return TextFormatCodes.BLACK;
            }
            case 0xFF_FF_FF -> {
                return TextFormatCodes.WHITE;
            }
            case 0x00_00_AA -> {
                return TextFormatCodes.DARK_BLUE;
            }
            case 0x00_AA_00 -> {
                return TextFormatCodes.DARK_GREEN;
            }
            case 0x00_AA_AA -> {
                return TextFormatCodes.DARK_AQUA;
            }
            case 0xAA_00_00 -> {
                return TextFormatCodes.DARK_RED;
            }
            case 0xAA_00_AA -> {
                return TextFormatCodes.DARK_PURPLE;
            }
            case 0x55_55_55 -> {
                return TextFormatCodes.DARK_GRAY;
            }
            case 0xAA_AA_AA -> {
                return TextFormatCodes.GRAY;
            }
            case 0xFF_AA_00 -> {
                return TextFormatCodes.GOLD;
            }
            case 0x55_55_FF -> {
                return TextFormatCodes.BLUE;
            }
            case 0x55_FF_55 -> {
                return TextFormatCodes.GREEN;
            }
            case 0x55_FF_FF -> {
                return TextFormatCodes.AQUA;
            }
            case 0xFF_55_55 -> {
                return TextFormatCodes.RED;
            }
            case 0xFF_55_FF -> {
                return TextFormatCodes.LIGHT_PURPLE;
            }
            case 0xFF_FF_55 -> {
                return TextFormatCodes.YELLOW;
            }
        }
        float[] hsb = getHSB(color);
        float h = hsb[0];
        float s = hsb[1];
        float b = hsb[2];
        if (s < 40.0f) {
            if (b < 10.0f) {
                return TextFormatCodes.BLACK;
            }
            if (b < 30.0f) {
                return TextFormatCodes.DARK_GRAY;
            }
            if (b < 60.0f) {
                return TextFormatCodes.GRAY;
            }
            return TextFormatCodes.WHITE;
        }
        if (b < 20.0f) {
            return TextFormatCodes.BLACK;
        }
        if (h > 340.0f || h >= 0.0f && h < 10.0f) {
            if (b <= 66.67f) {
                return TextFormatCodes.DARK_RED;
            }
            return TextFormatCodes.RED;
        }
        if (h >= 10.0f && h < 40.0f) {
            return TextFormatCodes.GOLD;
        }
        if (h >= 40.0f && h < 70.0f) {
            return TextFormatCodes.YELLOW;
        }
        if (h >= 70.0f && h < 150.0f) {
            if (b <= 66.67f) {
                return TextFormatCodes.DARK_GREEN;
            }
            return TextFormatCodes.GREEN;
        }
        if (h >= 150.0f && h < 190.0f) {
            if (b <= 66.67f) {
                return TextFormatCodes.DARK_AQUA;
            }
            return TextFormatCodes.AQUA;
        }
        if (h >= 190.0f && h < 250.0f) {
            if (b <= 66.67f) {
                return TextFormatCodes.DARK_BLUE;
            }
            return TextFormatCodes.BLUE;
        }
        if (h >= 250.0f && h < 280.0f) {
            return TextFormatCodes.DARK_PURPLE;
        }
        if (h >= 280.0f && h < 340.0f) {
            return TextFormatCodes.LIGHT_PURPLE;
        }
        throw new AssertionError("Undetectable legacy hex chat with properties " + h + " | " + s + " | " + b);
    }

    public static double distance(Color first, Color second) {
        int var2 = first.getRed();
        int var3 = second.getRed();
        int var4 = var2 + var3 >> 1;
        var2 -= var3;
        var3 = first.getGreen() - second.getGreen();
        int var5 = first.getBlue() - second.getBlue();
        return Math.sqrt(((var4 + 512) * var2 * var2 >> 8) + 4 * var3 * var3 + ((767 - var4) * var5 * var5 >> 8));
    }

    public static Color mixColors(Color... colors) {
        float var1 = 1.0F / (float) colors.length;
        int var2 = 0;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = (colors).length;

        for (Color var8 : colors) {
            var2 = (int) ((float) var2 + (float) var8.getRed() * var1);
            var3 = (int) ((float) var3 + (float) var8.getGreen() * var1);
            var4 = (int) ((float) var4 + (float) var8.getBlue() * var1);
            var5 = (int) ((float) var5 + (float) var8.getAlpha() * var1);
        }

        return new Color(var2, var3, var4, var5);
    }

    static {
        CHAR_CODE_MAP = new byte[103];

        Arrays.fill(CHAR_CODE_MAP, (byte) -1);

        int ch;
        for (ch = '0'; ch <= '9'; ++ch) {
            CHAR_CODE_MAP[ch] = (byte) (ch - 48);      // intCode 0 ~ 9
        }

        for (ch = 'A'; ch <= 'F'; ++ch) {
            CHAR_CODE_MAP[ch] = (byte) (ch - 65 + 10); // intCode 10 ~ 15
        }

        for (ch = 'a'; ch <= 'f'; ++ch) {
            CHAR_CODE_MAP[ch] = (byte) (ch - 97 + 10); // intCode 10 ~ 15
        }
    }
}
