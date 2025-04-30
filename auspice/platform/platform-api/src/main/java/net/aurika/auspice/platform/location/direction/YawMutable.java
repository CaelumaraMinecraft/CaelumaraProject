package net.aurika.auspice.platform.location.direction;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface YawMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull YawMutable yaw(float yaw);

}
