package net.aurika.common.event;

import java.lang.annotation.*;

/**
 * 注解一个方法用来重新放置一个事件的 Emitter.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EmitterReplaceMethod { }
