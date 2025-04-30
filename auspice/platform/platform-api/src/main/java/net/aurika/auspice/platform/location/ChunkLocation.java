package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.location.grid.Grid2Location;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.annotation.data.Immutable;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.LocationProperties.*;

@Immutable
public interface ChunkLocation extends Grid2Location, ChunkLocationAware, Examinable {

  @Contract("_ -> new")
  static @NotNull ChunkLocation chunkLocation(@NotNull ChunkLocationAware chunk) {
    return chunkLocation(chunk.world(), chunk.chunkX(), chunk.chunkX());
  }

  @Contract("_, _, _ -> new")
  @ExaminableConstructor(publicType = ChunkLocation.class, properties = {VAL_WORLD, VAL_X, VAL_Z})
  static @NotNull ChunkLocation chunkLocation(@NotNull World world, int x, int z) {
    return new ChunkLocationImpl(world, x, z);
  }

  @Override
  @ExaminablePropertyGetter(VAL_WORLD)
  @NotNull World world();

  @Override
  @ApiStatus.NonExtendable
  default int gridX() { return chunkX(); }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  int chunkX();

  @Override
  @ApiStatus.NonExtendable
  default int gridZ() { return chunkZ(); }

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  int chunkZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Grid2Location.super.examinableProperties();
  }

}

final class ChunkLocationImpl implements ChunkLocation {

  private final @NotNull World world;
  private final int x;
  private final int z;

  ChunkLocationImpl(@NotNull World world, int x, int z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.z = z;
  }

  @Override
  public @NotNull World world() { return world; }

  @Override
  public int chunkX() { return x; }

  @Override
  public int chunkZ() { return z; }

  @Override
  public int hashCode() {
    return Objects.hash(world, x, z);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ChunkLocation)) return false;
    ChunkLocation that = (ChunkLocation) obj;
    return x == that.chunkX() && z == that.chunkZ() && Objects.equals(world, that.world());
  }

  @Override
  public String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}
