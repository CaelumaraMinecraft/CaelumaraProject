package net.aurika.common.annotation.calltime;

import java.lang.annotation.*;

/**
 * 代表了在什么时候才应该调用这个API
 * <p>
 * It represents when calling this API can achieve its effect
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface CallWhen {

  String value();

}
