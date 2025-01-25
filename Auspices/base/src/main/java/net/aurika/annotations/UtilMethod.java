package net.aurika.annotations;

import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.*;

/**
 * 代表被注释的方法仅仅为一个工具方法, 重写这个方法并不会对代码的运行造成影响
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
@ApiStatus.NonExtendable
public @interface UtilMethod {
}
