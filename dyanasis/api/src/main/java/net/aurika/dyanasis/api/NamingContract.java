package net.aurika.dyanasis.api;

import net.aurika.common.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.*;

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
    return NAMESPACE_REGEX.matcher(str).matches();
  }

  @Language("RegExp")
  public static final String NAMESPACE_PATTERN = "[a-z]";
  public static final java.util.regex.Pattern NAMESPACE_REGEX = java.util.regex.Pattern.compile(NAMESPACE_PATTERN);

  @Language("RegExp")
  public static final String MEMBER_PATTERN = "[a-z]+[a-zA-Z0-9_]";
  public static final java.util.regex.Pattern MEMBER_REGEX = java.util.regex.Pattern.compile(MEMBER_PATTERN);

  @Documented
  @Pattern(NAMESPACE_PATTERN)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
  public @interface Namespace {
  }

  @Documented
  @Pattern(MEMBER_PATTERN)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
  public @interface Member {
  }

}
