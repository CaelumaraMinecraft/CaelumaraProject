package net.aurika.dyanasis.api;

import net.aurika.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;
import java.util.regex.Pattern;

public final class NamingContract {

  public static boolean isValidNormalInvokableName(@NotNull String name) {
    Validate.Arg.notNull(name, "name");
    if (name.isEmpty()) {     // check length
      return false;
    }
    if (!isValidNormalInvokableChar(name.charAt(0), true)) {  // check head
      return false;
    }
    boolean valid = true;
    for (int i = 1; i < name.length(); i++) {
      char c = name.charAt(i);
      if (!isValidNormalInvokableChar(c, false)) {
        valid = false;
        break;
      }
    }
    return valid;
  }

  /**
   * Checks a char is a valid normal invokable name char.
   *
   * @param c      the char to validate
   * @param onHead the char is on the head
   * @return is valid
   */
  public static boolean isValidNormalInvokableChar(char c, boolean onHead) {
    if ('a' <= c && c <= 'z') {
      return true;
    }
    if (!onHead) {
      return ('A' <= c && c <= 'Z') || ('0' <= c && c <= '9') || (c == '_');
    }
    return false;
  }

  public static boolean isValidNamespaceName(@NotNull String str) {
    Validate.Arg.notNull(str, "str");
    return NAMESPACE_PATTERN.matcher(str).matches();
  }

  @Language("RegExp")
  public static final String ALLOWED_NAMESPACE = "[a-z]";
  public static final Pattern NAMESPACE_PATTERN = Pattern.compile(ALLOWED_NAMESPACE);

  @Language("RegExp")
  public static final String ALLOWED_INVOKABLE = "[a-z]+[a-zA-Z0-9_]";
  public static final Pattern INVOKABLE_PATTERN = Pattern.compile(ALLOWED_INVOKABLE);

  @Documented
  @org.intellij.lang.annotations.Pattern(ALLOWED_NAMESPACE)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
  public @interface Namespace {
  }

  @Documented
  @org.intellij.lang.annotations.Pattern(ALLOWED_INVOKABLE)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
  public @interface Invokable {
  }

}
