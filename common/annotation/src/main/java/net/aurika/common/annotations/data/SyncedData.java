package net.aurika.common.annotations.data;

import java.lang.annotation.*;

/**
 * 被这个注解所注解的值中包含的值是全局同步的
 * <p>
 * 这个注解用于注解方法的返回值或形参, 被注解的类需要是一个容器类 (如 {@link java.util.Map}), 当这个容器类实例中的内容被修改时, 这个容器类的使用也应同步更新.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface SyncedData {
}
