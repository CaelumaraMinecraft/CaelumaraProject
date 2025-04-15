package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Vector3Location implements WorldAware, Vector3D {

  private final @NotNull World world;
  private final double x;
  private final double y;
  private final double z;

  public Vector3Location(@NotNull World world, double x, double y, double z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull World world() {
    return this.world;
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

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof Vector3Location)) {
      return false;
    } else {
      return Intrinsics.areEqual(
          this.world(),
          ((Vector3Location) other).world()
      ) && this.x() == ((Vector3Location) other).x() && this.y() == ((Vector3Location) other).y() && this.z() == ((Vector3Location) other).z();
    }
  }

  @NotNull
  public String toString() {
    return getClass().getSimpleName() + "(world=" + this.world() + ", x=" + this.x() + ", y=" + this.y() + ", z=" + this.z() + ')';
  }

  @NotNull
  public final Vector3Location add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Validate.Arg.notNull(x, "x");
    Validate.Arg.notNull(y, "y");
    Validate.Arg.notNull(z, "z");
    return this.simpleAdd(x, y, z);
  }

  @NotNull
  public final Vector3Location add(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final Vector3Location add(@NotNull Vector3D other) {
    Objects.requireNonNull(other);
    return this.add(other.x(), other.y(), other.z());
  }

  @NotNull
  public final Vector3Location subtract(@NotNull Block3D other) {
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
    return BlockLocation3.of(this.world(), (int) this.x(), (int) this.y(), (int) this.z());
  }

  @NotNull
  public final Block3 toBlockVector() {
    return Block3.of((int) this.x(), (int) this.y(), (int) this.z());
  }

  public final @NotNull Vector3 toVector() {
    return Vector3.of(this.x(), this.y(), this.z());
  }

  @Contract("_, _, _ -> new")
  private @NotNull Vector3Location simpleAdd(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    return of(
        this.world(), this.x() + x.doubleValue(), this.y() + y.doubleValue(), this.z() + z.doubleValue());
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
