package net.aurika.util.number;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.Nullable;

public final class RomanNumber {
    private static final int[] a = new int[22];
    private static final String[] thousands = new String[]{"", "M", "MM", "MMM"};
    private static final String[] hundreds = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] tens = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] ones = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    private RomanNumber() {
    }

    public static @NonNull String toRoman(@IntRange(from = 1L, to = 3999L) int number) {
        //noinspection ConstantValue
        if (number > 0 && number < 4000) {
            String thousand = thousands[number / 1000];
            String hundred = hundreds[number % 1000 / 100];
            String ten = tens[number % 100 / 10];
            String one = ones[number % 10];
            return thousand + hundred + ten + one;
        } else {
            return String.valueOf(number);
        }
    }

    private static @IntRange(from = 0L, to = 1000L) int a(char var0) {
        //noinspection ConstantValue
        return (var0 -= 67) >= 0 && var0 <= a.length ? a[var0] : 0;
    }

    public static @IntRange(from = 0L, to = 3999L) int fromRoman(@Nullable CharSequence charSeq) {
        if (charSeq == null) {
            return 0;
        } else {
            int charsLength = charSeq.length();
            if (charsLength == 0) {
                return 0;
            } else {
                --charsLength;
                int var2 = 0;

                int var3;
                for (var3 = 0; var3 < charsLength; ++var3) {
                    int var4;
                    if ((var4 = a(charSeq.charAt(var3))) == 0) {
                        return 0;
                    }

                    if (var4 >= a(charSeq.charAt(var3 + 1))) {
                        var2 += var4;
                    } else {
                        var2 -= var4;
                    }
                }

                if ((var3 = a(charSeq.charAt(charsLength))) == 0) {
                    return 0;
                } else {
                    return var2 + var3;
                }
            }
        }
    }

    static {
        a[10] = 1000;
        a[1] = 500;
        a[0] = 100;
        a[9] = 50;
        a[21] = 10;
        a[19] = 5;
        a[6] = 1;
    }
}

