package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Vector2 implements Vector2D {

  private final double x;
  private final double z;

  public Vector2(double x, double z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public double x() {
    return this.x;
  }

  @Override
  public double z() {
    return this.z;
  }

  public int hashCode() {
    long bits = Double.doubleToLongBits(this.x());
    bits ^= Double.doubleToLongBits(this.z()) * (long) 31;
    return (int) bits ^ (int) (bits >> 32);
  }

  public boolean equals(@Nullable Object obj) {
    Vector2D other = obj instanceof Vector2D ? (Vector2D) obj : null;
    if ((obj instanceof Vector2D ? (Vector2D) obj : null) == null) {
      return false;
    } else {
      return this.x() == other.x() && this.z() == other.z();
    }
  }

  @NotNull
  public String toString() {
    return "Vector2(" + this.x() + ", " + this.z() + ')';
  }

  @NotNull
  public static Vector2 of(double x, double z) {
    return new Vector2(x, z);
  }

}
