package net.aurika.common.annotations.data;

import java.lang.annotation.*;

/**
 * 标识一个不可变类
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Immutable {
}
