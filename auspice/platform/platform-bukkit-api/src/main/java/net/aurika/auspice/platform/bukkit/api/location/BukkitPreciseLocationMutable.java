package net.aurika.auspice.platform.bukkit.api.location;

import net.aurika.auspice.platform.bukkit.world.BukkitWorld;
import net.aurika.auspice.platform.bukkit.world.BukkitWorldAdapter;
import net.aurika.auspice.platform.location.floating.Float3Pos;
import net.aurika.auspice.platform.location.PreciseLocationMutable;
import net.aurika.auspice.platform.location.vector.Vector3;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BukkitPreciseLocationMutable implements PreciseLocationMutable {

  protected final @NotNull org.bukkit.Location location;

  public static @NotNull BukkitPreciseLocationMutable bukkitLocationSynced(@NotNull org.bukkit.Location location) {
    return new BukkitPreciseLocationMutable(location);
  }

  public static @NotNull BukkitPreciseLocationMutable bukkitLocationCloned(@NotNull org.bukkit.Location location) {
    return new BukkitPreciseLocationMutable(location.clone());
  }

  protected BukkitPreciseLocationMutable(@NotNull org.bukkit.Location location) {
    Validate.Arg.notNull(location, "location");
    this.location = location;
  }

  public @NotNull org.bukkit.Location bukkitObject() {
    return location;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable add(@NotNull Vector3 vec) {
    location.setX(location.getX() + vec.vectorX());
    location.setY(location.getY() + vec.vectorY());
    location.setZ(location.getZ() + vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable subtract(@NotNull Vector3 vec) {
    location.setX(location.getX() - vec.vectorX());
    location.setY(location.getY() - vec.vectorY());
    location.setZ(location.getZ() - vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable multiply(@NotNull Vector3 vec) {
    location.setX(location.getX() * vec.vectorX());
    location.setY(location.getY() * vec.vectorY());
    location.setZ(location.getZ() * vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable divide(@NotNull Vector3 vec) {
    location.setX(location.getX() / vec.vectorX());
    location.setY(location.getY() / vec.vectorY());
    location.setZ(location.getZ() / vec.vectorZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable add(@NotNull Float3Pos pos) {
    location.setX(location.getX() + pos.floatX());
    location.setY(location.getY() + pos.floatY());
    location.setZ(location.getZ() + pos.floatZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable subtract(@NotNull Float3Pos pos) {
    location.setX(location.getX() - pos.floatX());
    location.setY(location.getY() - pos.floatY());
    location.setZ(location.getZ() - pos.floatZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable multiply(@NotNull Float3Pos pos) {
    location.setX(location.getX() * pos.floatX());
    location.setY(location.getY() * pos.floatY());
    location.setZ(location.getZ() * pos.floatZ());
    return this;
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable divide(@NotNull Float3Pos pos) {
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
  public @NotNull BukkitPreciseLocationMutable world(@NotNull World world) {
    if (world instanceof BukkitWorld) {
      BukkitWorld bukkitWorld = (BukkitWorld) world;
      location.setWorld(bukkitWorld.bukkitWorld());
    } else {
      throw new IllegalArgumentException("World is not a BukkitWorld");
    }
    return this;
  }

  @Override
  public double preciseX() {
    return location.getX();
  }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable preciseX(double x) {
    location.setX(x);
    return this;
  }

  @Override
  public double preciseY() { return location.getY(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable preciseY(double y) {
    location.setY(y);
    return this;
  }

  @Override
  public double preciseZ() { return location.getZ(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable preciseZ(double z) {
    location.setZ(z);
    return this;
  }

  @Override
  public float pitch() { return location.getPitch(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable pitch(float pitch) {
    location.setPitch(pitch);
    return this;
  }

  @Override
  public float yaw() { return location.getYaw(); }

  @Override
  @Contract(value = "_ -> this", mutates = "this")
  public @NotNull BukkitPreciseLocationMutable yaw(float yaw) {
    location.setYaw(yaw);
    return this;
  }

  public static final class Adapter implements net.aurika.auspice.platform.adapter.Adapter<BukkitPreciseLocationMutable, org.bukkit.Location> {

    public static final Adapter INSTANCE = new Adapter();

    @Override
    public @NotNull BukkitPreciseLocationMutable adaptToAuspice(@NotNull org.bukkit.Location platformObj) {
      return BukkitPreciseLocationMutable.bukkitLocationSynced(platformObj);
    }

    @Override
    public @NotNull org.bukkit.Location adaptToActual(@NotNull BukkitPreciseLocationMutable auspiceObj) {
      return auspiceObj.location;
    }

    @Override
    public @NotNull Class<? extends BukkitPreciseLocationMutable> auspiceType() {
      return BukkitPreciseLocationMutable.class;
    }

    @Override
    public @NotNull Class<? extends org.bukkit.Location> platformType() {
      return org.bukkit.Location.class;
    }

  }

}
