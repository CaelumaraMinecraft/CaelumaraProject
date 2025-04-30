package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import org.jetbrains.annotations.NotNull;

public interface ChunkLocationAware extends WorldAware {

  String VAL_CHUNK$LOCATION = "chunkLocation";

  @Override
  @NotNull World world();

  int chunkX();

  int chunkZ();

}
