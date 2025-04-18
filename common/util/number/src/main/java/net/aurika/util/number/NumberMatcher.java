package net.aurika.util.number;

import net.aurika.util.string.Strings;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public interface NumberMatcher {

  boolean matches(@NotNull AnyNumber var1);

  @NotNull String getAsString();

  static @Nullable NumberMatcher parse(@Nullable String value) {
    if (value == null || value.isBlank()) {
      return null;
    } else {
      String sanitized = value.replace(" ", "");
      AnyNumber it = AnyNumber.of(sanitized);
      if (it != null) {
        return new Exact(it);
      } else {
        return (new Parser(sanitized)).parse();
      }
    }
  }

  final class Exact implements NumberMatcher {

    private final @NotNull AnyNumber number;

    public Exact(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      this.number = number;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      return Objects.equals(this.number, number);
    }

    public @NotNull String getAsString() {
      return "Exact:" + this.number;
    }

  }

  final class LessThan implements NumberMatcher {

    @NotNull
    private final AnyNumber lessThan;

    public LessThan(@NotNull AnyNumber lessThan) {
      Validate.Arg.notNull(lessThan, "lessThan");
      this.lessThan = lessThan;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      return number.compareTo(this.lessThan) > 0;
    }

    @NotNull
    public String getAsString() {
      return "" + '>' + this.lessThan;
    }

  }

  final class LessThanOrEqual implements NumberMatcher {

    @NotNull
    private final AnyNumber lessThan;

    public LessThanOrEqual(@NotNull AnyNumber lessThan) {
      Validate.Arg.notNull(lessThan, "lessThan");
      this.lessThan = lessThan;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      return number.compareTo(this.lessThan) >= 0;
    }

    @NotNull
    public String getAsString() {
      return ">=" + this.lessThan;
    }

  }

  final class GreaterThan implements NumberMatcher {

    @NotNull
    private final AnyNumber greaterThan;

    public GreaterThan(@NotNull AnyNumber greaterThan) {
      Validate.Arg.notNull(greaterThan, "greaterThan");
      this.greaterThan = greaterThan;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      return number.compareTo(this.greaterThan) < 0;
    }

    @NotNull
    public String getAsString() {
      return "" + '<' + this.greaterThan;
    }

    @NotNull
    public String toString() {
      return "GreaterThan(" + this.greaterThan + ')';
    }

  }

  final class GreaterThanOrEqual implements NumberMatcher {

    @NotNull
    private final AnyNumber greaterThan;

    public GreaterThanOrEqual(@NotNull AnyNumber greaterThan) {
      Validate.Arg.notNull(greaterThan, "greaterThan");
      this.greaterThan = greaterThan;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      return number.compareTo(this.greaterThan) <= 0;
    }

    @NotNull
    public String getAsString() {
      return "<=" + this.greaterThan;
    }

  }

  final class Range implements NumberMatcher {

    @NotNull
    private final NumberMatcher first;
    @NotNull
    private final NumberMatcher second;

    public Range(@NotNull NumberMatcher first, @NotNull NumberMatcher second) {
      Validate.Arg.notNull(first, "first");
      Validate.Arg.notNull(second, "second");
      this.first = first;
      this.second = second;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      return this.first.matches(number) && this.second.matches(number);
    }

    @NotNull
    public String getAsString() {
      return this.first + " && " + this.second;
    }

    @NotNull
    public String toString() {
      return "NumberMatcher::Range(" + this.first + " && " + this.second + ')';
    }

  }

  final class Multiple implements NumberMatcher {

    @NotNull
    private final AnyNumber[] list;

    public Multiple(@NotNull AnyNumber[] list) {
      Validate.Arg.notNull(list, "list");
      this.list = list;
    }

    public boolean matches(@NotNull AnyNumber number) {
      Validate.Arg.notNull(number, "number");
      AnyNumber[] var2 = this.list;
      int var3 = 0;

      for (int var4 = var2.length; var3 < var4; ++var3) {
        AnyNumber num = var2[var3];
        if (Objects.equals(number, num)) {
          return true;
        }
      }

      return false;
    }

    @NotNull
    public String getAsString() {
      return "Multiple" + Arrays.toString(this.list);
    }

  }

  final class Parser {

    private final @NotNull String value;
    private final char @NotNull [] chars;
    private final int len;
    private int i;

    public Parser(@NotNull String value) {
      Validate.Arg.notNull(value, "value");
      this.value = value;
      this.chars = this.value.toCharArray();
      this.len = this.chars.length;
    }

    public @NotNull String getValue() {
      return this.value;
    }

    public char @NotNull [] getChars() {
      return this.chars;
    }

    public int getLen() {
      return this.len;
    }

    public int getI() {
      return this.i;
    }

    public void setI(int var1) {
      this.i = var1;
    }

    public @NotNull NumberMatcher parse() {
      String symbol = checkSymbol();
      if (symbol != null) {
        AnyNumber mun = checkNumber();
        if (mun == null) {
          throw new IllegalArgumentException("Expected a number after comparison symbol in " + this.value);
        } else {
          return symbolToMatcher(symbol, mun, NumberMatcher.Parser.Side.RIGHT_HAND);
        }
      } else {
        AnyNumber number = checkNumber();
        if (number != null) {
          if (chars[i] == ',') {
            return new Multiple(
                Strings.split(this.value, ',', true).stream().map(AnyNumber::of).toArray(AnyNumber[]::new));
          } else {
            String firstSym = checkSymbol();
            if (firstSym == null) {
              throw new IllegalArgumentException(
                  "Cant find first symbol for ternary number comparison in " + this.value);
            } else if (i >= len) {
              return this.symbolToMatcher(firstSym, number, NumberMatcher.Parser.Side.LEFT_HAND);
            } else {
              int secSym = i++;
              char x = chars[secSym];
              if (x != 'x') {
                throw new IllegalArgumentException(
                    "Variable 'x' is not used (instead '" + x + "') for ternary number comparison in " + this.value);
              } else {
                String secSymb = this.checkSymbol();
                if (secSymb == null) {
                  throw new IllegalArgumentException(
                      "Cant find second symbol for ternary number comparison in " + this.value);
                } else {
                  AnyNumber secNum = this.checkNumber();
                  if (secNum == null) {
                    throw new IllegalArgumentException(
                        "Cant find end number for ternary number comparison in " + this.value);
                  } else {
                    return new Range(
                        this.symbolToMatcher(firstSym, number, NumberMatcher.Parser.Side.LEFT_HAND),
                        this.symbolToMatcher(secSymb, secNum, NumberMatcher.Parser.Side.RIGHT_HAND)
                    );
                  }
                }
              }
            }
          }
        } else {
          throw new IllegalArgumentException("Invalid number matcher format: " + this.value);
        }
      }
    }

    private @NotNull NumberMatcher symbolToMatcher(@NotNull String symbol, @NotNull AnyNumber num, @NotNull Side numSide) {
      return switch (symbol) {
        case ">" -> switch (numSide) {
          case RIGHT_HAND -> new LessThan(num);
          case LEFT_HAND -> new GreaterThan(num);
        };
        case ">=" -> switch (numSide) {
          case RIGHT_HAND -> new LessThanOrEqual(num);
          case LEFT_HAND -> new GreaterThanOrEqual(num);
        };
        case "<" -> switch (numSide) {
          case RIGHT_HAND -> new GreaterThan(num);
          case LEFT_HAND -> new LessThan(num);
        };
        case "<=" -> switch (numSide) {
          case RIGHT_HAND -> new GreaterThanOrEqual(num);
          case LEFT_HAND -> new LessThanOrEqual(num);
        };
        default ->
            throw new IllegalArgumentException("Unknown number comparison symbol '" + symbol + "' in " + this.value);
      };
    }

    private @Nullable String checkSymbol() {
      if (i >= len)
        throw new IllegalArgumentException(
            "Expected a number comparison symbol at " + i + " but reached the end in " + value);

      char firstChar = chars[i];
      Character firstSym = this.symbolOrNull(firstChar);
      if (firstSym == null) {
        return null;
      } else if (i + 1 < len) {
        char secChar = chars[i + 1];
        Character secSym = this.symbolOrNull(secChar);
        if (secSym == null) {
          this.i++;
          return firstSym.toString();
        } else {
          this.i += 2;
          return "" + firstSym + secSym;
        }
      } else {
        this.i++;
        return firstSym.toString();
      }
    }

    private @Nullable Character symbolOrNull(char ch) {
      return switch (ch) {
        case '<', '=', '>' -> ch;
        default -> null;
      };
    }

    private @Nullable AnyNumber checkNumber() {
      char var1 = '\u0000';
      StringBuilder number = new StringBuilder(10);
      int i = this.i;
      boolean sawSign = false;
      boolean sawDecimal = false;

      while (i < this.len) {
        var1 = this.chars[i++];
        if (var1 == '-') {
          if (sawSign) {
            return null;
          }

          sawSign = true;
          number.append(var1);
        } else if (var1 == '.') {
          if (sawDecimal) {
            return null;
          }

          sawDecimal = true;
          number.append(var1);
        } else {
          if (!('0' <= var1 && var1 < ':')) {
            if (number.isEmpty()) {
              return null;
            }

            --i;
            this.i = i;
            return AnyNumber.of(number.toString());
          }

          number.append(var1);
        }
      }

      if (number.isEmpty()) {
        return null;
      } else {
        this.i = i;
        return AnyNumber.of(number.toString());
      }
    }

    private enum Side {
      RIGHT_HAND, LEFT_HAND
    }

  }

}
