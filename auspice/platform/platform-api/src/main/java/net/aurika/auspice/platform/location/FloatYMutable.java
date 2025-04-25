package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 浮点 Y 轴可变.
 */
public interface FloatYMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull FloatYMutable floatY(double y);

}
