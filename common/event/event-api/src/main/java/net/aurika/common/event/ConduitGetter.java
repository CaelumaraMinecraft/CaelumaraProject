package net.aurika.common.event;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConduitGetter {

  @Language(
      value = "JAVA",
      prefix = "class X { public static net.aurika.common.event.Conduit<?> ",
      suffix = "() { } }"
  )
  String DEFAULT_VALUE = "emitter";

  @Language(
      value = "JAVA",
      prefix = "class X { public static net.aurika.common.event.Conduit<?> ",
      suffix = "() { } }"
  )
  String value() default ConduitGetter.DEFAULT_VALUE;

}
