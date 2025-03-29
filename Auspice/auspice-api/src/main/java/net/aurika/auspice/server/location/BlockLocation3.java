package net.aurika.auspice.server.location;

import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.utils.string.CommaDataSplitStrategy;
import net.aurika.ecliptor.object.DataStringRepresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BlockLocation3 implements BlockPoint3D, WorldContainer, DataStringRepresentation {

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
  public World getWorld() {
    return this.world;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public int getZ() {
    return this.z;
  }

  @NotNull
  public String asDataString() {
    Object[] var1 = new Object[]{this.getWorld().getName(), this.getX(), this.getY(), this.getZ()};
    String var10000 = CommaDataSplitStrategy.toString(var1);
    Objects.requireNonNull(var10000);
    return var10000;
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
  public final BlockVector3 toVector() {
    return BlockVector3.of(this.getX(), this.getY(), this.getZ());
  }

  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  public boolean equals(@Nullable Object other) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  public String toString() {
    return "BlockLocation3(" + this.getWorld() + ", " + this.getX() + ", " + this.getY() + ", " + this.getZ() + ')';
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
  public static BlockLocation3 of(@NotNull World world, @NotNull BlockPoint3D other) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(other);
    return of(world, other.getX(), other.getY(), other.getZ());
  }

  @NotNull
  public static BlockLocation3 fromString(@NotNull String str) {
    Objects.requireNonNull(str);
    CommaDataSplitStrategy fromString = new CommaDataSplitStrategy(str, 4);
    WorldRegistry worldRegistry = Server.get().worldRegistry();
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