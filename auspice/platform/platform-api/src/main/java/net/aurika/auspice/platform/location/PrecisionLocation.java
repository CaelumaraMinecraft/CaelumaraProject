package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Objects;

public class PrecisionLocation {

  private @Nullable Reference<World> world;
  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;

  @Contract(value = "_, _, _, _ -> new", pure = true)
  public static @NotNull PrecisionLocation precisionLocation(@Nullable World world, double x, double y, double z) {
    return new PrecisionLocation(world, x, y, z);
  }

  @Contract(value = "_, _, _, _, _, _ -> new", pure = true)
  public static @NotNull PrecisionLocation precisionLocation(@Nullable World world, double x, double y, double z, float pitch, float yaw) {
    return new PrecisionLocation(world, x, y, z, pitch, yaw);
  }

  public PrecisionLocation(@Nullable World world, double x, double y, double z) {
    this(world, x, y, z, 0, 0);
  }

  public PrecisionLocation(@Nullable World world, double x, double y, double z, float pitch, float yaw) {
    if (world != null) {
      this.world = new WeakReference<>(world);
    }
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }

  public @Nullable World getWorld() {
    return this.world != null ? this.world.get() : null;
  }

  public boolean isWorldLoaded() {
    if (this.world == null) {
      return false;
    }

    World world = this.world.get();
    return world != null && world.equals(Platform.get().worldRegistry().getWorld(world.uuid()));
  }

  public double x() {
    return this.x;
  }

  public double y() {
    return this.y;
  }

  public double z() {
    return this.z;
  }

  public float pitch() {
    return this.pitch;
  }

  public float yaw() {
    return this.yaw;
  }

  public void setWorld(@Nullable World world) {
    this.world world;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setZ(double z) {
    this.z = z;
  }

  public void setYaw(float yaw) {
    this.yaw = yaw;
  }

  public void setPitch(float pitch) {
    this.pitch = pitch;
  }

  public boolean equals(@Nullable Object obj) {
    if (!(obj instanceof PrecisionLocation)) {
      return false;
    } else {
      PrecisionLocation other = (PrecisionLocation) obj;
      return Objects.equals(this.getWorld(), other.getWorld())
          && this.x() == other.x()
          && this.y() == other.y()
          && this.z() == other.z()
          && this.pitch() == other.pitch()
          && this.yaw() == other.yaw();
    }
  }

  @Override
  public int hashCode() {
    int hash = 3;

    World world = (this.world == null) ? null : this.world.get();
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
    World world = (this.world == null) ? null : this.world.get();
    return "Location{" + "world=" + world + ",x=" + x + ",y=" + y + ",z=" + z + ",pitch=" + pitch + ",yaw=" + yaw + '}';
  }

  @Override
  @NotNull
  public PrecisionLocation clone() {
    try {
      return (PrecisionLocation) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new Error(e);
    }
  }

}
