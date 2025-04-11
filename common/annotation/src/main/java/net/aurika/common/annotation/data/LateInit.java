package net.aurika.common.annotation.data;

import java.lang.annotation.*;

/**
 * 标志被注解的字段为延迟初始化.
 */
@Documented
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface LateInit {

  /**
   * 在获取这个 lazy 属性的时候通过何种方式初始化
   *
   * @return 初始化方式, 如 {@code throw}, {@code 某某默认值}
   */
  String by() default "throw";

}
