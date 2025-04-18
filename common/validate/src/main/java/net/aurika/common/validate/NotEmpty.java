package net.aurika.common.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Not an empty string.
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, LOCAL_VARIABLE, TYPE_USE})
public @interface NotEmpty {

  String message() default "";

}
