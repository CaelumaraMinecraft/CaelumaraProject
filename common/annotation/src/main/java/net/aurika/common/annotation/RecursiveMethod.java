package net.aurika.common.annotation;

import java.lang.annotation.*;

/**
 * 代表所注解的方法是一个递归调用的方法.
 */
@Deprecated
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
@Documented
@interface RecursiveMethod { }
