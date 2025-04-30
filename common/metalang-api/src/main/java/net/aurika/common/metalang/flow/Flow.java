package net.aurika.common.metalang.flow;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface Flow {  // TODO

  /**
   * Declares variable pool for this flow
   *
   * @return the variable pool
   */
  Variables variables();

  VariableGet[] gets() default {};

  VariableSet[] sets() default {};

  Invoke[] invokes() default {};

  Construct[] constructs() default {};

  Action[] mapping();

  enum Action {
    GET, SET, INVOKE, CONSTRUCT
  }

}
