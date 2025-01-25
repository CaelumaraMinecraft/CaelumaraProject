package net.aurika.annotations;

import java.lang.annotation.*;

/**
 * 代表所注解的方法是一个递归调用的方法.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Documented
public @interface RecursiveMethod {
}
