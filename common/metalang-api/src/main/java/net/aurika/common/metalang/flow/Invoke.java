package net.aurika.common.metalang.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static net.aurika.common.metalang.MetalangObject.VAR_NULL;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Invoke {

  MethodFind find();

  /**
   * Arguments.
   */
  ExecutableArguments args() default @ExecutableArguments;

  /**
   * Whether static method invoking.
   */
  boolean isStatic() default true;

  /**
   * The method object.
   */
  VariableGet obj() default @VariableGet(value = VAR_NULL);

}
