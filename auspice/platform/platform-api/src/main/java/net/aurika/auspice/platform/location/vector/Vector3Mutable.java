package net.aurika.auspice.platform.location.vector;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Vector3Mutable extends Vector3, VectorXMutable, VectorYMutable, VectorZMutable {

  static @NotNull Vector3Mutable vector3DMutable(double x, double y, double z) {
    return new Vector3MutableImpl(x, y, z);
  }

  @Contract(value = " -> new", pure = true)
  static @NotNull Vector3Mutable zero() {
    return vector3DMutable(0, 0, 0);
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3 add(@NotNull Vector3 other);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3 subtract(@NotNull Vector3 other);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3 multiply(@NotNull Vector3 other);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3 divide(@NotNull Vector3 other);

  @Override
  double vectorX();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3Mutable vectorX(double x);

  @Override
  double vectorY();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3Mutable vectorY(double y);

  @Override
  double vectorZ();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull Vector3Mutable vectorZ(double z);

}

final class Vector3MutableImpl implements Vector3Mutable {

  private double x;
  private double y;
  private double z;

  Vector3MutableImpl(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Vector3 add(@NotNull Vector3 other) {
    this.x += other.vectorX();
    this.y += other.vectorY();
    this.z += other.vectorZ();
    return this;
  }

  @Override
  public @NotNull Vector3 subtract(@NotNull Vector3 other) {
    this.x -= other.vectorX();
    this.y -= other.vectorY();
    this.z -= other.vectorZ();
    return this;
  }

  @Override
  public @NotNull Vector3 multiply(@NotNull Vector3 other) {
    this.x *= other.vectorX();
    this.y *= other.vectorY();
    this.z *= other.vectorZ();
    return this;
  }

  @Override
  public @NotNull Vector3 divide(@NotNull Vector3 other) {
    this.x /= other.vectorX();
    this.y /= other.vectorY();
    this.z /= other.vectorZ();
    return this;
  }

  @Override
  public double vectorX() {
    return x;
  }

  @Override
  public @NotNull Vector3Mutable vectorX(double x) {
    this.x = x;
    return this;
  }

  @Override
  public double vectorY() {
    return y;
  }

  @Override
  public @NotNull Vector3Mutable vectorY(double y) {
    this.y = y;
    return this;
  }

  @Override
  public double vectorZ() {
    return z;
  }

  @Override
  public @NotNull Vector3Mutable vectorZ(double z) {
    this.z = z;
    return this;
  }

}
