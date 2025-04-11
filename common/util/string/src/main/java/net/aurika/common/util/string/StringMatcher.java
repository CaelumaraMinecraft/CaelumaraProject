package net.aurika.common.util.string;

import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;

public interface StringMatcher {

  boolean matches(String var1);

  static StringMatcher group(Collection<StringMatcher> matchers) {
    return StringMatcher.Aggregate.aggregate(matchers.toArray(new StringMatcher[0]));
  }

  static StringMatcher parseAndGroup(Collection<String> matchers) {
    return StringMatcher.Aggregate.aggregate(
        matchers.stream().map(StringMatcher::fromString).toArray(StringMatcher[]::new));
  }

  static StringMatcher fromString(String text) {
    Objects.requireNonNull(text, "Cannot construct checker from null text");
    if (text.equals("*")) {
      return StringMatcher.Constant.TRUE;
    } else {
      int handlerIndexEnd = text.indexOf(':');
      if (handlerIndexEnd == -1) {
        return new Exact(text);
      } else {
        String handlerName = text.substring(0, handlerIndexEnd);
        String realText = text.substring(handlerIndexEnd + 1);
        return switch (handlerName) {
          case "CONTAINS" -> new Contains(realText);
          case "STARTS" -> new StartsWith(realText);
          case "ENDS" -> new EndsWith(realText);
          case "REGEX" -> new Regex(Pattern.compile(realText));
          default -> new Exact(text);
        };
      }
    }
  }

  final class Aggregate implements StringMatcher {

    private final StringMatcher[] matchers;

    private Aggregate(StringMatcher[] matchers) {
      this.matchers = matchers;
    }

    private static StringMatcher aggregate(StringMatcher[] matchers) {
      return matchers.length == 0 ? Constant.FALSE : new Aggregate(matchers);
    }

    public boolean matches(String string) {

      for (StringMatcher matcher : this.matchers) {
        if (matcher.matches(string)) {
          return true;
        }
      }

      return false;
    }

  }

  final class Constant implements StringMatcher {

    private static final Constant TRUE = new Constant(true);
    private static final Constant FALSE = new Constant(false);
    private final boolean constant;

    private Constant(boolean constant) {
      this.constant = constant;
    }

    public boolean matches(String string) {
      return this.constant;
    }

  }

  final class Exact implements StringMatcher {

    private final String exact;

    private Exact(String exact) {
      this.exact = exact;
    }

    public boolean matches(String string) {
      return this.exact.equals(string);
    }

  }

  final class Contains implements StringMatcher {

    private final String contains;

    private Contains(String contains) {
      this.contains = contains;
    }

    public boolean matches(String string) {
      return string.contains(this.contains);
    }

  }

  final class StartsWith implements StringMatcher {

    private final String startsWith;

    private StartsWith(String startsWith) {
      this.startsWith = startsWith;
    }

    public boolean matches(String string) {
      return string.endsWith(this.startsWith);
    }

  }

  final class EndsWith implements StringMatcher {

    private final String endsWith;

    private EndsWith(String endsWith) {
      this.endsWith = endsWith;
    }

    public boolean matches(String string) {
      return string.endsWith(this.endsWith);
    }

  }

  final class Regex implements StringMatcher {

    private final Pattern pattern;

    private Regex(Pattern pattern) {
      this.pattern = pattern;
    }

    public boolean matches(String string) {
      return this.pattern.matcher(string).matches();
    }

  }

}
