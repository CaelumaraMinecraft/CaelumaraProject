package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Vector3Location implements WorldContainer, Point3D {

  @NotNull
  private final World world;
  private final double x;
  private final double y;
  private final double z;

  public Vector3Location(@NotNull World world, double x, double y, double z) {
    Objects.requireNonNull(world);
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @NotNull
  public World getWorld() {
    return this.world;
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

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof Vector3Location)) {
      return false;
    } else {
      return Intrinsics.areEqual(
          this.getWorld(),
          ((Vector3Location) other).getWorld()
      ) && this.getX() == ((Vector3Location) other).getX() && this.getY() == ((Vector3Location) other).getY() && this.getZ() == ((Vector3Location) other).getZ();
    }
  }

  @NotNull
  public String toString() {
    return "Vector3Location(world=" + this.getWorld() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ')';
  }

  @NotNull
  public final Vector3Location add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.simpleAdd(x, y, z);
  }

  @NotNull
  public final Vector3Location add(@NotNull BlockPoint3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final Vector3Location add(@NotNull Point3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final Vector3Location subtract(@NotNull BlockPoint3D other) {
    Objects.requireNonNull(other);
    return this.subtract(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final Vector3Location subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.simpleAdd(-x.doubleValue(), -y.doubleValue(), -z.doubleValue());
  }

  @NotNull
  public final BlockLocation3 toBlockLocation() {
    return BlockLocation3.of(this.getWorld(), (int) this.getX(), (int) this.getY(), (int) this.getZ());
  }

  @NotNull
  public final BlockVector3 toBlockVector() {
    return BlockVector3.of((int) this.getX(), (int) this.getY(), (int) this.getZ());
  }

  @NotNull
  public final Vector3 toVector() {
    return Vector3.of(this.getX(), this.getY(), this.getZ());
  }

  private Vector3Location simpleAdd(Number x, Number y, Number z) {
    return of(
        this.getWorld(), this.getX() + x.doubleValue(), this.getY() + y.doubleValue(), this.getZ() + z.doubleValue());
  }

  @NotNull
  public static Void modify() {
    throw new UnsupportedOperationException("Cannot modify immutable location");
  }

  @NotNull
  public static Vector3Location of(@NotNull World world, double x, double y, double z) {
    Objects.requireNonNull(world);
    return new Vector3Location(world, x, y, z);
  }

}
