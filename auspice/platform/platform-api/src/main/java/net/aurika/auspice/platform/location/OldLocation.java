package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class OldLocation implements Directional, WorldAware, Vector3D {

  private final @NotNull World world;
  private final double x;
  private final double y;
  private final double z;
  private final float yaw;
  private final float pitch;

  public OldLocation(@NotNull World world, double x, double y, double z, float yaw, float pitch) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  @NotNull
  public World world() {
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

  public float getYaw() {
    return this.yaw;
  }

  public float getPitch() {
    return this.pitch;
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof OldLocation)) {
      return false;
    } else {
      return Intrinsics.areEqual(
          this.world(),
          ((OldLocation) other).world()
      ) && this.x() == ((OldLocation) other).x() && this.y() == ((OldLocation) other).y() && this.z() == ((OldLocation) other).z() && this.getYaw() == ((OldLocation) other).getYaw() && this.getPitch() == ((OldLocation) other).getPitch();
    }
  }

  @NotNull
  public String toString() {
    return "Location(world=" + this.world() + ", x=" + this.x() + ", y=" + this.y() + ", z=" + this.z() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ')';
  }

  @NotNull
  public final OldLocation add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.simpleAdd(x, y, z);
  }

  @NotNull
  public final OldLocation add(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.add(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final OldLocation add(@NotNull Vector3D other) {
    Objects.requireNonNull(other);
    return this.add(other.x(), other.y(), other.z());
  }

  @NotNull
  public final OldLocation subtract(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.subtract(other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public final OldLocation subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.simpleAdd(-x.doubleValue(), -y.doubleValue(), -z.doubleValue());
  }

  @NotNull
  public final Block3 toBlockVector() {
    return Block3.of((int) this.x(), (int) this.y(), (int) this.z());
  }

  @NotNull
  public final Vector3Location toVectorLocation() {
    return Vector3Location.of(this.world(), this.x(), this.y(), this.z());
  }

  @NotNull
  public final Vector3 toVector() {
    return Vector3.of(this.x(), this.y(), this.z());
  }

  private OldLocation simpleAdd(Number x, Number y, Number z) {
    return of(
        this.world(), this.x() + x.doubleValue(), this.y() + y.doubleValue(), this.z() + z.doubleValue(),
        this.getYaw(), this.getPitch()
    );
  }

  @NotNull
  public static Void modify() {
    throw new UnsupportedOperationException("Cannot modify immutable location");
  }

  @NotNull
  public static OldLocation of(@NotNull World world, double x, double y, double z, float yaw, float pitch) {
    return new OldLocation(world, x, y, z, yaw, pitch);
  }

}
