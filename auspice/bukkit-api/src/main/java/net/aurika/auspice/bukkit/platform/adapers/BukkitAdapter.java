package net.aurika.auspice.bukkit.platform.adapers;

import net.aurika.auspice.nbt.NBTConverter;
import net.aurika.auspice.nbt.NBTTagConverterRegistry;
import net.aurika.auspice.platform.bukkit.location.BukkitWorld;
import net.aurika.auspice.utils.unsafe.Fn;
import net.aurika.nbt.tag.NBTTag;
import net.aurika.nbt.tag.NBTTagType;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BukkitAdapter {

  private BukkitAdapter() {
  }

  @NotNull
  public static BlockFace adapt(@NotNull Direction direction) {
    Objects.requireNonNull(direction);
    return BlockFace.valueOf(direction.name());
  }

  @NotNull
  public static Direction adapt(@NotNull BlockFace direction) {
    Objects.requireNonNull(direction);
    return Direction.valueOf(direction.name());
  }

  @NotNull
  public static <T extends NBTTag<?>> T adapt(@NotNull NBTTagType<T> type, @NotNull Object nbt) {
    Objects.requireNonNull(type);
    Objects.requireNonNull(nbt);
    NBTConverter converter = NBTTagConverterRegistry.INSTANCE.get(type.id());
    Objects.requireNonNull(converter);
    Object var10001 = Fn.cast(nbt);
    Objects.requireNonNull(var10001);
    T var3 = Fn.cast(converter.fromNBT(var10001));
    Objects.requireNonNull(var3);
    return var3;
  }

  @NotNull
  public static Object adapt(@NotNull NBTTag<?> nbt) {
    Objects.requireNonNull(nbt);
    NBTConverter converter = NBTTagConverterRegistry.INSTANCE.get(nbt.type().id());
    Objects.requireNonNull(converter);
    Object var2 = converter.toNBT(nbt);
    Objects.requireNonNull(var2);
    return var2;
  }

  @NotNull
  public static Color adapt(@NotNull java.awt.Color color) {
    Objects.requireNonNull(color);
    return Color.fromARGB(color.getAlpha(), color.getRed(), color.getGreen(), color.getBlue());
  }

  @NotNull
  public static World adapt(@NotNull org.bukkit.World world) {
    Objects.requireNonNull(world);
    return new BukkitWorld(world);
  }

  @NotNull
  public static org.bukkit.World adapt(@NotNull World world) {
    Objects.requireNonNull(world);
    return ((BukkitWorld) world).getRealWorld();
  }

  @NotNull
  public static Location adapt(@NotNull org.bukkit.World world, @NotNull Vector3 location) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(location);
    return new Location(world, location.getX(), location.getY(), location.getZ());
  }

  @Nullable
  public static OldLocation adapt(@Nullable Location location) {
    OldLocation var10000;
    if (location != null) {
      org.bukkit.World var10002 = location.getWorld();
      Objects.requireNonNull(var10002);
      var10000 = new OldLocation(
          adapt(var10002), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    } else {
      var10000 = null;
    }

    return var10000;
  }

  @Nullable
  public static BlockLocation3 adaptBlockLocation(@Nullable Location location) {
    if (location != null) {
      org.bukkit.World var10002 = location.getWorld();
      Objects.requireNonNull(var10002);
      return new BlockLocation3(adapt(var10002), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    } else {
      return null;
    }
  }

  @Nullable
  public static Location adapt(@Nullable OldLocation location) {
    Location var10000;
    if (location != null) {
      var10000 = new Location(
          adapt(location.getWorld()), location.getX(), location.getY(), location.getZ(), location.getYaw(),
          location.getPitch()
      );
    } else {
      var10000 = null;
    }

    return var10000;
  }

  @NotNull
  public static Location adapt(@NotNull BlockLocation3 location) {
    Objects.requireNonNull(location);
    return new Location(adapt(location.getWorld()), location.getX(), location.getY(), location.getZ(), 0.0F, 0.0F);
  }

  @NotNull
  public static BlockVector3 adaptVector(@NotNull Block block) {
    Objects.requireNonNull(block);
    Location $this$adaptVector_u24lambda_u244 = block.getLocation();
    return BlockVector3.of(
        $this$adaptVector_u24lambda_u244.getBlockX(), $this$adaptVector_u24lambda_u244.getBlockY(),
        $this$adaptVector_u24lambda_u244.getBlockZ()
    );
  }

  @NotNull
  public static BlockLocation3 adaptLocation(@NotNull Block block) {
    Objects.requireNonNull(block);
    Location $this$adaptLocation_u24lambda_u245 = block.getLocation();
    org.bukkit.World var10001 = $this$adaptLocation_u24lambda_u245.getWorld();
    Objects.requireNonNull(var10001);
    return BlockLocation3.of(
        adapt(var10001), $this$adaptLocation_u24lambda_u245.getBlockX(), $this$adaptLocation_u24lambda_u245.getBlockY(),
        $this$adaptLocation_u24lambda_u245.getBlockZ()
    );
  }

  @NotNull
  public static Location adapt(@NotNull org.bukkit.World world, @NotNull BlockVector3 location) {
    Objects.requireNonNull(world);
    Objects.requireNonNull(location);
    return new Location(world, location.getX(), location.getY(), location.getZ());
  }

}
