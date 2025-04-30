package net.aurika.common.ident;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.*;

public final class IdentPatterns {

  @Language("RegExp")
  public static final String GROUP_PATTERN = "([a-z]+(?:\\.[a-z]+)*)";
  @Language("RegExp")
  public static final String KEY_PATH_PATTERN = "([a-zA-Z_]+(?:\\/[a-zA-Z_]+)*)";
  @Language("RegExp")
  public static final String KEY_PATTERN = "^([a-z]+(?:\\.[a-z]+)*)\\:([a-zA-Z_]+(?:\\/[a-zA-Z_]+)+$)";

  @Documented
  @Pattern(GROUP_PATTERN)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface Group {
  }

  @Documented
  @Pattern(KEY_PATH_PATTERN)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface IdentPath {
  }

  @Documented
  @Pattern(KEY_PATTERN)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface Ident {
  }

}
