package net.aurika.auspice.paper.utils.paper.asyncchunks;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

final class AsyncChunksPaper_13 extends AsyncChunks {

  @Override
  public CompletableFuture<Chunk> getChunkAtAsync(@NotNull World world, int x, int z, boolean gen, boolean isUrgent) {
    return world.getChunkAtAsync(x, z, gen);
  }

}
