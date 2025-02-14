package net.aurika.annotations.data;

import java.lang.annotation.*;

/**
 * 标识一个不可变类
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Immutable {
}
