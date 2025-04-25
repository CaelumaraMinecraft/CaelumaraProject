package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GridYMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull GridYMutable gridY(int y);

}
