package net.aurika.common.keyed.annotation;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.*;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KeyedBy {

  /**
   * The key getter method name.
   *
   * @return the getter method name
   */
  @Language(value = "JAVA", prefix = "class Keyed { public Key ", suffix = "(); }")
  String value();

}
