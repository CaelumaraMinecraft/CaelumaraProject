package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.auspice.platform.world.WorldRegistry;
import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Grid3PosLocation implements Grid3Pos, WorldAware, DataStringRepresentation {

  private final @NotNull World world;
  private final int x;
  private final int y;
  private final int z;

  public Grid3PosLocation(@NotNull World world, int x, int y, int z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public @NotNull World world() {
    return this.world;
  }

  @Override
  public int gridX() {
    return this.x;
  }

  @Override
  public int gridY() {
    return this.y;
  }

  @Override
  public int gridZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] edits = new Object[]{this.world().name(), this.gridX(), this.gridY(), this.gridZ()};
    return CommaDataSplitStrategy.toString(edits);
  }

  @NotNull
  public final Grid3PosLocation add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(this.world);
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return new Grid3PosLocation(this.world, x.intValue(), y.intValue(), z.intValue());
  }

  @NotNull
  public final Grid3PosLocation subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.add(-x.intValue(), -y.intValue(), -z.intValue());
  }

  @NotNull
  public final Grid3PosImpl toVector() {
    return Grid3PosImpl.of(this.gridX(), this.gridY(), this.gridZ());
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  public String toString() {
    return "BlockLocation3(" + this.world() + ", " + this.gridX() + ", " + this.gridY() + ", " + this.gridZ() + ')';
  }

  @NotNull
  public static Grid3PosLocation of(@NotNull World world, @NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return new Grid3PosLocation(world, x.intValue(), y.intValue(), z.intValue());
  }

  @NotNull
  public static Grid3PosLocation of(@NotNull World world, @NotNull Grid3Pos other) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(other);
    return of(world, other.gridX(), other.gridY(), other.gridZ());
  }

  @NotNull
  public static Grid3PosLocation fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy fromString = new CommaDataSplitStrategy(str, 4);
    WorldRegistry worldRegistry = Platform.get().worldRegistry();
    String worldName = fromString.nextString();
    if (worldName == null) {
      throw new IllegalArgumentException("Cannot parse block location string when get the world name: " + str);
    }
    World world = worldRegistry.getWorld(worldName);
    if (world == null) {
      throw new IllegalArgumentException("Cannot parse block location when get the world: " + str);
    }
    return new Grid3PosLocation(world, fromString.nextInt(), fromString.nextInt(), fromString.nextInt());
  }

}