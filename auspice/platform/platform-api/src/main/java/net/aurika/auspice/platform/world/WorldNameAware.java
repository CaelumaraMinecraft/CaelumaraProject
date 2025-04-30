package net.aurika.auspice.platform.world;

import org.jetbrains.annotations.NotNull;

public interface WorldNameAware {

  String VAL_WORLD$NAME = "worldName";

  @NotNull String worldName();

}
