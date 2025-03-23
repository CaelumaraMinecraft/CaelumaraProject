package net.aurika.auspice.utils.math;

import net.aurika.auspice.text.compiler.PlaceholderTranslationContext;
import net.aurika.auspice.configs.messages.placeholders.context.VariableProvider;
import net.aurika.auspice.utils.compiler.math.MathExpression;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public final class MathUtils {
    private static final String[] a;

    private static final DecimalFormat b;

    public MathUtils() {
    }

    public static int getPageNumbers(int var0, int var1) {
        if (var1 <= 0) {
            throw new IllegalArgumentException("Size is zero or negative: " + var1);
        } else if (var0 < 0) {
            throw new IllegalArgumentException("Item count is negative: " + var0);
        } else {
            return Math.max(1, var0 % var1 != 0 ? var0 / var1 + 1 : var0 / var1);
        }
    }

    public static int getMiddleNumber(double var0, double var2) {
        return (int) Math.round((var0 + var2) / 2.0);
    }

    public static double getFractionalPart(double var0) {
        return var0 - (double) ((int) var0);
    }

    public static double eval(MathExpression mathExpression, VariableProvider variableProvider) {
        return mathExpression.eval((varName) -> expectDouble(varName, variableProvider.provideVariable(varName)));
    }

    public static Double expectDouble(String var0, Object obj) {
        if (obj == null) {
            return null;
        } else {
            if (obj instanceof PlaceholderTranslationContext && (obj = ((PlaceholderTranslationContext) obj).getValue()) instanceof String) {
                try {
                    obj = Double.parseDouble(obj.toString());
                } catch (NumberFormatException ignored) {
                }
            }

            if (obj instanceof Number) {
                return ((Number) obj).doubleValue();
            } else if (obj instanceof Boolean) {
                return (Boolean) obj ? 1.0 : 0.0;
            } else if (obj instanceof String) {
                return (double) obj.hashCode();
            } else {
                throw new IllegalArgumentException("Expected an arithmetic placeholder for '" + var0 + "' instead got '" + obj + "' (" + obj.getClass().getName() + ')');
            }
        }
    }

    public static Double parseDouble(String str) {
        try {
            double value = Double.parseDouble(str);
            return !Double.isNaN(value) && value != Double.NEGATIVE_INFINITY && value != Double.POSITIVE_INFINITY ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Float parseFloat(String str) {
        try {
            float var2 = Float.parseFloat(str);
            return !Float.isNaN(var2) && var2 != Float.NEGATIVE_INFINITY && var2 != Float.POSITIVE_INFINITY ? var2 : null;
        } catch (NumberFormatException var1) {
            return null;
        }
    }

    public static Integer parseInt(CharSequence var0) {
        return parseInt(var0, true);
    }

    public static Integer parseInt(CharSequence var0, boolean var1) {
        int var2;
        if ((var2 = var0.length()) != 0 && var2 <= 11) {
            int var3 = 0;
            if (var1) {
                if (var0.charAt(0) != '-') {
                    var1 = false;
                } else {
                    if (var2 == 1) {
                        return null;
                    }

                    ++var3;
                }
            }

            int var4;
            int var10000 = (var4 = var1 ? Integer.MIN_VALUE : -2147483647) / 10;

            int var5;
            int var6;
            for (var5 = 0; var3 < var2; var5 -= var6) {
                if ((var6 = a(var0.charAt(var3++))) < 0 || var5 < -214748364) {
                    return null;
                }

                if ((var5 *= 10) < var4 + var6) {
                    return null;
                }
            }

            return var1 ? var5 : -var5;
        } else {
            return null;
        }
    }

    public static Integer parseIntUnchecked(CharSequence var0, boolean var1) {
        int var2 = 0;
        if (var1) {
            if (var0.charAt(0) != '-') {
                var1 = false;
            } else {
                ++var2;
            }
        }

        int var3 = var0.length();

        int var4;
        for (var4 = 0; var2 < var3; var4 = var4 * 10 - (var0.charAt(var2++) - 48)) {
        }

        return var1 ? var4 : -var4;
    }

    public static Long parseLong(CharSequence charSequence) {
        return parseLong(charSequence, true);
    }

    public static Long parseLong(CharSequence charSequence, boolean negative) {
        int var2 = charSequence.length();
        if (var2 != 0 && var2 <= 20) {
            int var3 = 0;
            if (negative) {
                if (charSequence.charAt(0) != '-') {
                    negative = false;
                } else {
                    ++var3;
                }
            }

            long var4 = negative ? Long.MIN_VALUE : -9223372036854775807L;
            long var10000 = var4 / 10L;

            long var6;
            int var8;
            for (var6 = 0L; var3 < var2; var6 -= var8) {
                if ((var8 = a(charSequence.charAt(var3++))) < 0 || var6 < -922337203685477580L) {
                    return null;
                }

                if ((var6 *= 10L) < var4 + (long) var8) {
                    return null;
                }
            }

            return negative ? var6 : -var6;
        } else {
            return null;
        }
    }

    private static int a(int var0) {
        return (var0 -= 48) >= 0 && var0 < 10 ? var0 : -1;
    }

    public static long averageOf(long... var0) {
        return Arrays.stream(var0).sum() / (long) var0.length;
    }

    public static boolean hasChance(double var0) {
        return ThreadLocalRandom.current().nextDouble(0.0, 100.0) <= var0;
    }

    public static int randInt(int var0, int var1) {
        return ThreadLocalRandom.current().nextInt(var0, var1 + 1);
    }

    public static double getPercent(double var0, double var2) {
        return getAmountFromAmount(var0, var2, 100.0);
    }

    public static double percentOfAmount(double var0, double var2) {
        return var2 * var0 / 100.0;
    }

    public static int percentOfPercent(int... var0) {
        int var1 = 1;

        for (int var4 : var0) {
            var1 = (int) ((double) var1 * ((double) var4 / 100.0));
        }

        return var1;
    }

    public static double getAmountFromAmount(double var0, double var2, double var4) {
        return var0 / var2 * var4;
    }

    public static double roundToDigits(double var0, int var2) {
        if (var2 <= 0) {
            return (double) Math.round(var0);
        } else {
            var2 = (int) Math.pow(10.0, var2);
            return (double) Math.round(var0 * (double) var2) / (double) var2;
        }
    }

    public static String getShortNumber(Number var0) {
        double var1 = var0.doubleValue();
        String var4 = b.format(var1);
        if (var1 < 1000.0) {
            return var4;
        } else {
            String var5;
            try {
                var5 = a[(var4.length() - 1) / 3 - 1];
            } catch (IndexOutOfBoundsException var3) {
                return var4.substring(0, 2) + 'e' + (var4.length() - 2);
            }

            int var2;
            if ((var2 = var4.length() % 3) == 0) {
                var2 = 3;
            }

            return var4.substring(0, var2) + '.' + var4.charAt(var2) + var5;
        }
    }

    public static int increasingRandInt(int var0, int var1) {
        ArrayList<Integer> var2 = new ArrayList<>();

        for (; var0 < var1; ++var0) {
            for (int var3 = var0; var3 >= 0; --var3) {
                var2.add(var3);
            }
        }

        var0 = randInt(0, var2.size() - 1);
        return var2.get(var0);
    }

    public static int naturalSum(int var0) {
        return var0 * (var0 + 1) / 2;
    }

    public static double factorial(int var0) {
        double var1;
        for (var1 = var0; var0 > 1; var1 *= var0) {
            --var0;
        }

        return var1;
    }

    public static boolean isPrime(int var0) {
        if (var0 < 2) {
            return false;
        } else if (var0 == 2) {
            return true;
        } else if (var0 % 2 == 0) {
            return false;
        } else {
            for (int var1 = 3; var1 * var1 <= var0; var1 += 2) {
                if (var0 % var1 == 0) {
                    return false;
                }
            }

            return true;
        }
    }

    public static int[] generatePrimes(int var0) {
        if (var0 < 2) {
            return new int[0];
        } else if (var0 == 2) {
            return new int[]{2};
        } else {
            int[] var1 = new int[var0 / 2];
            int var2 = 1;
            var1[0] = 2;

            for (int var3 = 3; var3 * var3 <= var0; var3 += 2) {
                if (var0 % var3 == 0) {
                    var1[var2++] = var3;
                }
            }

            return Arrays.copyOf(var1, var2);
        }
    }

    public static double sqrtn(double var0, int var2) {
        return (double) Math.round(Math.pow(var0, 1.0 / (double) var2));
    }

    public static boolean isEven(int var0) {
        return (var0 & 1) == 0;
    }

    public static boolean isOdd(int var0) {
        return (var0 & 1) == 1;
    }

    static {
        a = new String[]{"K", "M", "B", "T", "Quadrillion", "Quintillion", "Sextillion", "Septillion", "Octillion", "Nonillion", "Decillion", "Undecillion", "Duodecillion", "Tredecillion", "Quattuordecillion", "Quindecillion", "Sexdecillion", "Septendecillion", "Octodecillion", "Novemdecillion", "Vigintillion", "Unvigintillion", "Duovigintillion", "Tresvigintillion", "Quattuor\u00advigint\u00adillion", "Quinvigintillion", "Sesvigintillion", "Septemvigintillion", "Octovigintillion", "Novemvigintillion", "Trigintillion", "Untrigintillion", "Duotrigintillion", "Trestrigintillion", "Quattuor\u00adtrigint\u00adillion", "Quintrigintillion", "Sestrigintillion", "Septentrigintillion", "Octotrigintillion", "Noventrigintillion", "Quadragintillion"};
        b = new DecimalFormat("#", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
    }

}

