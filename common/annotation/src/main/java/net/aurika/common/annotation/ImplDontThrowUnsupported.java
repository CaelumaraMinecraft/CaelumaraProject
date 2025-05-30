package net.aurika.common.annotation;

import java.lang.annotation.*;

/**
 * 不要因为当前实现不支持而抛出 {@link UnsupportedOperationException} 异常
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ImplDontThrowUnsupported { }
