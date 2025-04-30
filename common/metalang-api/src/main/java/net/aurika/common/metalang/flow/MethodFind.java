package net.aurika.common.metalang.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface MethodFind {

  String name();

  ExecutableParameters params() default @ExecutableParameters;

  Class<?> inClass();

}
