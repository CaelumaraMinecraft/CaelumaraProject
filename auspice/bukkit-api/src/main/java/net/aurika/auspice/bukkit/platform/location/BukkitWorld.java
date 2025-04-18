package net.aurika.auspice.bukkit.platform.location;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.validate.Validate;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class BukkitWorld implements World {

  private final @NotNull org.bukkit.World world;
  private final @NotNull String name;
  private final @NotNull UUID id;
  private final int maxHeight;
  private final int minHeight;
  private static final boolean SUPPORTS_MIN_HEIGHT;

  @Contract("null -> null; !null -> new")
  public static @Nullable BukkitWorld of(@Nullable final org.bukkit.World world) {
    return world == null ? null : new BukkitWorld(world);
  }

  public BukkitWorld(@NotNull org.bukkit.World world) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.name = world.getName();
    this.id = world.getUID();
    this.maxHeight = this.world.getMaxHeight();
    this.minHeight = SUPPORTS_MIN_HEIGHT ? this.world.getMinHeight() : 0;
  }

  public @NotNull org.bukkit.World getRealWorld() {
    return this.world;
  }

  @Override
  public @NotNull String name() {
    return this.name;
  }

  @Override
  public @NotNull UUID id() {
    return this.id;
  }

  @Override
  public int maxHeight() {
    return this.maxHeight;
  }

  @Override
  public int minHeight() {
    return this.minHeight;
  }

  public boolean equals(@Nullable Object other) {
    return other instanceof World && Intrinsics.areEqual(this.id(), ((World) other).id());
  }

  public int hashCode() {
    return this.id().hashCode();
  }

  @NotNull
  public String toString() {
    return "BukkitWorld(" + this.id() + ':' + this.name() + "})";
  }

  @NotNull
  public static org.bukkit.World getWorld(@NotNull String world, @NotNull Object id) {
    Objects.requireNonNull((Object) world);
    Objects.requireNonNull(id);
    final org.bukkit.World world2 = Bukkit.getWorld(world);
    if (world2 == null) {
      throw cantFindWorld(world, id);
    }
    return world2;
  }

  @NotNull
  public static org.bukkit.World from(@NotNull World world) {
    Objects.requireNonNull(world);
    if (world instanceof BukkitWorld) {
      return ((BukkitWorld) world).getRealWorld();
    }
    return getWorld(world.uuid(), world);
  }

  @NotNull
  public static org.bukkit.World getWorld(@NotNull UUID worldId, @NotNull Object id) {
    Objects.requireNonNull(worldId);
    Objects.requireNonNull(id);
    final org.bukkit.World world = Bukkit.getWorld(worldId);
    if (world == null) {
      throw cantFindWorld(worldId, id);
    }
    return world;
  }

  private static IllegalStateException cantFindWorld(final Object world, final Object id) {
    var worlds = Bukkit.getWorlds().stream()
        .map((org.bukkit.World x) -> x.getName() + ':' + x.getUID())
        .collect(Collectors.joining(", "));

    return new IllegalStateException(
        "Cannot find world '" + world + "' from id (" + id + ") available worlds: [ " + worlds + "]");
  }

  static {
    boolean supportsMin;

    try {
      Class.forName("org.bukkit.World").getMethod("getMinHeight");
      supportsMin = true;
    } catch (Throwable var2) {
      supportsMin = false;
    }

    SUPPORTS_MIN_HEIGHT = supportsMin;
  }
}
                                                                                                                                                                                                                                                             
