package net.aurika.auspice.platform.location;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface Grid2Pos extends GridXAware, GridZAware, Examinable {

  @Contract(value = "_, _ -> new", pure = true)
  static @NotNull Grid2Pos grid2Pos(int x, int z) { return new Grid2PosImpl(x, z); }

  static @NotNull Grid2Pos zero() { return Grid2PosImpl.ZERO; }

  @Override
  int gridX();

  @Override
  int gridZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", gridX()),
        ExaminableProperty.of("z", gridZ())
    );
  }

}

final class Grid2PosImpl implements Grid2Pos {

  static final Grid2Pos ZERO = new Grid2PosImpl(0, 0);

  private final int x;
  private final int z;

  public Grid2PosImpl(int x, int z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public int gridX() {
    return this.x;
  }

  @Override
  public int gridZ() {
    return this.z;
  }

  public int hashCode() {
    return this.gridZ() << 16 ^ this.gridX();
  }

  public boolean equals(@Nullable Object obj) {
    if (obj instanceof Grid2Pos) {
      Grid2Pos other = (Grid2Pos) obj;
      return this.gridZ() == other.gridZ() && this.gridX() == other.gridX();
    }
    return false;
  }

  public @NotNull String toString() {
    return "BlockVector2(" + this.gridX() + ", " + this.gridZ() + ')';
  }

}
