package net.aurika.auspice.platform.location;

import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Vector3 implements Vector3D {

  private final double x;
  private final double y;
  private final double z;
  public static final Vector3 ZERO = of(0, 0, 0);

  public Vector3(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public double x() {
    return this.x;
  }

  @Override
  public double y() {
    return this.y;
  }

  @Override
  public double z() {
    return this.z;
  }

  @NotNull
  public final Block3 toBlockVector() {
    return Block3.of(
        LocationUtils.toBlock(this.x()), LocationUtils.toBlock(this.y()), LocationUtils.toBlock(this.z()));
  }

  @NotNull
  public final Vector3 add(double x, double y, double z) {
    return of(this.x() + x, this.y() + y, this.z() + z);
  }

  @NotNull
  public final Vector3 add(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final Vector3 add(@NotNull Vector3D other) {
    Objects.requireNonNull(other);
    return this.add(other.x(), other.y(), other.z());
  }

  @NotNull
  public final OldLocation inWorld(@NotNull World world) {
    Objects.requireNonNull(world);
    return OldLocation.of(world, this.x(), this.y(), this.z(), 0.0F, 0.0F);
  }

  public final double lengthSq() {
    double x = this.x();
    double y = this.y();
    double z = this.z();
    return x * x + y * y + z * z;
  }

  @NotNull
  public final Vector3 getMinimum(@NotNull Vector3 v2) {
    Objects.requireNonNull(v2);
    return of(Math.min(this.x(), v2.x()), Math.min(this.y(), v2.y()), Math.min(this.z(), v2.z()));
  }

  public final double length() {
    return Math.sqrt(this.lengthSq());
  }

  @NotNull
  public final Vector3 normalize() {
    double len = this.length();
    return of(this.x() / len, this.y() / len, this.z() / len);
  }

  @NotNull
  public final Vector3 withX(@NotNull Number x) {
    Objects.requireNonNull(x);
    return of(x, this.y(), this.z());
  }

  @NotNull
  public final Vector3 withY(@NotNull Number y) {
    Objects.requireNonNull(y);
    return of(this.x(), y, this.z());
  }

  @NotNull
  public final Vector3 withZ(@NotNull Number z) {
    Objects.requireNonNull(z);
    return of(this.x(), this.y(), z);
  }

  @NotNull
  public final Vector3 divide(@NotNull Number by0) {
    Objects.requireNonNull(by0);
    double d = by0.doubleValue();
    return of(this.x() / d, this.y() / d, this.z() / d);
  }

  @NotNull
  public final Vector3 getMaximum(@NotNull Vector3 v2) {
    Objects.requireNonNull(v2);
    return of(Math.max(this.x(), v2.x()), Math.max(this.y(), v2.y()), Math.max(this.z(), v2.z()));
  }

  @NotNull
  public final Vector3 floor() {
    return of(Math.floor(this.x()), Math.floor(this.y()), Math.floor(this.z()));
  }

  @NotNull
  public final Vector3 ceil() {
    return of(Math.ceil(this.x()), Math.ceil(this.y()), Math.ceil(this.z()));
  }

  public final double dot(@NotNull Vector3 other) {
    Objects.requireNonNull(other);
    return this.x() * other.x() + this.y() * other.y() + this.z() * other.z();
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof Vector3)) {
      return false;
    } else {
      return this.x() == ((Vector3) other).x() && this.y() == ((Vector3) other).y() && this.z() == ((Vector3) other).z();
    }
  }

  public @NotNull String toString() {
    return "Vector3(" + this.x() + ", " + this.y() + ", " + this.z() + ')';
  }

  @NotNull
  public static Vector3 of(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return new Vector3(other.getX(), other.getY(), other.getZ());
  }

  public static @NotNull Vector3 of(@NotNull Vector3D other) {
    Validate.Arg.notNull(other, "other");
    return new Vector3(other.x(), other.y(), other.z());
  }

  public static @NotNull Vector3 of(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    return new Vector3(x.doubleValue(), y.doubleValue(), z.doubleValue());
  }

  public static @NotNull Vector3 fromString(@NotNull String str) {
    Validate.Arg.notNull(str, "str");
    CommaDataSplitStrategy fromString = new CommaDataSplitStrategy(str, 3);
    return new Vector3(fromString.nextDouble(), fromString.nextDouble(), fromString.nextDouble());
  }

}
