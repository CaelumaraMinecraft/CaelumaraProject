package net.aurika.common.metalang.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Constant {

  Type type();

  boolean booleanConst() default false;

  char charConst() default '\0';

  byte byteConst() default 0;

  short shortConst() default 0;

  int intConst() default 0;

  long longConst() default 0;

  float floatConst() default 0;

  double doubleConst() default 0;

  String stringConst() default "";

  String enumConstName() default "";

  Class<? extends Enum> enumConstClass() default Enum.class;

  Class<?> classConst() default void.class;

  enum Type {
    NULL, BOOLEAN, CHAR, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, STRING, ENUM, CLASS
  }

}
