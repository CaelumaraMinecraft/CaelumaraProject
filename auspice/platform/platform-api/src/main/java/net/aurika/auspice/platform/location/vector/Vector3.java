package net.aurika.auspice.platform.location.vector;

import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.kyori.examination.Examinable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static net.aurika.auspice.platform.location.vector.InternalUtil.square;

/**
 * A three-axis vector.
 */
public interface Vector3 extends VectorXAware, VectorYAware, VectorZAware, Examinable {

  @Contract(value = "_, _, _ -> new", pure = true)
  @ExaminableConstructor(publicType = Vector3.class, properties = {VAL_X, VAL_Y, VAL_Z})
  static @NotNull Vector3 vector3(double x, double y, double z) {
    return new Vector3Impl(x, y, z);
  }

  static @NotNull Vector3 zero() { return Vector3Impl.ZERO; }

  @Contract(value = "_ -> new", pure = true)
  @NotNull Vector3 add(@NotNull Vector3 other);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Vector3 subtract(@NotNull Vector3 other);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Vector3 multiply(@NotNull Vector3 other);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Vector3 divide(@NotNull Vector3 other);

  /**
   * Gets the length of the vector, defined as sqrt(x^2+y^2+z^2). The
   * value of this method is not cached and uses a costly square-root
   * function, so do not repeatedly call this method to get the vector's
   * magnitude. NaN will be returned if the inner result of the sqrt()
   * function overflows, which will be caused if the length is too long.
   *
   * @return the magnitude
   */
  default double length() {
    return Math.sqrt(lengthSquared());
  }

  /**
   * Gets the magnitude of the vector squared.
   *
   * @return the magnitude
   */
  default double lengthSquared() {
    return square(vectorX()) + square(vectorY()) + square(vectorZ());
  }

  /**
   * Gets the distance between this vector and another. The value of this
   * method is not cached and uses a costly square-root function, so do not
   * repeatedly call this method to get the vector's magnitude. NaN will be
   * returned if the inner result of the sqrt() function overflows, which
   * will be caused if the distance is too long.
   *
   * @param other The other vector
   * @return the distance
   */
  default double distance(@NotNull Vector3 other) {
    return Math.sqrt(distanceSquared(other));
  }

  /**
   * Get the squared distance between this vector and another.
   *
   * @param other The other vector
   * @return the distance
   */
  default double distanceSquared(@NotNull Vector3 other) {
    return square(vectorX() - other.vectorX())
        + square(vectorY() - other.vectorY())
        + square(vectorZ() - other.vectorZ());
  }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  double vectorX();

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  double vectorY();

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  double vectorZ();

}

final class Vector3Impl implements Vector3 {

  static final Vector3Impl ZERO = new Vector3Impl(0.0, 0.0, 0.0);

  private final double x;
  private final double y;
  private final double z;

  Vector3Impl(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Vector3 add(@NotNull Vector3 other) {
    return new Vector3Impl(this.x + other.vectorX(), this.y + other.vectorY(), this.z + other.vectorZ());
  }

  @Override
  public @NotNull Vector3 subtract(@NotNull Vector3 other) {
    return new Vector3Impl(this.x - other.vectorX(), this.y - other.vectorY(), this.z - other.vectorZ());
  }

  @Override
  public @NotNull Vector3 multiply(@NotNull Vector3 other) {
    return new Vector3Impl(this.x * other.vectorX(), this.y * other.vectorY(), this.z * other.vectorZ());
  }

  @Override
  public @NotNull Vector3 divide(@NotNull Vector3 other) {
    return new Vector3Impl(this.x / other.vectorX(), this.y / other.vectorY(), this.z / other.vectorZ());
  }

  @Override
  public double vectorX() { return x; }

  @Override
  public double vectorY() { return y; }

  @Override
  public double vectorZ() { return z; }

}
