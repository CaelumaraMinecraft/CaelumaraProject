package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.auspice.platform.world.WorldRegistry;
import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BlockLocation3 implements Block3D, WorldAware, DataStringRepresentation {

  private final @NotNull World world;
  private final int x;
  private final int y;
  private final int z;

  public BlockLocation3(@NotNull World world, int x, int y, int z) {
    Objects.requireNonNull(world);
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @NotNull
  public World world() {
    return this.world;
  }

  public int intX() {
    return this.x;
  }

  public int intY() {
    return this.y;
  }

  public int intZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] edits = new Object[]{this.world().name(), this.intX(), this.intY(), this.intZ()};
    return CommaDataSplitStrategy.toString(edits);
  }

  @NotNull
  public final BlockLocation3 add(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(this.world);
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return new BlockLocation3(this.world, x.intValue(), y.intValue(), z.intValue());
  }

  @NotNull
  public final BlockLocation3 subtract(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return this.add(-x.intValue(), -y.intValue(), -z.intValue());
  }

  @NotNull
  public final AbstractBlock3D toVector() {
    return AbstractBlock3D.of(this.intX(), this.intY(), this.intZ());
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  public String toString() {
    return "BlockLocation3(" + this.world() + ", " + this.intX() + ", " + this.intY() + ", " + this.intZ() + ')';
  }

  @NotNull
  public static BlockLocation3 of(@NotNull World world, @NotNull Number x, @NotNull Number y, @NotNull Number z) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(x);
    Objects.requireNonNull(y);
    Objects.requireNonNull(z);
    return new BlockLocation3(world, x.intValue(), y.intValue(), z.intValue());
  }

  @NotNull
  public static BlockLocation3 of(@NotNull World world, @NotNull Block3D other) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(other);
    return of(world, other.intX(), other.intY(), other.intZ());
  }

  @NotNull
  public static BlockLocation3 fromString(@NotNull String str) {
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
    return new BlockLocation3(world, fromString.nextInt(), fromString.nextInt(), fromString.nextInt());
  }

}