package net.aurika.config.validation;

import java.lang.annotation.*;

/**
 * 代表了这个 {@linkplain ConfigValidator} 的实现将会验证出来并缓存的类型
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheType {
    Class<?>[] value();
}
