package net.aurika.utils.string;

import net.aurika.checker.Checker;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Strings {

    public static char[] toCharArray(Character[] characters) {
        char[] chars = new char[characters.length];
        for (int i = 0; i < characters.length; ++i) {
            chars[i] = characters[i];
        }
        return chars;
    }

    public static char[] toCharArray(Collection<Character> characters) {
        return toCharArray(characters.toArray(new Character[0]));
    }

    public static boolean isNullOrEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    public static String notEmpty(@Nullable String string) {
        if (isNullOrEmpty(string)) {
            throw new IllegalArgumentException("String is null or empty");
        }
        return string;
    }

    public static boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch) || Character.isSpaceChar(ch);
    }

    public static int countSepAmount(String string, char sep) {
        char[] chars = string.toCharArray();
        int amount = 0;
        for (char c : chars) {
            if (c == sep) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * <blockquote><pre>
     * this is a string
     * ___^__^___^_____
     * </pre></blockquote>
     */
    public static int[] countSepIndexes(String string, char sep) {
        char[] chars = string.toCharArray();
        int[] indexes = new int[countSepAmount(string, sep)];
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c == sep) {
                indexes[count] = i;
                count++;
            }
        }
        return indexes;
    }

    public static final int INDEX_NOT_FOUND = -1;
    private static final DecimalFormat a;
    private static final Pattern b;

    public Strings() {
    }

    public static String random(int var0, int var1, String var2) {
        ThreadLocalRandom var3;
        char[] var4;
        for (var4 = new char[(var0 = (var3 = ThreadLocalRandom.current()).nextInt(var0, var1 + 1)) <= 0 ? var3.nextInt(Math.abs(var0)) : var0]; var0-- > 0; var4[var0] = var2.charAt(var3.nextInt(var2.length()))) {
        }

        return new String(var4);
    }

    public static String findCapitalized(String var0) {
        Matcher var2 = b.matcher(var0);
        StringBuilder var1 = new StringBuilder();

        while (var2.find()) {
            var1.append(var2.group());
        }

        return var1.toString();
    }

    public static @Nullable String capitalize(@Nullable String s) {
        if (Strings.isNullOrEmpty(s)) {
            return s;
        } else {
            int var1 = s.length();
            StringBuilder var2 = new StringBuilder(var1);
            boolean var3 = true;

            for (int var4 = 0; var4 < var1; ++var4) {
                char var5;
                if ((var5 = s.charAt(var4)) != ' ' && var5 != '_' && var5 != '-') {
                    if (var3) {
                        var2.append(Character.toTitleCase(var5));
                        var3 = false;
                    } else {
                        var2.append(Character.toLowerCase(var5));
                    }
                } else {
                    if (var5 == '_' || var5 == '-') {
                        var2.append(' ');
                    }

                    var3 = true;
                }
            }

            return var2.toString();
        }
    }

    public static boolean contains(String var0, char var1) {
        return var0.indexOf(var1) >= 0;
    }

    public static List<String> splitByLength(List<String> var0, int var1) {
        ArrayList<String> var2 = new ArrayList<>(var0.size());

        for (String s : var0) {
            String var3;
            for (var3 = s; var3.length() > var1; var3 = var3.substring(var1)) {
                var2.add(var3.substring(0, var1));
            }

            var2.add(var3);
        }

        return var2;
    }

    public static String configOption(@Nullable Enum<?> e) {
        return e == null ? null : configOption(e.name());
    }

    public static String configOption(@Nullable String var0) {
        if (Strings.isNullOrEmpty(var0)) {
            return var0;
        } else {
            char[] var1 = var0.toCharArray();
            int var4 = var0.length();

            for (int var2 = 0; var2 < var4; ++var2) {
                char var3;
                if ((var3 = var1[var2]) == '_') {
                    var1[var2] = '-';
                } else {
                    var1[var2] = (char) (var3 | 32);  // to lower case
                }
            }

            return new String(var1);
        }
    }

    public static String configOptionToEnum(@Nullable String name) {
        if (Strings.isNullOrEmpty(name)) {
            return name;
        } else {
            char[] chars = name.toCharArray();
            int length = name.length();

            for (int i = 0; i < length; ++i) {
                char var3;
                if ((var3 = chars[i]) == '-') {
                    chars[i] = '_';
                } else {
                    chars[i] = (char) (var3 & 95); // to upper case
                }
            }

            return new String(chars);
        }
    }

    public static String findSimilar(String var01, Collection<String> var1) {
        String var0 = var01.toLowerCase();
        return var1.stream().filter((var1x) -> var0.equals(var1x.toLowerCase())).findFirst().orElseGet(() -> var1.stream().filter((var1x) -> {
            var1x = var1x.toLowerCase();
            if (!var0.contains(var1x) && !var1x.contains(var0)) {
                return false;
            } else {
                int var3 = var0.length();
                int var4 = var1x.length();
                return Math.abs(var3 - var4) < (var3 >= 5 && var4 >= 5 ? 10 : 3);
            }
        }).findFirst().orElse(null));
    }

    public static boolean areElementsEmpty(Collection<String> var0) {
        if (var0 != null && !var0.isEmpty()) {
            Iterator<String> var2 = var0.iterator();

            do {
                if (!var2.hasNext()) {
                    return true;
                }
            } while (var2.next().trim().isEmpty());

            return false;
        } else {
            return true;
        }
    }

    public static String repeat(String var0, int var1) {
        if (!var0.isEmpty() && var1 != 0) {
            char[] var8;
            char[] var2 = new char[(var8 = var0.toCharArray()).length * var1];
            int var3 = 0;

            while (var1-- > 0) {
                int var5 = var8.length;

                for (char var7 : var8) {
                    var2[var3++] = var7;
                }
            }

            return new String(var2);
        } else {
            return "";
        }
    }

    public static String repeat(char var0, int var1) {
        char[] var2 = new char[var1];
        Arrays.fill(var2, var0);
        return new String(var2);
    }

    public static boolean containsWhitespace(String var0) {
        char[] var4;
        int var1 = (var4 = var0.toCharArray()).length;

        for (int var2 = 0; var2 < var1; ++var2) {
            if (Character.isWhitespace(var4[var2])) {
                return false;
            }
        }

        return true;
    }

    @Contract("!null -> !null; null -> null")
    public static String toLatinLowerCase(String string) {
        if (Strings.isNullOrEmpty(string)) {
            return string;
        } else {
            char[] var1 = string.toCharArray();
            int length = var1.length;
            boolean hasUpperCase = false;

            for (int var4 = 0; var4 < length; ++var4) {
                char var5 = var1[var4];
                if (var5 >= 'A' && var5 <= 'Z') {
                    var5 = (char) (var5 | 0b100000);
                    hasUpperCase = true;
                }

                var1[var4] = var5;
            }

            if (hasUpperCase) {
                return new String(var1);
            } else {
                return string;
            }
        }
    }

    public static CharSequence replace(String var0, char var1, String var2) {
        if (Strings.isNullOrEmpty(var0)) {
            return var0;
        } else {
            int var3;
            if ((var3 = var0.indexOf(var1)) == -1) {
                return var0;
            } else {
                int var4 = Math.max(0, var2.length() - 1) * 50;
                StringBuilder var6 = new StringBuilder(var0.length() + var4);

                int var5;
                for (var5 = 0; var3 != -1; var3 = var0.indexOf(var1, var5)) {
                    var6.append(var0, var5, var3).append(var2);
                    var5 = var3 + 1;
                }

                return var6.append(var0.substring(var5));
            }
        }
    }

    public static CharSequence replace(String var0, char var1, char var2) {
        if (Strings.isNullOrEmpty(var0)) {
            return var0;
        } else {
            int var3;
            if ((var3 = var0.indexOf(var1)) < 0) {
                return var0;
            } else {
                char[] var4;
                for (var4 = var0.toCharArray(); var3 > 0; var3 = var0.indexOf(var1, var3 + 1)) {
                    var4[var3] = var2;
                }

                return new String(var4);
            }
        }
    }

    public static String generatedToString(Object var0) {
        StringBuilder var1 = new StringBuilder(var0.getClass().getSimpleName() + '{');
        Field[] var2;
        int var3 = (var2 = var0.getClass().getDeclaredFields()).length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Field var5;
            (var5 = var2[var4]).setAccessible(true);

            try {
                Object var6 = var5.get(var0);
                var1.append(var5.getName()).append('=').append(var6).append(", ");
            } catch (IllegalAccessException var7) {
                throw new RuntimeException(var7);
            }
        }

        return var1.append('}').toString();
    }

    @Nullable
    public static String toLatinUpperCase(@Nullable String str) {
        if (Strings.isNullOrEmpty(str)) {
            return str;
        } else {
            char[] var1 = str.toCharArray();
            int var2 = var1.length;
            boolean var3 = false;

            for (int var4 = 0; var4 < var2; ++var4) {
                char var5;
                if ((var5 = var1[var4]) >= 'a' && var5 <= 'z') {
                    var5 = (char) (var5 & 95);
                    var3 = true;
                }

                var1[var4] = var5;
            }

            if (var3) {
                return new String(var1);
            } else {
                return str;
            }
        }
    }

    public static String upperCaseReplaceChar(@Nullable String var0, char var1, char var2) {
        if (Strings.isNullOrEmpty(var0)) {
            return var0;
        } else {
            char[] var6;
            int var3 = (var6 = var0.toCharArray()).length;

            for (int var4 = 0; var4 < var3; ++var4) {
                char var5;
                if ((var5 = var6[var4]) == var1) {
                    var5 = var2;
                } else if (var5 >= 'a' && var5 <= 'z') {
                    var5 = (char) (var5 & 95);
                }

                var6[var4] = var5;
            }

            return new String(var6);
        }
    }

    public static String join(Object[] array, String separator) {
        return array == null ? null : join(array, separator, 0, array.length);
    }

    public static String join(Object[] array, String separator, int start, int end) {
        if (array == null) {
            return null;
        } else {
            if (separator == null) {
                separator = "";
            }

            int var4;
            if ((var4 = end - start) <= 0) {
                return "";
            } else {
                var4 *= (array[start] == null ? 16 : array[start].toString().length()) + separator.length();
                StringBuilder var6 = new StringBuilder(var4);

                for (int var5 = start; var5 < end; ++var5) {
                    if (var5 > start) {
                        var6.append(separator);
                    }

                    if (array[var5] != null) {
                        var6.append(array[var5]);
                    }
                }

                return var6.toString();
            }
        }
    }

    public static String lowerCaseReplaceChar(@Nullable String string, char var1, char var2) {
        if (Strings.isNullOrEmpty(string)) {
            return string;
        } else {
            char[] var6;
            int var3 = (var6 = string.toCharArray()).length;

            for (int var4 = 0; var4 < var3; ++var4) {
                char var5;
                if ((var5 = var6[var4]) == var1) {
                    var5 = var2;
                } else if (var5 >= 'a' && var5 <= 'z') {
                    var5 = (char) (var5 | 32);
                }

                var6[var4] = var5;
            }

            return new String(var6);
        }
    }

    public static boolean isEnglish(@Nullable CharSequence var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1;
            if ((var1 = var0.length()) == 0) {
                return false;
            } else {
                for (int var2 = 0; var2 < var1; ++var2) {
                    char var3;
                    if ((var3 = var0.charAt(var2)) != '_' && var3 != ' ' && !isEnglishLetterOrDigit(var3)) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static URL validateURL(String value) {
        try {
            URL var2;
            (var2 = new URL(value)).toURI();
            return var2;
        } catch (MalformedURLException | URISyntaxException exc) {
            return null;
        }
    }

    public static boolean hasSymbol(@Nullable CharSequence var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1;
            if ((var1 = var0.length()) == 0) {
                return false;
            } else {
                for (int var2 = 0; var2 < var1; ++var2) {
                    char var3;
                    if ((var3 = var0.charAt(var2)) != '_' && var3 != ' ' && !Character.isLetterOrDigit(var3)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public static boolean isEnglishLetter(char var0) {
        return var0 >= 'A' && var0 <= 'Z' || var0 >= 'a' && var0 <= 'z';
    }

    public static boolean isEnglishDigit(char var0) {
        return var0 >= '0' && var0 <= '9';
    }

    @NonNull
    public static List<String> cleanSplit(@NotNull String var0, char var1) {
        return split(deleteWhitespace(var0), var1, false);
    }

    @NonNull
    public static List<String> cleanSplitManaged(@NotNull String var0, char var1) {
        if (var1 != ' ') {
            var0 = deleteWhitespace(var0);
        }

        return split(var0, var1, false);
    }

    @Nullable
    @Contract("null -> null; !null -> !null")
    public static String deleteWhitespace(@Nullable String var0) {
        if (Strings.isNullOrEmpty(var0)) {
            return var0;
        } else {
            int oriLength = var0.length();
            char[] newStrChars = new char[oriLength];
            int var3 = 0;

            for (int var4 = 0; var4 < oriLength; ++var4) {
                char var5;
                if ((var5 = var0.charAt(var4)) != ' ') {
                    newStrChars[var3++] = var5;
                }
            }

            if (var3 == oriLength) {
                return var0;
            } else {
                return new String(newStrChars, 0, var3);
            }
        }
    }

    public static CharSequence join(char var0, CharSequence... var1) {
        int var2;
        if ((var2 = var1.length) == 0) {
            return null;
        } else if (var2 == 1) {
            return var1[0];
        } else {
            CharSequence[] var3 = var1;
            int var4 = var1.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                CharSequence var6 = var3[var5];
                var2 += var6.length();
            }

            char[] var11 = new char[var2 - 1];
            var2 = var1.length;
            var4 = 0;
            int var13 = var1.length;

            for (CharSequence charSequence : var1) {
                CharSequence var7;
                int var8 = (var7 = charSequence).length();

                for (int var9 = 0; var9 < var8; ++var9) {
                    var11[var4++] = var7.charAt(var9);
                }

                --var2;
                if (var2 > 0) {
                    var11[var4++] = var0;
                }
            }

            return new String(var11);
        }
    }

    public static String join(String var0, @NotNull Collection<String> strings, Function<String, String> var2) {
        return join(var0, strings, var2, "[]");
    }

    public static String join(String var0, @NotNull Collection<String> strings, Function<String, String> replacer, String whenEmpty) {
        if (strings.isEmpty()) {
            return whenEmpty;
        } else {
            StringBuilder builder = new StringBuilder();
            int var4 = 0;

            String var5;
            for (Iterator<String> it = strings.iterator(); it.hasNext(); builder.append(replacer.apply(var5))) {
                var5 = it.next();
                if (var4++ != 0) {
                    builder.append(var0);
                }
            }

            return builder.toString();
        }
    }

    /**
     * 1 -> 1st, 4 -> 4th, 26 - 26th...
     *
     * @param num The ordinal number
     */
    public static String toOrdinalNumeral(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Ordinal numerals must start from 1");
        } else {
            String str = Integer.toString(num);
            char lastChar = str.charAt(str.length() - 1);
            if (lastChar == '1') {
                return str + "st";
            }
            if (lastChar == '2') {
                return str + "nd";
            }
            if (lastChar == '3') {
                return str + "rd";
            }
            return str + "th";
        }
    }

    @NonNull
    public static List<String> split(@NotNull String string, char separator, boolean var2) {
        //noinspection ConstantValue
        if (string == null) {
            throw new IllegalArgumentException("Cannot split a null string: " + string);
        } else {
            ArrayList<String> var3 = new ArrayList<>();
            if (string.isEmpty()) {
                var3.add("");
            } else {
                boolean var4 = false;
                int var5 = string.length();
                int var6 = 0;

                for (int var7 = 0; var7 < var5; ++var7) {
                    if (string.charAt(var7) != separator) {
                        var4 = true;
                    } else {
                        if (var4 || var2) {
                            var3.add(string.substring(var6, var7));
                            var4 = false;
                        }

                        var6 = var7 + 1;
                    }
                }

                if (var4 || var2) {
                    var3.add(string.substring(var6, var5));
                }
            }
            return var3;
        }
    }

    @NonNull
    public static List<SplitInfo> advancedSplit(@NotNull String var0, char sep, boolean var2) {
        if (isNullOrEmpty(var0)) {
            throw new IllegalArgumentException("Cannot split a null or empty string: " + var0);
        } else {
            ArrayList<SplitInfo> var3 = new ArrayList<>();
            boolean var4 = false;
            boolean var5 = false;
            int var6 = var0.length();
            int var7 = 0;

            for (int var8 = 0; var8 < var6; ++var8) {
                if (var0.charAt(var8) != sep) {
                    var5 = false;
                    var4 = true;
                } else {
                    if (var4 || var2) {
                        var3.add(new SplitInfo(var0.substring(var7, var8), var7, var8));
                        var4 = false;
                        var5 = true;
                    }

                    var7 = var8 + 1;
                }
            }

            if (var4 || var2 && var5) {
                var3.add(new SplitInfo(var0.substring(var7, var6), var7, var6));
            }

            return var3;
        }
    }

    @Deprecated
    public static String[] splitLocation(@NotNull String var0, int var1) {
        String[] var2 = new String[var1];
        int var3 = var0.length();
        int var4 = 0;
        var1 = 0;

        for (int var5 = 0; var5 < var3; ++var5) {
            if (var0.charAt(var5) == ',') {
                var2[var1++] = var0.substring(var4, var5);
                var5 += 2;
                var4 = var5;
            }
        }

        var2[var1] = var0.substring(var4, var3);
        return var2;
    }

    public static boolean isEnglishLetterOrDigit(char var0) {
        return isEnglishDigit(var0) || isEnglishLetter(var0);
    }

    /**
     * 返回一个字符串是否含有数字
     * 数字指 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
     */
    @Contract("null -> false")
    public static boolean containsNumber(@Nullable CharSequence charSequence) {
        if (charSequence != null) {
            int var1;
            if ((var1 = charSequence.length()) != 0) {
                for (int var2 = 0; var2 < var1; ++var2) {
                    if (isEnglishDigit(charSequence.charAt(var2))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Contract("null -> false")
    public static boolean containsAnyLangNumber(@Nullable CharSequence charSequence) {
        if (charSequence != null) {
            int var1 = charSequence.length();
            if (var1 != 0) {
                for (int var2 = 0; var2 < var1; ++var2) {
                    if (Character.isDigit(charSequence.charAt(var2))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Nullable
    public static Dimension getImageSize(@NotNull URL url) {
        try {
            InputStream var16 = url.openStream();

            label151:
            {
                Dimension var17;
                try {
                    label152:
                    {
                        ImageInputStream var1 = ImageIO.createImageInputStream(var16);

                        label153:
                        {
                            try {
                                Iterator<ImageReader> var2 = ImageIO.getImageReaders(var1);
                                if (!var2.hasNext()) {
                                    break label153;
                                }

                                ImageReader var3 = var2.next();

                                try {
                                    var3.setInput(var1);
                                    var17 = new Dimension(var3.getWidth(0), var3.getHeight(0));
                                } finally {
                                    var3.dispose();
                                }
                            } catch (Throwable var13) {
                                if (var1 != null) {
                                    try {
                                        var1.close();
                                    } catch (Throwable var11) {
                                        var13.addSuppressed(var11);
                                    }
                                }

                                throw var13;
                            }

                            if (var1 != null) {
                                var1.close();
                            }
                            break label152;
                        }

                        if (var1 != null) {
                            var1.close();
                        }
                        break label151;
                    }
                } catch (Throwable var14) {
                    if (var16 != null) {
                        try {
                            var16.close();
                        } catch (Throwable var10) {
                            var14.addSuppressed(var10);
                        }
                    }

                    throw var14;
                }

                if (var16 != null) {
                    var16.close();
                }

                return var17;
            }

            if (var16 != null) {
                var16.close();
            }
        } catch (IOException ignored) {
        }

        return null;
    }

    public static boolean isNumeric(@Nullable String var0) {
        if (var0 == null) {
            return false;
        } else {
            int var1;
            if ((var1 = var0.length()) == 0) {
                return false;
            } else {
                int var2 = 0;
                char var3;
                if (var1 != 1 && ((var3 = var0.charAt(0)) == '-' || var3 == '+')) {
                    var2 = 1;
                }

                do {
                    if (var2 >= var1) {
                        return true;
                    }
                } while (isEnglishDigit(var0.charAt(var2++)));

                return false;
            }
        }
    }

    public static <K, V> StringBuilder associatedArrayMap(Map<K, V> map) {
        return a(map, 1);
    }

    private static <K, V> StringBuilder a(Map<K, V> var0, int var1) {
        if (var0 == null) {
            return new StringBuilder("<null>");
        } else {
            StringBuilder builder = new StringBuilder(var0.size() * 15);
            builder.append(var0.getClass().getSimpleName()).append('{');

            for (Map.Entry<K, V> kvEntry : var0.entrySet()) {
                builder.append('\n');
                builder.append(spaces(var1 << 1));
                builder.append(kvEntry.getKey()).append(" => ");
                V var5 = kvEntry.getValue();
                builder.append(var5 instanceof Map ? a((Map<K, V>) var5, var1 + 1) : var5);
                builder.append('\n');
            }

            return builder.append('}');
        }
    }

    public static String spaces(int var0) {
        if (var0 <= 0) {
            return "";
        } else {
            char[] var1;
            Arrays.fill(var1 = new char[var0], ' ');
            return new String(var1);
        }
    }

    public static boolean isPureNumber(@Nullable String var0) {
        if (isNullOrEmpty(var0)) {
            return false;
        } else {
            int var1 = var0.length();

            for (int var2 = 0; var2 < var1; ++var2) {
                if (!isEnglishDigit(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static int indexOfAny(String var0, String[] var1) {
        Objects.requireNonNull(var0);
        Objects.requireNonNull(var1);
        int var2 = Integer.MAX_VALUE;
        int var3 = var1.length;

        for (String s : var1) {
            String var5;
            int var6;
            if ((var5 = s) != null && (var6 = var0.indexOf(var5)) != -1 && var6 < var2) {
                var2 = var6;
            }
        }

        if (var2 == Integer.MAX_VALUE) {
            return -1;
        } else {
            return var2;
        }
    }

    @NotNull
    public static String getGroupedOption(@NotNull String str, int @NotNull ... var1) {
        Checker.Arg.notNull(str, "str", "Enum option name cannot be null");
        String var0 = toLatinLowerCase(str);
        if (var1.length == 0) {
            return var0.replace('_', '-');
        } else {
            String[] var2;
            if ((var2 = splitArray(var0, '_', false)).length < var1.length) {
                throw new IllegalArgumentException("Groups cannot be greater than enum separators: " + str);
            } else {
                boolean[] var3 = new boolean[var2.length];
                int var4 = var1.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    int var6 = var1[var5];
                    var3[var6 - 1] = true;
                }

                StringBuilder var7 = new StringBuilder(str.length());

                for (var4 = 0; var4 < var2.length; ++var4) {
                    var7.append(var2[var4]);
                    if (var3[var4]) {
                        var7.append('.');
                    } else {
                        var7.append('-');
                    }
                }

                var7.setLength(var7.length() - 1);
                return var7.toString();
            }
        }
    }

    public static @NotNull String @NotNull [] splitArray(@NotNull String string, char separator) {
        return splitArray(string, separator, false);
    }

    /**
     * 用某个字符分割字符串.
     * <p>
     * 保留空字符串:
     * <p>
     * 分割一个值为 {@code "..123..789.."} 的字符串, 分隔符为 {@code '.'}.
     * <p>
     * 若保留空字符串, 则会返回一个长度为 7 的数组 {@code [, , 123, , 789, , ]}
     * <p>
     * 若不保留空字符串, 则返回长为 2 的数组 {@code [123, 789]}
     *
     * @param string          要分割的字符串
     * @param separator       分隔符
     * @param keepEmptyString 是否保留分割出来的空字符串
     * @return 分割后的字符串数组
     */
    public static @NotNull String @NotNull [] splitArray(@NotNull String string, char separator, boolean keepEmptyString) {
        Checker.Arg.notNull(string, "string");

        int length = string.length();
        if (length == 0) {  // ""
            return keepEmptyString ? new String[]{""} : new String[0];
        } else {
            ArrayList<String> list = new ArrayList<>();
            int index = 0;
            int var6 = 0;
            boolean var7 = false;
            boolean var8 = false;


            while (index < length) {
                if (string.charAt(index) == separator) {
                    if (var7 || keepEmptyString) {
                        list.add(string.substring(var6, index));
                        var7 = false;
                        var8 = true;
                    }

                    ++index;
                    var6 = index;
                } else {
                    var8 = false;
                    var7 = true;
                    ++index;
                }
            }

            if (var7 || keepEmptyString && var8) {
                list.add(string.substring(var6, index));
            }

            return list.toArray(new String[0]);
        }
    }

    public static @NotNull String reverse(@NotNull String string) {
        Checker.Arg.notNull(string, "string");

        char[] var4 = string.toCharArray();
        int var1 = 0;

        for (int var2 = var4.length - 1; var2 > var1; ++var1) {
            char var3 = var4[var1];
            var4[var1] = var4[var2];
            var4[var2] = var3;
            --var2;
        }

        return new String(var4);
    }

    public static boolean isOneOf(@Nullable String var0, @NonNull String... var1) {
        return !Strings.isNullOrEmpty(var0) && Arrays.asList(var1).contains(var0);
    }

    @NotNull
    public static String toFancyNumber(double var0) {
        return a.format(var0);
    }

    @Nullable
    public static String remove(@Nullable String var0, char var1) {
        if (Strings.isNullOrEmpty(var0)) {
            return var0;
        } else {
            char[] var2 = var0.toCharArray();
            int var3 = 0;
            int var5 = var2.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                char var7;
                if ((var7 = var2[var6]) != var1) {
                    var2[var3++] = var7;
                }
            }

            if (var2.length == var3) {
                return var0;
            } else {
                return new String(var2, 0, var3);
            }
        }
    }

    public static String remove(String var0, String var1) {
        Objects.requireNonNull(var0);
        Objects.requireNonNull(var1);
        return replace(var0, var1, "", -1);
    }

    public static String replaceOnce(String var0, String var1, String var2) {
        return replace(var0, var1, var2, 1);
    }

    public static String replace(String string, String oldPart, String newPart) {
        return replace(string, oldPart, newPart, -1);
    }

    public static String replace(String string, String oldPart, String newPart, int limit) {
        if (!string.isEmpty() && !oldPart.isEmpty() && newPart != null && limit != 0) {
            int var4 = 0;
            int var5 = string.indexOf(oldPart);
            if (var5 == -1) {
                return string;
            } else {
                int oldPartLen = oldPart.length();
                int var7 = Math.max(newPart.length() - oldPartLen, 0) * (limit < 0 ? 16 : Math.min(limit, 64));

                StringBuilder builder;
                for (builder = new StringBuilder(string.length() + var7); var5 != -1; var5 = string.indexOf(oldPart, var4)) {
                    builder.append(string, var4, var5).append(newPart);
                    var4 = var5 + oldPartLen;
                    --limit;
                    if (limit == 0) {
                        break;
                    }
                }

                builder.append(string.substring(var4));
                return builder.toString();
            }
        } else {
            return string;
        }
    }

    public static boolean containsAny(@Nullable String var0, @NonNull String... var1) {
        if (!Strings.isNullOrEmpty(var0) && var1.length != 0) {
            int var2 = (var1).length;

            for (String var4 : var1) {
                if (var0.contains(var4)) {
                    return true;
                }
            }
        }
        return false;
    }

    static {
        a = new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.ENGLISH));
        b = Pattern.compile("([A-Z])");
    }

    public static final class SplitInfo {
        public final String text;
        public final int index;
        public final int endIndex;

        public SplitInfo(String var1, int var2, int var3) {
            this.text = var1;
            this.index = var2;
            this.endIndex = var3;
        }
    }
}
