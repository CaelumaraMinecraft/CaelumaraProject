package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldMutable;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A mutable {@link Location}.
 */
public interface LocationMutable extends Location, WorldMutable, Float3PosMutable, DirectionalMutable, Examinable {

  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static @NotNull LocationMutable locationMutable(@NotNull World world, double x, double y, double z) {
    return locationMutable(world, x, y, z, 0.0F, 0.0F);
  }

  /**
   * Constructs a new Location with the given coordinates and direction
   *
   * @param world The world in which this location resides
   * @param x     The x-coordinate of this new location
   * @param y     The y-coordinate of this new location
   * @param z     The z-coordinate of this new location
   * @param pitch The absolute rotation on the y-plane, in degrees
   * @param yaw   The absolute rotation on the x-plane, in degrees
   */
  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  @ExaminableConstructor(publicType = LocationMutable.class, properties = {VAL_WORLD, VAL_X, VAL_Y, VAL_Z, VAL_PITCH, VAL_YAW})
  public static @NotNull LocationMutable locationMutable(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    return new LocationMutableImpl(world, x, y, z, pitch, yaw);
  }

  public static @NotNull LocationMutable zeroLocationMutable(@NotNull World world) {
    return locationMutable(world, 0.0, 0.0, 0.0, 0.0F, 0.0F);
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable add(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable subtract(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable multiply(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable divide(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable add(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable subtract(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable multiply(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable divide(@NotNull Float3Pos pos);

  @Override
  @NotNull World world();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable world(@NotNull World world);

  @Override
  double floatX();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable floatX(double x);

  @Override
  double floatY();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable floatY(double y);

  @Override
  double floatZ();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable floatZ(double z);

  @Override
  float pitch();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable pitch(float pitch);

  @Override
  float yaw();

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  @NotNull LocationMutable yaw(float yaw);

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Location.super.examinableProperties();
  }

}

final class LocationMutableImpl implements LocationMutable {

  private @NotNull Reference<World> world;
  private double x;
  private double y;
  private double z;
  private float pitch;
  private float yaw;

  LocationMutableImpl(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    Validate.Arg.notNull(world, "world");
    this.world = new WeakReference<>(world);
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public @NotNull LocationMutable add(@NotNull Vector3 vec) {
    this.x += vec.vectorX();
    this.y += vec.vectorY();
    this.z += vec.vectorZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable subtract(@NotNull Vector3 vec) {
    this.x -= vec.vectorX();
    this.y -= vec.vectorY();
    this.z -= vec.vectorZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable multiply(@NotNull Vector3 vec) {
    this.x *= vec.vectorX();
    this.y *= vec.vectorY();
    this.z *= vec.vectorZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable divide(@NotNull Vector3 vec) {
    this.x /= vec.vectorX();
    this.y /= vec.vectorY();
    this.z /= vec.vectorZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable add(@NotNull Float3Pos pos) {
    this.x += pos.floatX();
    this.y += pos.floatY();
    this.z += pos.floatZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable subtract(@NotNull Float3Pos pos) {
    this.x -= pos.floatX();
    this.y -= pos.floatY();
    this.z -= pos.floatZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable multiply(@NotNull Float3Pos pos) {
    this.x *= pos.floatX();
    this.y *= pos.floatY();
    this.z *= pos.floatZ();
    return this;
  }

  @Override
  public @NotNull LocationMutable divide(@NotNull Float3Pos pos) {
    this.x /= pos.floatX();
    this.y /= pos.floatY();
    this.z /= pos.floatZ();
    return this;
  }

  @Override
  public @NotNull World world() {
    @Nullable World world = this.world.get();
    if (world == null) {
      throw new IllegalStateException("World is not loaded");
    } else {
      return world;
    }
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull LocationMutable world(@NotNull World world) {
    Validate.Arg.notNull(world, "world");
    this.world = new WeakReference<>(world);
    return this;
  }

  @Override
  public double floatX() { return this.x; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull LocationMutable floatX(double x) {
    this.x = x;
    return this;
  }

  @Override
  public double floatY() { return this.y; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull LocationMutable floatY(double y) {
    this.y = y;
    return this;
  }

  @Override
  public double floatZ() { return this.z; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull LocationMutable floatZ(double z) {
    this.z = z;
    return this;
  }

  @Override
  public float pitch() { return this.pitch; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull LocationMutable pitch(float pitch) {
    this.pitch = pitch;
    return this;
  }

  @Override
  public float yaw() { return this.yaw; }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull LocationMutable yaw(float yaw) {
    this.yaw = yaw;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof LocationMutable)) return false;
    LocationMutable that = (LocationMutable) o;
    return Double.compare(x, that.floatX()) == 0
        && Double.compare(y, that.floatY()) == 0
        && Double.compare(z, that.floatZ()) == 0
        && Float.compare(pitch, that.pitch()) == 0
        && Float.compare(yaw, that.yaw()) == 0
        && Objects.equals(world, that.world());
  }

  @Override
  public int hashCode() {
    return Objects.hash(world, x, y, z, pitch, yaw);
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

  @Override
  public @NotNull LocationMutable clone() {
    try {
      return (LocationMutableImpl) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

}
