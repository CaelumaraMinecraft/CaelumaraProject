package net.aurika.auspice.platform.location;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractFloat2D implements Float2D {

  private final double x;
  private final double z;

  public AbstractFloat2D(double x, double z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public double floatX() {
    return this.x;
  }

  @Override
  public double floatZ() {
    return this.z;
  }

  public int hashCode() {
    long bits = Double.doubleToLongBits(this.floatX());
    bits ^= Double.doubleToLongBits(this.floatZ()) * (long) 31;
    return (int) bits ^ (int) (bits >> 32);
  }

  public boolean equals(@Nullable Object obj) {
    Float2D other = obj instanceof Float2D ? (Float2D) obj : null;
    if ((obj instanceof Float2D ? (Float2D) obj : null) == null) {
      return false;
    } else {
      return this.floatX() == other.floatX() && this.floatZ() == other.floatZ();
    }
  }

  @NotNull
  public String toString() {
    return "Vector2(" + this.floatX() + ", " + this.floatZ() + ')';
  }

  @NotNull
  public static AbstractFloat2D of(double x, double z) {
    return new AbstractFloat2D(x, z);
  }

}
