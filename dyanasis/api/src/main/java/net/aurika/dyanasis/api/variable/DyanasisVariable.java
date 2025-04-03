package net.aurika.dyanasis.api.variable;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/**
 * A dyanasis variable.
 * 这是一个广义的定义, "变量"不仅至在方法定义内部的局部变量, 还指在一个文件中能访问到的所有作为变量的符号, 如在类定义内部能访问到这个类定义的属性.
 */
@ApiStatus.Experimental
public interface DyanasisVariable {

  /**
   * Gets the value of this dyanasis variable.
   *
   * @return the value
   */
  @NotNull DyanasisObject value();

}
