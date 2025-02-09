package top.auspice.configs.messages;

import net.aurika.text.TextObject;
import net.aurika.utils.string.Strings;
import org.jetbrains.annotations.Contract;
import top.auspice.configs.globalconfig.AuspiceGlobalConfig;
import net.aurika.text.placeholders.StandardKingdomsPlaceholder;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;
import top.auspice.main.Auspice;
import top.auspice.permission.DefaultAuspicePluginPermissions;
import top.auspice.server.command.CommandSender;
import top.auspice.server.core.Server;
import top.auspice.server.entity.Player;
import top.auspice.utils.math.MathUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class MessageHandler {
    public static final boolean SIXTEEN;
    public static final char HEX_CHAR = 'x';
    public static final char HEX_CODE = '#';
    public static final char COLOR_CHAR = '§';
    public static final char COLOR_CODE = '&';
    protected static final char[] HEXADECIMAL = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String translateComplexColor(String var01) {
        String var0 = Strings.deleteWhitespace(var01);
        if (var0.charAt(0) == '&') {
            return colorize(var0);
        } else {
            int r;
            int g;
            int b;
            if (var0.indexOf(44) > 1) {
                List<String> RGB = Strings.split(var0, ',', true);

                Integer R = MathUtils.parseInt(RGB.get(0), false);
                if (R == null) {
                    throw new IllegalArgumentException("Invalid String value of RGB: " + RGB.get(0));
                } else {
                    r = R;
                }
                Integer G = MathUtils.parseInt(RGB.get(1), false);
                if (G == null) {
                    throw new IllegalArgumentException("Invalid String value of RGB: " + RGB.get(1));
                } else {
                    g = G;
                }
                Integer B = MathUtils.parseInt(RGB.get(2), false);
                if (B == null) {
                    throw new IllegalArgumentException("Invalid String value of RGB: " + RGB.get(2));
                } else {
                    b = B;
                }

                if (r > 255) {
                    throw new IllegalArgumentException("Invalid R value of RGB: " + r);
                }
                if (g > 255) {
                    throw new IllegalArgumentException("Invalid G value of RGB: " + g);
                }
                if (b > 255) {
                    throw new IllegalArgumentException("Invalid B value of RGB: " + b);
                }

                return rgbToHexString(r, g, b);

            } else {
                int length = var0.length();
                if (length == 3) {
                    a(var0);
                    r = var0.charAt(0);
                    g = var0.charAt(1);
                    b = var0.charAt(2);
                    return "§" + 'x' + "§" + r + '§' + r + "§" + g + '§' + g + "§" + b + '§' + b;
                } else if (length == 6) {
                    a(var0);
                    return "§" + 'x' + "§" + var0.charAt(0) + '§' + var0.charAt(1) + "§" + var0.charAt(2) + '§' + var0.charAt(3) + "§" + var0.charAt(4) + '§' + var0.charAt(5);
                } else {
                    throw new IllegalArgumentException("Unknown complex chat format/name: " + var0);
                }
            }
        }
    }

    public static String hex(String var0) {
        return "§" + 'x' + "§" + var0.charAt(0) + '§' + var0.charAt(1) + "§" + var0.charAt(2) + '§' + var0.charAt(3) + "§" + var0.charAt(4) + '§' + var0.charAt(5);
    }

    private static void a(String var0) {
        int var1 = var0.length();

        for (int var2 = 0; var2 < var1; ++var2) {
            if (!isHexOrDigit(var0.charAt(var2))) {
                throw new IllegalArgumentException("Invalid character for hex chat " + var0 + ": " + var0.charAt(var2));
            }
        }

    }

    public static String replaceVariables(String var0, Object... var1) {
        if (var1 != null && var1.length != 0 && !com.google.common.base.Strings.isNullOrEmpty(var0)) {
            if (var1[0] instanceof Collection) {
                throw new IllegalArgumentException("First element of edits is a collection (bad method use)");
            } else if ((var1.length & 1) != 0) {
                throw new IllegalArgumentException("No replacement is specified for the last variable: \"" + var1[var1.length - 1] + "\" in \"" + var0 + '"' + " with edits: \"" + Arrays.toString(var1) + '"');
            } else {
                int var2 = var1.length - 1;

                for (int var3 = 0; var3 < var2; ++var3) {
                    String var4 = String.valueOf(var1[var3]);
                    ++var3;
                    Object var5 = var1[var3];
                    var0 = replace(var0, var4, var5);
                }

                return var0;
            }
        } else {
            return var0;
        }
    }

    public static String replaceVariables(String var0, List<Object> var1) {
        if (var1 != null && !var1.isEmpty() && !Strings.isNullOrEmpty(var0)) {
            if (var1.get(0) instanceof Collection) {
                throw new IllegalArgumentException("First element of edits is a collection (bad method use)");
            } else if ((var1.size() & 1) != 0) {
                throw new IllegalArgumentException("No replacement is specified for the last variable: \"" + var1.get(var1.size() - 1) + "\" in \"" + var0 + '"' + " with edits: \"" + Arrays.toString(var1.toArray()) + '"');
            } else {
                String var2 = null;

                for (Object var3 : var1) {
                    if (var2 == null) {
                        var2 = String.valueOf(var3);
                    } else {
                        var0 = replace(var0, var2, var3);
                        var2 = null;
                    }
                }

                return var0;
            }
        } else {
            return var0;
        }
    }

    public static String replace(String var0, String var1, Object var2) {
        int var3;
        if ((var3 = var0.indexOf(var1)) == -1) {
            return var0;
        } else {
            int var4 = 0;
            int var5 = var0.length();
            int var6 = var1.length();
            StringBuilder var7 = new StringBuilder(var5);
            String var9;
            if (var2 instanceof Supplier) {
                var9 = String.valueOf(((Supplier<?>) var2).get());
            } else {
                var9 = String.valueOf(var2);
            }

            while (var3 != -1) {
                var7.append(var0, var4, var3).append(var9);
                var4 = var3 + var6;
                var3 = var0.indexOf(var1, var4);
            }

            var7.append(var0, var4, var5);
            return var7.toString();
        }
    }

    public static void sendMessage(CommandSender resolver, String string, boolean usePrefix) {
        Objects.requireNonNull(resolver, "Cannot send message to null receiver");
        if (!Strings.isNullOrEmpty(string)) {
            MessageProcessor.compile(string).getSimpleProvider().send(resolver, (new MessagePlaceholderProvider()).usePrefix(usePrefix));
        }
    }

    @Contract("null -> null; !null -> !null")
    public static String colorize(String str) {
        if (str != null && !str.isEmpty()) {
            if (Auspice.QUANTUM_STATE) {
                return colorize$(str);
            } else {
                int maxInd = str.length() - 1;
                int index;
                if (!SIXTEEN) {
                    char[] var9 = str.toCharArray();

                    for (index = 0; index < maxInd; ++index) {
                        if (var9[index] == '&' && isColorCode(var9[index + 1])) {
                            var9[index++] = '§';
                        }
                    }

                    return new String(var9);
                } else {
                    StringBuilder builder = new StringBuilder(maxInd + 50);
                    index = -1;
                    boolean var4 = false;

                    for (int var5 = 0; var5 < maxInd; ++var5) {
                        int var6 = str.charAt(var5);
                        if (var6 == '\\') {
                            var4 = true;
                        } else if (var6 != '{') {
                            if (var4) {
                                var4 = false;
                                builder.append('\\');
                            } else if (index >= 0) {
                                if (isHexOrDigit((char) var6)) {
                                    builder.append('§').append((char) var6);
                                    if (index++ == 6) {
                                        index = -1;
                                    }
                                } else {
                                    index = -1;
                                    builder.append((char) var6);
                                }
                            } else if (var6 == '&') {
                                char var11 = str.charAt(var5 + 1);
                                boolean var13 = var11 == '#';
                                if (!var13 && !isColorCode(var11)) {
                                    builder.append('&');
                                } else {
                                    builder.append('§');
                                    if (var13) {
                                        builder.append('x');
                                        index = 1;
                                        ++var5;
                                    }
                                }
                            } else {
                                builder.append((char) var6);
                            }
                        } else {
                            if (var5 > 0) {
                                var6 = str.charAt(var5 - 1);
                                if (var4 || var6 == ':') {
                                    if (var4) {
                                        builder.append('\\');
                                        var4 = false;
                                    }

                                    builder.append('{');
                                    continue;
                                }
                            }

                            if ((var6 = str.indexOf('}', var5 + 1)) == -1) {
                                throw new IllegalStateException("Found unclosed replacement field at: " + var5 + ", " + str);
                            }

                            String var7;
                            if ((var7 = Strings.deleteWhitespace(str.substring(var5 + 1, var6))).length() >= 2) {
                                if (var7.charAt(0) == '#') {
                                    String var10;
                                    TextObject var12;
                                    if ((var12 = StandardKingdomsPlaceholder.getMacro(Strings.toLatinLowerCase(var10 = var7.substring(1)), new MessagePlaceholderProvider())) != null) {
                                        builder.append(var12.buildPlain(new MessagePlaceholderProvider()));
                                    } else {
                                        builder.append(translateComplexColor(var10));
                                    }

                                    var5 = var6;
                                } else {
                                    builder.append('{');
                                }
                            } else {
                                builder.append('{');
                            }
                        }
                    }

                    if (index == 6) {
                        builder.append('§');
                    }

                    builder.append(str.charAt(maxInd));
                    return builder.toString();
                }
            }
        } else {
            return str;
        }
    }


    /**
     * 将 '& ' 转换为 '§'
     */
    public static String colorize$(String str) {
        char[] var7 = str.toCharArray();
        int maxInd = var7.length - 1;
        StringBuilder builder = new StringBuilder(maxInd);
        int var3 = -1;

        for (int var4 = 0; var4 < maxInd; ++var4) {
            char var5 = var7[var4];
            char var6 = var7[var4 + 1];
            if (var3 >= 0) {
                if (!Character.isWhitespace(var5)) {
                    if (var3++ == 6) {
                        var3 = -1;
                    }

                    builder.append('§').append(var5);
                } else {
                    var3 = -1;
                }
            }

            if (var5 == '&' && !Character.isWhitespace(var6)) {
                builder.append('§');
                var3 = 0;
            } else {
                builder.append(var5);
            }
        }

        return builder.toString();
    }


    public static boolean isColorCode(char var0) {
        return isHexOrDigit(var0) || isFormattingCode(var0);
    }

    protected static boolean isFormattingCode(char var0) {
        return var0 >= 'K' && var0 <= 'O' || var0 >= 'k' && var0 <= 'o' || var0 == 'R' || var0 == 'r';
    }

    protected static boolean isHexOrDigit(char var0) {
        return var0 >= '0' && var0 <= '9' || var0 >= 'A' && var0 <= 'F' || var0 >= 'a' && var0 <= 'f';
    }

    public static String stripColors(String var0, boolean var1) {
        if (var0 == null) {
            return null;
        } else {
            int var2;
            if ((var2 = var0.length()) < 2) {
                return var0;
            } else {
                int var4;
                int var5;
                char var6;
                if (SIXTEEN) {
                    StringBuilder var8 = new StringBuilder(var2);
                    var4 = -1;

                    for (var5 = 0; var5 < var2 - 1; ++var5) {
                        if ((var6 = var0.charAt(var5)) == 167) {
                            var6 = var0.charAt(var5 + 1);
                            if (var4 != -1) {
                                if (isHexOrDigit(var6)) {
                                    if (var1) {
                                        var8.append(var6);
                                    }

                                    if (var4++ == 6) {
                                        var4 = -1;
                                    }

                                    ++var5;
                                } else {
                                    var4 = -1;
                                }
                            } else {
                                boolean var7;
                                if ((var7 = var6 == 'x') || isColorCode(var6)) {
                                    if (var1) {
                                        var8.append('&');
                                    }

                                    if (var7) {
                                        if (var1) {
                                            var8.append('#');
                                        }

                                        var4 = 0;
                                    } else if (var1) {
                                        var8.append(var6);
                                    }

                                    ++var5;
                                }
                            }
                        } else {
                            var4 = -1;
                            var8.append(var6);
                        }
                    }

                    var8.append(var0.charAt(var2 - 1));
                    return var8.toString();
                } else {
                    char[] var3;
                    if (var1) {
                        var3 = var0.toCharArray();

                        for (var4 = 0; var4 < var2 - 1; ++var4) {
                            if (var3[var4] == 167 && isColorCode(var3[var4 + 1])) {
                                var3[var4++] = '&';
                            }
                        }

                        return new String(var3);
                    } else {
                        var3 = new char[var2];
                        var4 = 0;

                        for (var5 = 0; var5 < var2 - 1; ++var5) {
                            if ((var6 = var0.charAt(var5)) == 167 && isColorCode(var0.charAt(var5 + 1))) {
                                ++var5;
                            } else {
                                var3[var4++] = var6;
                            }
                        }

                        if (var5 != var2) {
                            var3[var4] = var0.charAt(var2 - 1);
                        }

                        return new String(var3, 0, var4 + 1);
                    }
                }
            }
        }
    }

    public static String removePattern(String var0, Pattern var1) {
        Matcher var5;
        if (!(var5 = var1.matcher(var0)).find()) {
            return var0;
        } else {
            StringBuilder var3 = new StringBuilder(var0.length());
            int var4 = 0;

            do {
                var3.append(var0, var4, var5.start());
                var4 = var5.end();
            } while (var5.find());

            var3.append(var0, var4, var0.length());
            return var3.toString();
        }
    }

    public static String parseRGB(String var0) {
        return Strings.split(var0, ',', false).size() != 3 ? null : var0;
    }

    public static String rgbToHexString(int r, int g, int b) {
        return "§" + 'x' + "§" + HEXADECIMAL[r / 16 % 16] + "§" + HEXADECIMAL[r % 16] + "§" + HEXADECIMAL[g / 16 % 16] + "§" + HEXADECIMAL[g % 16] + "§" + HEXADECIMAL[b / 16 % 16] + "§" + HEXADECIMAL[b % 16];
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            sendPlayerMessage((Player) sender, message);
        } else {
            sendConsoleMessage(message);
        }
    }

    public static void sendPluginMessage(CommandSender sender, String message) {
        if (sender instanceof Player) {
            sendPlayerPluginMessage((Player) sender, message);
        } else {
            sendConsolePluginMessage(message);
        }
    }

    public static void sendPlayerMessage(Player player, String message) {
        sendMessage(player, message, false);
    }

    public static void sendPlayerPluginMessage(Player player, String message) {
        sendMessage(player, message, true);
    }

    public static void sendConsoleMessage(String var0) {
        sendMessage(Server.get().getConsoleSender(), var0, false);
    }

    public static void sendConsolePluginMessage(String var0) {
        sendMessage(Bukkit.getConsoleSender(), var0, true);
    }

    public static void sendPlayersMessage(String message) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMessage(player, message, false);
        }

    }

    public static void sendPlayersPluginMessage(String var0) {

        for (Player player : Bukkit.getOnlinePlayers()) {
            sendMessage(player, var0, true);
        }

    }

    public static void debug(String var0) {
        if (AuspiceGlobalConfig.DEBUG.getBoolean()) {
            var0 = "&8[&5DEBUG&8] &4" + var0;
            sendMessage(Bukkit.getConsoleSender(), var0, true);

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (DefaultAuspicePluginPermissions.DEBUG.hasPermission(player)) {
                    sendMessage(player, var0, true);
                }
            }

        }
    }

    public static Supplier<?> supply(Supplier<?> var0) {
        return var0;
    }

    static {
        boolean var0;
        try {
            Class.forName("org.bukkit.entity.Zoglin");
            var0 = true;
        } catch (ClassNotFoundException var1) {
            var0 = false;
        }

        SIXTEEN = var0;
    }
}
