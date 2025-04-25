package net.aurika.auspice.platform.location;

import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

import static net.aurika.auspice.platform.location.InternalUtil.square;

/**
 * 网格 3d 坐标. X, Y, Z 均为整形.
 */
public interface Grid3Pos extends GridXAware, GridYAware, GridZAware, Examinable {

  String VAL_X = "x";
  String VAL_Y = "y";
  String VAL_Z = "z";

  @Contract(value = "_, _, _ -> new", pure = true)
  @ExaminableConstructor(publicType = Grid3Pos.class, properties = {VAL_X, VAL_Y, VAL_Z})
  static @NotNull Grid3Pos grid3Pos(int x, int y, int z) { return new Grid3PosImpl(x, y, z); }

  static @NotNull Grid3Pos zero() {
    return Grid3PosImpl.ZERO;
  }

  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Pos add(@NotNull Grid3Pos other);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Pos subtract(@NotNull Grid3Pos other);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Pos multiply(@NotNull Grid3Pos other);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Grid3Pos divide(@NotNull Grid3Pos other);

  /**
   * 与另外一个 {@link Grid3Pos} 的距离.
   *
   * @return 距离
   */
  default double distance(@NotNull Grid3Pos other) {
    return Math.sqrt(distanceSquared(other));
  }

  /**
   * 与另外一个 {@link Grid3Pos} 的距离的三次方.
   *
   * @return 距离
   */
  default double distanceSquared(@NotNull Grid3Pos other) {
    return square(gridX() - other.gridX()) + square(gridY() - other.gridY()) + square(gridZ() - other.gridZ());
  }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  int gridX();

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  int gridY();

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  int gridZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", gridX()), //
        ExaminableProperty.of("y", gridY()), //
        ExaminableProperty.of("z", gridZ())  //
    );
  }

}

final class Grid3PosImpl implements Grid3Pos {

  static final Grid3Pos ZERO = new Grid3PosImpl(0, 0, 0);

  private final int x;
  private final int y;
  private final int z;

  Grid3PosImpl(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Grid3Pos add(@NotNull Grid3Pos other) {
    return new Grid3PosImpl(x + other.gridX(), y + other.gridY(), z + other.gridZ());
  }

  @Override
  public @NotNull Grid3Pos subtract(@NotNull Grid3Pos other) {
    return new Grid3PosImpl(x - other.gridX(), y - other.gridY(), z - other.gridZ());
  }

  @Override
  public @NotNull Grid3Pos multiply(@NotNull Grid3Pos other) {
    return new Grid3PosImpl(x * other.gridX(), y * other.gridY(), z * other.gridZ());
  }

  @Override
  public @NotNull Grid3Pos divide(@NotNull Grid3Pos other) {
    return new Grid3PosImpl(x / other.gridX(), y / other.gridY(), z / other.gridZ());
  }

  @Override
  public int gridX() { return this.x; }

  @Override
  public int gridY() { return this.y; }

  @Override
  public int gridZ() { return this.z; }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Grid3Pos)) return false;
    Grid3Pos grid3Pos = (Grid3Pos) obj;
    return x == grid3Pos.gridX() && y == grid3Pos.gridY() && z == grid3Pos.gridZ();
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}


