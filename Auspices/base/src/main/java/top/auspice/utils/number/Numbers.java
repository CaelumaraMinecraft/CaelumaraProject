package top.auspice.utils.number;

import com.google.common.base.Strings;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public final class Numbers {
    private static final DecimalFormat CURRENCY_FORMAT = new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final DecimalFormat CURRENCY_DEC_3_FORMAT = new DecimalFormat("0.###", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final DecimalFormat CURRENCY_DEC_4_FORMAT = new DecimalFormat("0.####", new DecimalFormatSymbols(Locale.ENGLISH));
    private static final DecimalFormat SCIENTIFIC_FORMAT = new DecimalFormat("00E0", new DecimalFormatSymbols(Locale.ENGLISH));

    private Numbers() {
    }

    public static boolean isEnglishDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static String toOrdinalNumeral(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("Ordinal numerals must start from 1");
        } else {
            String str = Integer.toString(num);
            char lastDigit = str.charAt(str.length() - 1);
            if (lastDigit == '1') {
                return str + "st";
            } else if (lastDigit == '2') {
                return str + "nd";
            } else {
                return lastDigit == '3' ? str + "rd" : str + "th";
            }
        }
    }

    public static boolean containsNumber(@Nullable CharSequence str) {
        if (str == null) {
            return false;
        } else {
            int len = str.length();
            if (len == 0) {
                return false;
            } else {
                for (int i = 0; i < len; ++i) {
                    char ch = str.charAt(i);
                    if (isEnglishDigit(ch)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public static boolean containsAnyLangNumber(@Nullable CharSequence str) {
        if (str == null) {
            return false;
        } else {
            int len = str.length();
            if (len == 0) {
                return false;
            } else {
                for (int i = 0; i < len; ++i) {
                    char ch = str.charAt(i);
                    if (Character.isDigit(ch)) {
                        return true;
                    }
                }

                return false;
            }
        }
    }

    public static boolean isNumeric(@Nullable String str) {
        if (str == null) {
            return false;
        } else {
            int len = str.length();
            if (len == 0) {
                return false;
            } else {
                int i = 0;
                if (len != 1) {
                    char first = str.charAt(0);
                    if (first == '-' || first == '+') {
                        i = 1;
                    }
                }

                do {
                    if (i >= len) {
                        return true;
                    }
                } while (isEnglishDigit(str.charAt(i++)));

                return false;
            }
        }
    }

    public static boolean isPureNumber(@Nullable String str) {
        if (Strings.isNullOrEmpty(str)) {
            return false;
        } else {
            int len = str.length();

            for (int i = 0; i < len; ++i) {
                char ch = str.charAt(i);
                if (!isEnglishDigit(ch)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static int getPositionOfFirstNonZeroDecimal(double number) {
        if (number >= 1.0) {
            throw new IllegalArgumentException("Number is greater than 1.0");
        } else {
            return (int) Math.abs(Math.floor(Math.log10(Math.abs(number))));
        }
    }

    @Nonnull
    public static String toFancyNumber(double number) {
        if (number == 0.0) {
            return "0";
        } else {
            DecimalFormat format;
            boolean positive;
            label67:
            {
                positive = number >= 0.0;
                if (positive) {
                    if (!(number >= 0.01)) {
                        break label67;
                    }
                } else if (!(number <= -0.01)) {
                    break label67;
                }

                format = CURRENCY_FORMAT;
                return format.format(number);
            }

            label68:
            {
                if (positive) {
                    if (!(number >= 0.001)) {
                        break label68;
                    }
                } else if (!(number <= -0.001)) {
                    break label68;
                }

                format = CURRENCY_DEC_3_FORMAT;
                return format.format(number);
            }

            label69:
            {
                if (positive) {
                    if (number >= 1.0E-4) {
                        break label69;
                    }
                } else if (number <= -1.0E-4) {
                    break label69;
                }

                format = SCIENTIFIC_FORMAT;
                return format.format(number);
            }

            format = CURRENCY_DEC_4_FORMAT;
            return format.format(number);
        }
    }

    public static double squared(double $this$) {
        return $this$ * $this$;
    }

    public static float squared(float $this$) {
        return $this$ * $this$;
    }

    public static int squared(int $this$) {
        return $this$ * $this$;
    }

    public static boolean isEven(@NotNull Number $this$) {
        Objects.requireNonNull($this$);
        return $this$.intValue() % 2 == 0;
    }

    public static void requireNonNegative(@NotNull Number $this$) {
        Objects.requireNonNull($this$);
        if (AnyNumber.of($this$).isNegative()) {
            throw new IllegalArgumentException("Required a non-negative number, but got: " + $this$);
        }
    }
}