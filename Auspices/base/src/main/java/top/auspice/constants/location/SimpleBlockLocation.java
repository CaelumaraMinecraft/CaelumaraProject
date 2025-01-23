package top.auspice.constants.location;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.annotations.data.Immutable;
import top.auspice.data.object.DataStringRepresentation;
import top.auspice.data.object.structure.DataStructureObject;
import top.auspice.server.location.*;
import top.auspice.utils.Checker;
import top.auspice.utils.string.CommaDataSplitStrategy;
import top.auspice.utils.string.Strings;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

@Immutable
public class SimpleBlockLocation implements Cloneable, DataStringRepresentation, BlockPoint3D, DataStructureObject {
    private final @NonNull String worldName;
    private final int x;
    private final int y;
    private final int z;

    public SimpleBlockLocation(@NonNull String worldName, int x, int y, int z) {
        this.worldName = Checker.Argument.checkNotNull(worldName, "worldName", "World name cannot be null");
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static SimpleBlockLocation of(@NonNull String worldName, @NonNull BlockVector3 var1) {
        Checker.Argument.checkNotNull(worldName, "worldName", "Cannot get simple location of a null location");
        return new SimpleBlockLocation(worldName, var1.getX(), var1.getY(), var1.getZ());
    }

    public static SimpleBlockLocation of(Location var0) {
        Objects.requireNonNull(var0, "Cannot get simple location of a null location");
        return new SimpleBlockLocation(var0.getWorld().getName(), (int) var0.getX(), (int) var0.getY(), (int) var0.getZ());
    }

    public static SimpleBlockLocation of(BlockLocation3 var0) {
        Objects.requireNonNull(var0, "Cannot get simple location of a null location");
        return new SimpleBlockLocation(var0.getWorld().getName(), var0.getX(), var0.getY(), var0.getZ());
    }

    public static SimpleBlockLocation of(@NonNull org.bukkit.Location var0) {
        Objects.requireNonNull(var0, "Cannot get simple location of a null location");
        World var1 = (World) Objects.requireNonNull(var0.getWorld(), "World of location is null - Either an issue with your world management plugin or the world was deleted.");
        return new SimpleBlockLocation(var1.getName(), var0.getBlockX(), var0.getBlockY(), var0.getBlockZ());
    }

    public static SimpleBlockLocation of(@NonNull Block var0) {
        Objects.requireNonNull(var0, "Cannot get simple location of a null block");
        return new SimpleBlockLocation(var0.getWorld().getName(), var0.getX(), var0.getY(), var0.getZ());
    }

    public BlockVector3 toBlockVector() {
        return BlockVector3.of(this.x, this.y, this.z);
    }

    public static boolean equalsIgnoreWorld(@Nonnull org.bukkit.Location var0, @Nonnull org.bukkit.Location var1) {
        if (var0 == var1) {
            return true;
        } else {
            return var0.getX() == var1.getX() && var0.getY() == var1.getY() && var0.getZ() == var1.getY();
        }
    }

    public static boolean equalsIgnoreWorld(@Nonnull Block var0, @Nonnull Block var1) {
        if (var0 == var1) {
            return true;
        } else {
            return var0.getX() == var1.getX() && var0.getY() == var1.getY() && var0.getZ() == var1.getY();
        }
    }

    public static Supplier<SimpleBlockLocation> resolve(Block var0) {
        return () -> of(var0);
    }

    public static Supplier<SimpleBlockLocation> resolve(Entity var0) {
        return () -> of(var0.getLocation());
    }

    public @NonNull SimpleChunkLocation toSimpleChunkLocation() {
        return new SimpleChunkLocation(this.worldName, this.x >> 4, this.z >> 4);
    }

    public @NonNull Block getBlock() {
        return this.getBukkitWorld().getBlockAt(this.x, this.y, this.z);
    }

    public @NonNull String getWorld() {
        return this.worldName;
    }

    public @Nullable World getBukkitWorld() {
        return (World) Objects.requireNonNull(BukkitWorld.getWorld(this.worldName, this));
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getZ() {
        return this.z;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public @NonNull SimpleBlockLocation clone() {
        try {
            return (SimpleBlockLocation) super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new AssertionError("SimpleLocation clone failed: " + var2.getLocalizedMessage());
        }
    }

    public int hashCode() {
        int var1 = 434 + this.worldName.hashCode();
        var1 = var1 * 31 + this.x;
        var1 = var1 * 31 + this.y;
        return var1 * 31 + this.z;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SimpleBlockLocation pos) {
            return this.x == pos.x && this.y == pos.y && this.z == pos.z && Objects.equals(this.worldName, pos.worldName);
        }
        return false;
    }

    public boolean equalsIgnoreWorld(SimpleBlockLocation var1) {
        return this.x == var1.x && this.y == var1.y && this.z == var1.z;
    }

    public String toString() {
        return this.worldName + ", " + this.x + ", " + this.y + ", " + this.z;
    }

    public @NonNull Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public @NonNull SimpleBlockLocation getRelative(int var1, int var2, int var3) {
        return new SimpleBlockLocation(this.worldName, this.x + var1, this.y + var2, this.z + var3);
    }

    public boolean validateWorld() {
        return !Strings.isNullOrEmpty(this.worldName) && this.getBukkitWorld() != null;
    }

    public double distanceSquared(@NonNull SimpleBlockLocation var1) {
        return Math.sqrt(this.distance(var1));
    }

    public double distance(@NonNull SimpleBlockLocation var1) {
        Objects.requireNonNull(var1, "Cannot check distance between a null location");
        if (!Objects.equals(this.worldName, var1.worldName)) {
            throw new IllegalArgumentException("Cannot measure distance between " + this.worldName + " and " + var1.worldName);
        } else {
            return this.distanceIgnoreWorld(var1);
        }
    }

    public double distanceIgnoreWorld(@NonNull SimpleBlockLocation var1) {
        Objects.requireNonNull(var1, "Cannot check distance between a null location");
        int var2;
        int var3;
        int var4;
        return (double) (var2 = this.x - var1.x) * (double) var2 + (double) (var3 = this.y - var1.y) * (double) var3 + (double) (var4 = this.z - var1.z) * (double) var4;
    }

    public double distanceSquaredIgnoreWorld(@NonNull SimpleBlockLocation var1) {
        Objects.requireNonNull(var1, "Cannot check distance between a null location");
        return Math.sqrt(this.distanceIgnoreWorld(var1));
    }


    public static SimpleBlockLocation fromString(@NotNull String var0) {
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(var0, 4);
        String world = splitter.nextString();
        int x = splitter.nextInt();
        int y = splitter.nextInt();
        int z = splitter.nextInt();
        return new SimpleBlockLocation(world, x, y, z);
    }

    @Override
    public @NotNull String asDataString() {
        return CommaDataSplitStrategy.toString(new Object[]{this.worldName, this.x, this.y, this.z});
    }

    @Override
    public @NonNull Map<String, Object> getData() {
        return Map.of("world", this.worldName, "x", this.x, "y", this.y, "z", this.z);
    }
}
