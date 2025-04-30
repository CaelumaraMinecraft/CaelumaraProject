package net.aurika.auspice.platform.location.direction;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 一个对象的 pitch 值可以被修改
 */
public interface PitchMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull PitchMutable pitch(float pitch);

}
