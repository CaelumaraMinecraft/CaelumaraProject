package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 浮点 X 轴可变.
 */
public interface FloatXMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull FloatXMutable floatX(double x);

}
