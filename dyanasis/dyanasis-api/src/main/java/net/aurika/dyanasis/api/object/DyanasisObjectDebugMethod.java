package net.aurika.dyanasis.api.object;

import java.lang.annotation.*;

/**
 * 表示这个方法不用于配置文本.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface DyanasisObjectDebugMethod {
}
