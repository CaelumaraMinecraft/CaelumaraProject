package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.auspice.platform.world.WorldMutable;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class AbstractLocationMutable implements WorldAware, WorldMutable, Float3DMutable, DirectionalMutable {

  private @NotNull Reference<World> world;
  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;

  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static @NotNull AbstractLocationMutable precisionLocation(@NotNull World world, double x, double y, double z) {
    return new AbstractLocationMutable(world, x, y, z);
  }

  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static @NotNull AbstractLocationMutable precisionLocation(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    return new AbstractLocationMutable(world, x, y, z, pitch, yaw);
  }

  public AbstractLocationMutable(@NotNull World world, double x, double y, double z) {
    this(world, x, y, z, 0, 0);
  }

  /**
   * Constructs a new Location with the given coordinates and direction
   *
   * @param world The world in which this location resides
   * @param x     The x-coordinate of this new location
   * @param y     The y-coordinate of this new location
   * @param z     The z-coordinate of this new location
   * @param yaw   The absolute rotation on the x-plane, in degrees
   * @param pitch The absolute rotation on the y-plane, in degrees
   */
  public AbstractLocationMutable(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    Validate.Arg.notNull(world, "world");
    this.world = new WeakReference<>(world);
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }

  public boolean isWorldLoaded() {
    World world = this.world.get();
    return world != null && world.equals(Platform.get().worldRegistry().getWorld(world.uuid()));
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
  public void world(@NotNull World world) {
    Validate.Arg.notNull(world, "world");
    this.world = new WeakReference<>(world);
  }

  @Override
  public double floatX() { return this.x; }

  @Override
  public void floatX(double x) { this.x = x; }

  @Override
  public double floatY() { return this.y; }

  @Override
  public void floatY(double y) { this.y = y; }

  @Override
  public double floatZ() { return this.z; }

  @Override
  public void floatZ(double z) { this.z = z; }

  @Override
  public float pitch() { return this.pitch; }

  @Override
  public void pitch(float pitch) { this.pitch = pitch; }

  @Override
  public float yaw() { return this.yaw; }

  @Override
  public void yaw(float yaw) { this.yaw = yaw; }

  public boolean equals(@Nullable Object obj) {
    if (!(obj instanceof AbstractLocationMutable)) {
      return false;
    } else {
      AbstractLocationMutable other = (AbstractLocationMutable) obj;
      return Objects.equals(this.world(), other.world())
          && this.floatX() == other.floatX()
          && this.floatY() == other.floatY()
          && this.floatZ() == other.floatZ()
          && this.pitch() == other.pitch()
          && this.yaw() == other.yaw();
    }
  }

  @Override
  public int hashCode() {
    int hash = 3;

    World world = this.world.get();
    hash = 19 * hash + (world != null ? world.hashCode() : 0);
    hash = 19 * hash + Long.hashCode(Double.doubleToLongBits(this.x));
    hash = 19 * hash + Long.hashCode(Double.doubleToLongBits(this.y));
    hash = 19 * hash + Long.hashCode(Double.doubleToLongBits(this.z));
    hash = 19 * hash + Float.floatToIntBits(this.pitch);
    hash = 19 * hash + Float.floatToIntBits(this.yaw);
    return hash;
  }

  @Override
  public String toString() {
    World world = this.world.get();
    return "Location{" + "world=" + world + ",x=" + x + ",y=" + y + ",z=" + z + ",pitch=" + pitch + ",yaw=" + yaw + '}';
  }

  @Override
  @NotNull
  public AbstractLocationMutable clone() {
    try {
      return (AbstractLocationMutable) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

}
