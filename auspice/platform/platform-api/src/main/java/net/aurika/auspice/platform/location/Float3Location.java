package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Float3Location implements WorldAware, Float3D {

  private final @NotNull World world;
  private final double x;
  private final double y;
  private final double z;

  public Float3Location(@NotNull World world, double x, double y, double z) {
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
  public double floatX() {
    return this.x;
  }

  @Override
  public double floatY() {
    return this.y;
  }

  @Override
  public double floatZ() {
    return this.z;
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof Float3Location)) {
      return false;
    } else {
      return Intrinsics.areEqual(
          this.world(),
          ((Float3Location) other).world()
      ) && this.floatX() == ((Float3Location) other).floatX() && this.floatY() == ((Float3Location) other).floatY() && this.floatZ() == ((Float3Location) other).floatZ();
    }
  }

  @NotNull
  public String toString() {
    return getClass().getSimpleName() + "(world=" + this.world() + ", x=" + this.floatX() + ", y=" + this.floatY() + ", z=" + this.floatZ() + ')';
  }

  @NotNull
  public final Float3Location add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Validate.Arg.notNull(x, "x");
    Validate.Arg.notNull(y, "y");
    Validate.Arg.notNull(z, "z");
    return this.simpleAdd(x, y, z);
  }

  @NotNull
  public final Float3Location add(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.add(other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public final Float3Location add(@NotNull Float3D other) {
    Objects.requireNonNull(other);
    return this.add(other.floatX(), other.floatY(), other.floatZ());
  }

  @NotNull
  public final Float3Location subtract(@NotNull Block3D other) {
    Objects.requireNonNull(other);
    return this.subtract(other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public final Float3Location subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.simpleAdd(-x.doubleValue(), -y.doubleValue(), -z.doubleValue());
  }

  @NotNull
  public final BlockLocation3 toBlockLocation() {
    return BlockLocation3.of(this.world(), (int) this.floatX(), (int) this.floatY(), (int) this.floatZ());
  }

  @NotNull
  public final AbstractBlock3D toBlockVector() {
    return AbstractBlock3D.of((int) this.floatX(), (int) this.floatY(), (int) this.floatZ());
  }

  public final @NotNull AbstractFloat3D toVector() {
    return AbstractFloat3D.of(this.floatX(), this.floatY(), this.floatZ());
  }

  @Contract("_, _, _ -> new")
  private @NotNull Float3Location simpleAdd(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    return of(
        this.world(), this.floatX() + x.doubleValue(), this.floatY() + y.doubleValue(),
        this.floatZ() + z.doubleValue()
    );
  }

  @NotNull
  public static Void modify() {
    throw new UnsupportedOperationException("Cannot modify immutable location");
  }

  @NotNull
  public static Float3Location of(@NotNull World world, double x, double y, double z) {
    Objects.requireNonNull(world);
    return new Float3Location(world, x, y, z);
  }

}
