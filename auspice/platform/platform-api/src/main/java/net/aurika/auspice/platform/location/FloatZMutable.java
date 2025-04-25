package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 浮点 Z 轴可变.
 */
public interface FloatZMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull FloatZMutable floatZ(double z);

}
