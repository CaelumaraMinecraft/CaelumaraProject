package top.mckingdom.auspice.util;

import org.intellij.lang.annotations.Language;
import org.intellij.lang.annotations.Pattern;
import org.kingdoms.locale.placeholders.KingdomsPlaceholderTranslator;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;

public final class KingdomsNamingContract {
  private KingdomsNamingContract() {
    throw new AssertionError("Can not initialize class " + getClass().getName());
  }

  @Language("RegExp")
  public static final String NAMESPACE_GROUP = "[A-Za-z]{3,20}";
  @Language("RegExp")
  public static final String NAMESPACE_KEY = "[A-Z0-9_]{3,100}";
  @Language("RegExp")
  public static final String NAMESPACE = "[A-Za-z]{3,20}:[A-Z0-9_]{3,100}";
  @Language("RegExp")
  public static final String COMMAND_NAME = "[a-zA-Z0-9]+";
  @Language("RegExp")
  public static final String PLACEHOLDER_TRANSLATOR_NAME = KingdomsPlaceholderTranslator.NAME_PATTERN; // "[a-z0-9]"

  /**
   * {@linkplain org.kingdoms.constants.namespace.Namespace(String, String)}
   */
  @Documented
  @Pattern(NAMESPACE)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface Namespace {
    @Documented
    @Pattern(NAMESPACE_GROUP)
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    @interface Group {
    }

    @Documented
    @Pattern(NAMESPACE_KEY)
    @Retention(RetentionPolicy.CLASS)
    @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
    @interface Key {
    }

  }

  /**
   * {@linkplain KingdomsPlaceholderTranslator#NAME_PATTERN}
   */
  @Documented
  @Pattern(PLACEHOLDER_TRANSLATOR_NAME)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface PlaceholderName {
  }

  @Documented
  @Pattern(COMMAND_NAME)
  @Retention(CLASS)
  @Target({METHOD, FIELD, PARAMETER, LOCAL_VARIABLE, ANNOTATION_TYPE})
  public @interface CommandName {
  }

}
