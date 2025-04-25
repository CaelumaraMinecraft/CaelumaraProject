package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GridZMutable {

  @Contract(value = "_ -> this", mutates = "this")
  @NotNull GridZMutable gridZ(int z);

}
