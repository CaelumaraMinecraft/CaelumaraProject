package net.aurika.auspice.server.location;

import net.aurika.auspice.utils.string.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Vector3 implements Point3D {

  private final double x;
  private final double y;
  private final double z;
  @NotNull
  public static final Vector3 ZERO;

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public double getZ() {
    return this.z;
  }

  @NotNull
  public final BlockVector3 toBlockVector() {
    return BlockVector3.of(
        LocationUtils.toBlock(this.getX()), LocationUtils.toBlock(this.getY()), LocationUtils.toBlock(this.getZ()));
  }

  @NotNull
  public final Vector3 add(double x, double y, double z) {
    return of(this.getX() + x, this.getY() + y, this.getZ() + z);
  }

  @NotNull
  public final Vector3 add(@NotNull BlockPoint3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final Vector3 add(@NotNull Point3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final OldLocation inWorld(@NotNull World world) {
    Objects.requireNonNull(world);
    return OldLocation.of(world, this.getX(), this.getY(), this.getZ(), 0.0F, 0.0F);
  }

  public final double lengthSq() {
    double x = this.getX();
    double y = this.getY();
    double z = this.getZ();
    return x * x + y * y + z * z;
  }

  @NotNull
  public final Vector3 getMinimum(@NotNull Vector3 v2) {
    Objects.requireNonNull(v2);
    return of(Math.min(this.getX(), v2.getX()), Math.min(this.getY(), v2.getY()), Math.min(this.getZ(), v2.getZ()));
  }

  public final double length() {
    return Math.sqrt(this.lengthSq());
  }

  @NotNull
  public final Vector3 normalize() {
    double len = this.length();
    return of(this.getX() / len, this.getY() / len, this.getZ() / len);
  }

  @NotNull
  public final Vector3 withX(@NotNull Number x) {
    Objects.requireNonNull(x);
    return of(x, this.getY(), this.getZ());
  }

  @NotNull
  public final Vector3 withY(@NotNull Number y) {
    Objects.requireNonNull(y);
    return of(this.getX(), y, this.getZ());
  }

  @NotNull
  public final Vector3 withZ(@NotNull Number z) {
    Objects.requireNonNull(z);
    return of(this.getX(), this.getY(), z);
  }

  @NotNull
  public final Vector3 divide(@NotNull Number by0) {
    Objects.requireNonNull(by0);
    double d = by0.doubleValue();
    return of(this.getX() / d, this.getY() / d, this.getZ() / d);
  }

  @NotNull
  public final Vector3 getMaximum(@NotNull Vector3 v2) {
    Objects.requireNonNull(v2);
    return of(Math.max(this.getX(), v2.getX()), Math.max(this.getY(), v2.getY()), Math.max(this.getZ(), v2.getZ()));
  }

  @NotNull
  public final Vector3 floor() {
    return of(Math.floor(this.getX()), Math.floor(this.getY()), Math.floor(this.getZ()));
  }

  @NotNull
  public final Vector3 ceil() {
    return of(Math.ceil(this.getX()), Math.ceil(this.getY()), Math.ceil(this.getZ()));
  }

  public final double dot(@NotNull Vector3 other) {
    Objects.requireNonNull(other);
    return this.getX() * other.getX() + this.getY() * other.getY() + this.getZ() * other.getZ();
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof Vector3)) {
      return false;
    } else {
      return this.getX() == ((Vector3) other).getX() && this.getY() == ((Vector3) other).getY() && this.getZ() == ((Vector3) other).getZ();
    }
  }

  @NotNull
  public String toString() {
    return "Vector3(" + this.getX() + ", " + this.getY() + ", " + this.getZ() + ')';
  }

  @NotNull
  public static Vector3 of(@NotNull BlockPoint3D other) {
    Objects.requireNonNull(other);
    return new Vector3(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public static Vector3 of(@NotNull Point3D other) {
    Objects.requireNonNull(other);
    return new Vector3(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public static Vector3 of(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    return new Vector3(x.doubleValue(), y.doubleValue(), z.doubleValue());
  }

  @NotNull
  public static Vector3 fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy fromString = new CommaDataSplitStrategy(str, 3);
    return new Vector3(fromString.nextDouble(), fromString.nextDouble(), fromString.nextDouble());
  }

  static {
    ZERO = of(0, 0, 0);
  }
}
