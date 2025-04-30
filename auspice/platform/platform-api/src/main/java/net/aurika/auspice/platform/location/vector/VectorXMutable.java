package net.aurika.auspice.platform.location.vector;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface VectorXMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull VectorYMutable vectorX(double x);

}
