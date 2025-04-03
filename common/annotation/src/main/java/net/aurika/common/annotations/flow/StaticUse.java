package net.aurika.common.annotations.flow;

import java.lang.annotation.*;

/**
 * 被注解的类的实现创建静态对象使用.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface StaticUse {
}
