package net.aurika.common.key;

import org.intellij.lang.annotations.Pattern;

import java.lang.annotation.*;

import static net.aurika.common.key.IdentImpl.*;
import static net.aurika.common.key.KeyImpl.ALLOWED_KEY;
import static net.aurika.common.key.KeyImpl.ALLOWED_KEY_VALUE;
import static net.aurika.common.key.NamespacedImpl.ALLOWED_NAMESPACE;

public final class KeyPatterns {

  @Documented
  @Pattern(ALLOWED_NAMESPACE)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface Namespace {
  }

  @Documented
  @Pattern(ALLOWED_KEY_VALUE)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface KeyValue {
  }

  @Documented
  @Pattern(ALLOWED_IDENT_VALUE)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface IdentValue {
  }

  @Documented
  @Pattern(ALLOWED_IDENT_SECTION)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface IdentSection {
  }

  @Documented
  @Pattern(ALLOWED_KEY)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface Key {
  }

  @Documented
  @Pattern(ALLOWED_IDENT)
  @Retention(RetentionPolicy.CLASS)
  @Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD, ElementType.PARAMETER})
  public @interface Ident {
  }

}
