package net.aurika.common.annotation;

import java.lang.annotation.*;

/**
 * Mark a setter method.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Setter {

  boolean isRawValue() default true;

}
