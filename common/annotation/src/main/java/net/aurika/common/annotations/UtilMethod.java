package net.aurika.common.annotations;

import java.lang.annotation.*;

/**
 * 代表被注释的方法仅仅为一个工具方法, 重写这个方法并不会对代码的运行造成影响
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface UtilMethod {
}
