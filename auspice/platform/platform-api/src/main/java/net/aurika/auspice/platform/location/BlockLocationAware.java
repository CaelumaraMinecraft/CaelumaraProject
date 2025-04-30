package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import org.jetbrains.annotations.NotNull;

public interface BlockLocationAware extends WorldAware {

  String VAL_BLOCK$LOCATION = "blockLocation";

  @Override
  @NotNull World world();

  int blockX();

  int blockY();

  int blockZ();

}
