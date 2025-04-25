package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Grid3PosMutable extends Grid3Pos, GridXMutable, GridYMutable, GridZMutable {

  @Contract(value = "_, _, _ -> new", pure = true)
  static @NotNull Grid3PosMutable grid3PosMutable(int x, int y, int z) {
    return new Grid3PosMutableImpl(x, y, z);
  }

  @Contract(value = " -> new", pure = true)
  static @NotNull Grid3PosMutable zeroGrid3PosMutable() {
    return grid3PosMutable(0, 0, 0);
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3Pos add(@NotNull Grid3Pos other);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3Pos subtract(@NotNull Grid3Pos other);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3Pos multiply(@NotNull Grid3Pos other);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3Pos divide(@NotNull Grid3Pos other);

  @Override
  int gridX();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3PosMutable gridX(int x);

  @Override
  int gridY();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3PosMutable gridY(int y);

  @Override
  int gridZ();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Grid3PosMutable gridZ(int z);

}

final class Grid3PosMutableImpl implements Grid3PosMutable {

  private int x;
  private int y;
  private int z;

  Grid3PosMutableImpl(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Grid3Pos add(@NotNull Grid3Pos other) {
    this.x += other.gridX();
    this.y += other.gridY();
    this.z += other.gridZ();
    return this;
  }

  @Override
  public @NotNull Grid3Pos subtract(@NotNull Grid3Pos other) {
    this.x -= other.gridX();
    this.y -= other.gridY();
    this.z -= other.gridZ();
    return this;
  }

  @Override
  public @NotNull Grid3Pos multiply(@NotNull Grid3Pos other) {
    this.x *= other.gridX();
    this.y *= other.gridY();
    this.z *= other.gridZ();
    return this;
  }

  @Override
  public @NotNull Grid3Pos divide(@NotNull Grid3Pos other) {
    this.x /= other.gridX();
    this.y /= other.gridY();
    this.z /= other.gridZ();
    return this;
  }

  @Override
  public int gridX() { return x; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull Grid3PosMutable gridX(int x) {
    this.x = x;
    return this;
  }

  @Override
  public int gridY() { return y; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull Grid3PosMutable gridY(int y) {
    this.y = y;
    return this;
  }

  @Override
  public int gridZ() { return z; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull Grid3PosMutable gridZ(int z) {
    this.z = z;
    return this;
  }

}
