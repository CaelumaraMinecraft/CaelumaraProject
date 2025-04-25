package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface VectorZMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull VectorZMutable vectorZ(double z);

}
