package net.aurika.auspice.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.platform.server.Platform;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Location {

  private @Nullable World world;
  private double x;
  private double y;
  private double z;
  private float yaw;
  private float pitch;

  public static Location of(@Nullable World world, double x, double y, double z) {
    return new Location(world, x, y, z);
  }

  public static Location of(@Nullable World world, double x, double y, double z, float yaw, float pitch) {
    return new Location(world, x, y, z, yaw, pitch);
  }

  public Location(@Nullable World world, double x, double y, double z) { // Paper
    this(world, x, y, z, 0, 0);
  }

  public Location(@Nullable World world, double x, double y, double z, float yaw, float pitch) {
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.yaw = yaw;
    this.pitch = pitch;
  }

  public @Nullable World getWorld() {
    return this.world;
  }

  public boolean isWorldLoaded() {
    if (this.world == null) {
      return false;
    }

    World world = this.world;
    return Platform.get().worldRegistry().getWorld(world.getUID()) != null;
  }

  public double getX() {
    return this.x;
  }

  public double getY() {
    return this.y;
  }

  public float getYaw() {
    return this.yaw;
  }

  public float getPitch() {
    return this.pitch;
  }

  public double getZ() {
    return this.z;
  }

  public void setWorld(@Nullable World world) {
    this.world = world;
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

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    if (!(other instanceof Location)) {
      return false;
    } else {
      return Intrinsics.areEqual(
          this.getWorld(),
          ((Location) other).getWorld()
      ) && this.getX() == ((Location) other).getX() && this.getY() == ((Location) other).getY() && this.getZ() == ((Location) other).getZ() && this.getYaw() == ((Location) other).getYaw() && this.getPitch() == ((Location) other).getPitch();
    }
  }

  @NotNull
  public String toString() {
    return this.getClass().getSimpleName() + "(world=" + this.getWorld() + ", x=" + this.getX() + ", y=" + this.getY() + ", z=" + this.getZ() + ", yaw=" + this.getYaw() + ", pitch=" + this.getPitch() + ')';
  }

}
