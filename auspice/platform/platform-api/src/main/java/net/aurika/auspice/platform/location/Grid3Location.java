package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.validate.Validate;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public interface Grid3Location extends WorldAware, Grid3Pos {

  @Contract("_, _, _, _ -> new")
  static @NotNull Grid3Location grid3Location(@NotNull World world, int x, int y, int z) {
    return new Grid3LocationImpl(world, x, y, z);
  }

  @Contract("_ -> new")
  static @NotNull Grid3Location zeroGrid3Location(@NotNull World world) {
    return grid3Location(world, 0, 0, 0);
  }

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Location add(@NotNull Grid3Pos other);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Location subtract(@NotNull Grid3Pos other);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Location multiply(@NotNull Grid3Pos other);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Location divide(@NotNull Grid3Pos other);

  @Override
  @NotNull World world();

  @Override
  int gridX();

  @Override
  int gridY();

  @Override
  int gridZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.concat(
        Stream.of(ExaminableProperty.of("world", world())),
        Grid3Pos.super.examinableProperties()
    );
  }

}

final class Grid3LocationImpl implements Grid3Location {

  private final @NotNull World world;
  private final int x;
  private final int y;
  private final int z;

  Grid3LocationImpl(@NotNull World world, int x, int y, int z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Grid3Location add(@NotNull Grid3Pos other) {
    return new Grid3LocationImpl(world, x + other.gridX(), y + other.gridY(), z + other.gridZ());
  }

  @Override
  public @NotNull Grid3Location subtract(@NotNull Grid3Pos other) {
    return new Grid3LocationImpl(world, x - other.gridX(), y + other.gridY(), z - other.gridZ());
  }

  @Override
  public @NotNull Grid3Location multiply(@NotNull Grid3Pos other) {
    return new Grid3LocationImpl(world, x * other.gridX(), y * other.gridY(), z * other.gridZ());
  }

  @Override
  public @NotNull Grid3Location divide(@NotNull Grid3Pos other) {
    return new Grid3LocationImpl(world, x / other.gridX(), y / other.gridY(), z / other.gridZ());
  }

  @Override
  public @NotNull World world() { return world; }

  @Override
  public int gridX() { return x; }

  @Override
  public int gridY() { return y; }

  @Override
  public int gridZ() { return z; }

}
