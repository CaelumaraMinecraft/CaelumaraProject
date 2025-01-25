package net.aurika.annotations.data;

import java.lang.annotation.*;

/**
 * 标识一个不可变类, 类的内容
 * @author Attaccer
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
@Documented
public @interface Immutable {
}
