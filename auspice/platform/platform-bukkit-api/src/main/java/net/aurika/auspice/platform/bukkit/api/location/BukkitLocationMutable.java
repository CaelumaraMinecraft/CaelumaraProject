package net.aurika.auspice.platform.bukkit.api.location;

import net.aurika.auspice.platform.bukkit.world.BukkitWorld;
import net.aurika.auspice.platform.bukkit.world.BukkitWorldAdapter;
import net.aurika.auspice.platform.location.Float3Pos;
import net.aurika.auspice.platform.location.LocationMutable;
import net.aurika.auspice.platform.location.Vector3;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BukkitLocationMutable implements LocationMutable {

  protected final @NotNull org.bukkit.Location location;

  public static @NotNull BukkitLocationMutable bukkitLocationSynced(@NotNull org.bukkit.Location location) {
    return new BukkitLocationMutable(location);
  }

  public static @NotNull BukkitLocationMutable bukkitLocationCloned(@NotNull org.bukkit.Location location) {
    return new BukkitLocationMutable(location.clone());
  }

  protected BukkitLocationMutable(@NotNull org.bukkit.Location location) {
    Validate.Arg.notNull(location, "location");
    this.location = location;
  }

  public @NotNull org.bukkit.Location bukkitObject() {
    return location;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable add(@NotNull Vector3 vec) {
    location.setX(location.getX() + vec.vectorX());
    location.setY(location.getY() + vec.vectorY());
    location.setZ(location.getZ() + vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable subtract(@NotNull Vector3 vec) {
    location.setX(location.getX() - vec.vectorX());
    location.setY(location.getY() - vec.vectorY());
    location.setZ(location.getZ() - vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable multiply(@NotNull Vector3 vec) {
    location.setX(location.getX() * vec.vectorX());
    location.setY(location.getY() * vec.vectorY());
    location.setZ(location.getZ() * vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable divide(@NotNull Vector3 vec) {
    location.setX(location.getX() / vec.vectorX());
    location.setY(location.getY() / vec.vectorY());
    location.setZ(location.getZ() / vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable add(@NotNull Float3Pos pos) {
    location.setX(location.getX() + pos.floatX());
    location.setY(location.getY() + pos.floatY());
    location.setZ(location.getZ() + pos.floatZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable subtract(@NotNull Float3Pos pos) {
    location.setX(location.getX() - pos.floatX());
    location.setY(location.getY() - pos.floatY());
    location.setZ(location.getZ() - pos.floatZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable multiply(@NotNull Float3Pos pos) {
    location.setX(location.getX() * pos.floatX());
    location.setY(location.getY() * pos.floatY());
    location.setZ(location.getZ() * pos.floatZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable divide(@NotNull Float3Pos pos) {
    location.setX(location.getX() / pos.floatX());
    location.setY(location.getY() / pos.floatY());
    location.setZ(location.getZ() / pos.floatZ());
    return this;
  }

  @Override
  public @NotNull BukkitWorld world() {
    org.bukkit.World bukkitWorld = location.getWorld();
    if (bukkitWorld == null) {
      throw new IllegalStateException("Cannot get bukkit world");
    }
    return BukkitWorldAdapter.get().adapt(bukkitWorld);
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable world(@NotNull World world) {
    if (world instanceof BukkitWorld) {
      BukkitWorld bukkitWorld = (BukkitWorld) world;
      location.setWorld(bukkitWorld.bukkitWorld());
    } else {
      throw new IllegalArgumentException("World is not a BukkitWorld");
    }
    return this;
  }

  @Override
  public double floatX() {
    return location.getX();
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable floatX(double x) {
    location.setX(x);
    return this;
  }

  @Override
  public double floatY() { return location.getY(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable floatY(double y) {
    location.setY(y);
    return this;
  }

  @Override
  public double floatZ() { return location.getZ(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable floatZ(double z) {
    location.setZ(z);
    return this;
  }

  @Override
  public float pitch() { return location.getPitch(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable pitch(float pitch) {
    location.setPitch(pitch);
    return this;
  }

  @Override
  public float yaw() { return location.getYaw(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitLocationMutable yaw(float yaw) {
    location.setYaw(yaw);
    return this;
  }

  public static final class Adapter implements net.aurika.auspice.platform.adapter.Adapter<BukkitLocationMutable, org.bukkit.Location> {

    public static final Adapter INSTANCE = new Adapter();

    @Override
    public @NotNull BukkitLocationMutable adaptToAuspice(@NotNull org.bukkit.Location platformObj) {
      return BukkitLocationMutable.bukkitLocationSynced(platformObj);
    }

    @Override
    public @NotNull org.bukkit.Location adaptToActual(@NotNull BukkitLocationMutable auspiceObj) {
      return auspiceObj.location;
    }

    @Override
    public @NotNull Class<? extends BukkitLocationMutable> auspiceType() {
      return BukkitLocationMutable.class;
    }

    @Override
    public @NotNull Class<? extends org.bukkit.Location> platformType() {
      return org.bukkit.Location.class;
    }

  }

}
