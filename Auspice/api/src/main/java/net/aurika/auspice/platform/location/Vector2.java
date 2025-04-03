package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Vector2 implements Point2D {

  private final double x;
  private final double z;

  public Vector2(double x, double z) {
    this.x = x;
    this.z = z;
  }

  public double getX() {
    return this.x;
  }

  public double getZ() {
    return this.z;
  }

  public int hashCode() {
    long bits = Double.doubleToLongBits(this.getX());
    bits ^= Double.doubleToLongBits(this.getZ()) * (long) 31;
    return (int) bits ^ (int) (bits >> 32);
  }

  public boolean equals(@Nullable Object obj) {
    Point2D other = obj instanceof Point2D ? (Point2D) obj : null;
    if ((obj instanceof Point2D ? (Point2D) obj : null) == null) {
      return false;
    } else {
      return this.getX() == other.getX() && this.getZ() == other.getZ();
    }
  }

  @NotNull
  public String toString() {
    return "Vector2(" + this.getX() + ", " + this.getZ() + ')';
  }

  @NotNull
  public static Vector2 of(double x, double z) {
    return new Vector2(x, z);
  }

}
