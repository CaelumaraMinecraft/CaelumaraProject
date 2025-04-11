package net.aurika.common.annotation;

import java.lang.annotation.*;

/**
 * Mark a getter method.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Getter {

  boolean isRawValue() default true;

}
