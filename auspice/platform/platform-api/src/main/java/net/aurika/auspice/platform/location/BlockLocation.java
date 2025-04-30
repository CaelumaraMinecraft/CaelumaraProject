package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.location.grid.Grid3Location;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.annotation.data.Immutable;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.LocationProperties.*;

@Immutable
public interface BlockLocation extends Grid3Location, BlockLocationAware, Examinable {

  @Contract("_ -> new")
  static @NotNull BlockLocation blockLocation(@NotNull BlockLocationAware block) {
    return blockLocation(block.world(), block.blockX(), block.blockY(), block.blockZ());
  }

  @Contract("_, _, _, _ -> new")
  @ExaminableConstructor(publicType = BlockLocation.class, properties = {VAL_WORLD, VAL_X, VAL_Y, VAL_Z})
  static @NotNull BlockLocation blockLocation(@NotNull World world, int x, int y, int z) {
    return new BlockLocationImpl(world, x, y, z);
  }

  @Override
  @ExaminablePropertyGetter(VAL_WORLD)
  @NotNull World world();

  @Override
  @ApiStatus.NonExtendable
  default int gridX() { return blockX(); }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  int blockX();

  @Override
  @ApiStatus.NonExtendable
  default int gridY() { return blockY(); }

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  int blockY();

  @Override
  @ApiStatus.NonExtendable
  default int gridZ() { return blockZ(); }

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  int blockZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Grid3Location.super.examinableProperties();
  }

}

final class BlockLocationImpl implements BlockLocation {

  private final @NotNull World world;
  private final int x;
  private final int y;
  private final int z;

  BlockLocationImpl(@NotNull World world, int x, int y, int z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull World world() { return this.world; }

  @Override
  public int blockX() { return x; }

  @Override
  public int blockY() { return y; }

  @Override
  public int blockZ() { return z; }

}
