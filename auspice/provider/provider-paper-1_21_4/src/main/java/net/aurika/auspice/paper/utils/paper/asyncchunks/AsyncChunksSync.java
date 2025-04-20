package net.aurika.auspice.paper.utils.paper.asyncchunks;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

final class AsyncChunksSync extends AsyncChunks {

  @Override
  public @NotNull CompletableFuture<Chunk> getChunkAtAsync(@NotNull World world, int x, int z, boolean gen, boolean isUrgent) {
    return CompletableFuture.completedFuture(world.getChunkAt(x, z));
  }

}
