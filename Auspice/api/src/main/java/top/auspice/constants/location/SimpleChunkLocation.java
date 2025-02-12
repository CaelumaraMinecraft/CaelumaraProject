package top.auspice.constants.location;

import net.aurika.data.api.DataStringRepresentation;
import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.BundledData;
import net.aurika.data.api.bundles.BundledDataLike;
import net.aurika.data.api.bundles.DataBundleSchema;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import net.aurika.util.string.CommaDataSplitStrategy;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.server.entity.Entity;
import top.auspice.server.location.BlockPoint2D;
import top.auspice.server.location.BlockVector2;
import top.auspice.server.location.Direction;
import top.auspice.server.location.Location;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class SimpleChunkLocation implements Cloneable, DataStringRepresentation, BlockPoint2D, BundledDataLike {
    private final @NonNull String world;
    private final int x;
    private final int z;

    public static final DataBundleSchema<SimpleChunkLocation> DATA_TEMPLATE = DataBundleSchema.of(
            SimpleChunkLocation.class,
            data -> new SimpleChunkLocation(
                    data.getString("world"),
                    data.getInt("x"),
                    data.getInt("z")
            ),
            "world", DataScalarType.STRING,
            "x", DataScalarType.INT,
            "z", DataScalarType.INT
    );

    public SimpleChunkLocation(@NonNull String var1, int var2, int var3) {
        this.world = Objects.requireNonNull(var1, "Simple chunk location cannot have a null world");
        this.x = var2;
        this.z = var3;
    }

    public static SimpleChunkLocation of(@NonNull Chunk var0) {
        Objects.requireNonNull(var0, "Cannot get simple chunk location of a null chunk");
        return new SimpleChunkLocation(var0.getWorld().getName(), var0.getX(), var0.getZ());
    }

    public static SimpleChunkLocation of(Location var0) {
        Objects.requireNonNull(var0, "Cannot get simple chunk location of a null location");
        return new SimpleChunkLocation((Objects.requireNonNull(var0.getWorld(), "Simple chunk location cannot have a null world")).getName(), (int) var0.getX() >> 4, (int) var0.getZ() >> 4);
    }

    public static SimpleChunkLocation of(@NonNull Block var0) {
        Objects.requireNonNull(var0, "Cannot get simple chunk location of a null block");
        return new SimpleChunkLocation(var0.getWorld().getName(), var0.getX() >> 4, var0.getZ() >> 4);
    }

    public BlockVector2 toBlockVector() {
        return BlockVector2.of(this.x, this.z);
    }

    public static int calculateBorderSize(int var0) {
        int var1;
        return (var1 = 2 * var0 + 1) * var1;
    }

    private static void a(int var0) {
        if (var0 <= 0) {
            throw new IllegalArgumentException("Cannot get chunks around chunk with radius: " + var0);
        }
    }

    public static Supplier<SimpleChunkLocation> resolve(Block var0) {
        return () -> of(var0);
    }

    public static Supplier<SimpleChunkLocation> resolve(Entity var0) {
        return () -> of(var0.getLocationCopy());
    }

    public boolean equalsIgnoreWorld(SimpleChunkLocation var1) {
        return this.x == var1.x && this.z == var1.z;
    }

    public SimpleChunkLocation getRelative(int var1, int var2) {
        return new SimpleChunkLocation(this.world, this.x + var1, this.z + var2);
    }

    public SimpleChunkLocation getRelative(Direction var1) {
        if (var1.getType() != Direction.Type.CARDINAL) {
            throw new IllegalArgumentException("Only cardinal directions can be used for relative chunks: " + var1);
        } else {
            return this.getRelative(var1.getX(), var1.getZ());
        }
    }

    public boolean isInChunk(@NonNull SimpleBlockLocation var1) {
        SimpleChunkLocation var2;
        return (var2 = var1.toSimpleChunkLocation()).x == this.x && var2.z == this.z && Objects.equals(this.world, var2.world);
    }

    public boolean isInChunk(org.bukkit.@NonNull Location var1) {
        return this.equals(of(var1));
    }

    public double distance(@NonNull SimpleChunkLocation var1) {
        if (var1.world.equals(this.world)) {
            return this.distanceIgnoreWorld(var1);
        } else {
            throw new IllegalArgumentException("Cannot measure distance between " + this.world + " and " + var1.world);
        }
    }

    public double distanceIgnoreWorld(@NonNull SimpleChunkLocation var1) {
        return Math.sqrt(NumberConversions.square((double) this.x - (double) var1.x) + NumberConversions.square((double) this.z - (double) var1.z));
    }

    public org.bukkit.@NonNull Location getCenterLocation() {
        org.bukkit.World var1 = this.getBukkitWorld();
        int var2 = (this.x << 4) + 8;
        int var3 = (this.z << 4) + 8;
        int var4 = var1.getHighestBlockYAt(var2, var3) + 1;
        return new org.bukkit.Location(var1, (double) var2, (double) var4, (double) var3);
    }

    public BlockVector2 toLocationXZ() {
        return BlockVector2.of((this.x << 4) + 8, (this.z << 4) + 8);
    }

    public org.bukkit.@NonNull Location getCenterLocation(double var1) {
        org.bukkit.World var3 = this.getBukkitWorld();
        int var4 = (this.x << 4) + 8;
        int var5 = (this.z << 4) + 8;
        return new org.bukkit.Location(var3, (double) var4, var1, (double) var5);
    }

    public @NonNull SimpleBlockLocation getSimpleLocation(int var1, int var2, int var3) {
        var1 = this.x << 4 | var1;
        var3 = this.z << 4 | var3;
        return new SimpleBlockLocation(this.world, var1, var2, var3);
    }

    public @NonNull String getWorld() {
        return this.world;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public @NonNull SimpleChunkLocation clone() {
        try {
            return (SimpleChunkLocation) super.clone();
        } catch (CloneNotSupportedException var2) {
            throw new AssertionError("SimpleChunkLocation clone failed: " + var2.getLocalizedMessage());
        }
    }

    public BlockVector2 worldlessWrapper() {
        return BlockVector2.of(this.x, this.z);
    }

    public int hashCode() {
        int var1 = 589 + this.world.hashCode();
        var1 = var1 * 31 + this.x;
        return var1 * 31 + this.z;
    }

    public boolean equals(Object var1) {
        if (this == var1) {
            return true;
        } else if (var1 instanceof SimpleChunkLocation) {
            var1 = var1;
            return this.x == var1.b && this.z == var1.c && Objects.equals(this.world, var1.a);
        } else {
            return false;
        }
    }

    public List<Player> getPlayers() {
        ArrayList var1 = new ArrayList();
        Entity[] var2;
        int var3 = (var2 = this.toChunk().getEntities()).length;

        for (int var4 = 0; var4 < var3; ++var4) {
            Entity var5;
            if ((var5 = var2[var4]) instanceof Player) {
                var1.add(var5);
            }
        }

        return var1;
    }

    public @Nullable SimpleChunkLocation findFarthestChunk(Collection<SimpleChunkLocation> var1, boolean var2) {
        Objects.requireNonNull(var1);
        if (var1.isEmpty()) {
            return null;
        } else {
            SimpleChunkLocation var3 = null;
            SimpleChunkLocation var4 = null;
            double var5 = 0.0F;

            for (SimpleChunkLocation var7 : var1) {
                if (!var2 && !var7.world.equals(this.world)) {
                    var3 = var7;
                } else {
                    double var9;
                    if ((var9 = this.distance(var7)) > var5) {
                        var4 = var7;
                        var5 = var9;
                    }
                }
            }

            if (var4 == null && var3 != null) {
                return var3;
            } else {
                return var4;
            }
        }
    }

    public SimpleChunkLocation[] getChunksAround(int var1, boolean var2) {
        a(var1);
        SimpleChunkLocation[] var3 = new SimpleChunkLocation[calculateBorderSize(var1) - (var2 ? 0 : 1)];
        int var4 = 0;

        for (int var5 = -var1; var5 <= var1; ++var5) {
            for (int var6 = -var1; var6 <= var1; ++var6) {
                if (var2 || var5 != 0 || var6 != 0) {
                    var3[var4++] = this.getRelative(var5, var6);
                }
            }
        }

        return var3;
    }

    public SimpleChunkLocation[] getChunksAround(int var1) {
        return this.getChunksAround(var1, false);
    }

    public <T> T findFromSurroundingChunks(int var1, Function<SimpleChunkLocation, T> var2) {
        return (T) this.findFromSurroundingChunks(var1, (Object) null, var2);
    }

    public boolean anySurroundingChunks(int var1, Predicate<SimpleChunkLocation> var2) {
        return this.findFromSurroundingChunks(var1, Boolean.FALSE, (var1x) -> var2.test(var1x) ? Boolean.TRUE : null);
    }

    public <T> T findFromSurroundingChunks(int var1, T var2, Function<SimpleChunkLocation, T> var3) {
        a(var1);

        for (int var4 = -var1; var4 <= var1; ++var4) {
            for (int var5 = -var1; var5 <= var1; ++var5) {
                if (var4 != 0 || var5 != 0) {
                    SimpleChunkLocation var6 = this.getRelative(var4, var5);
                    if ((var6 = (SimpleChunkLocation) var3.apply(var6)) != null) {
                        return (T) var6;
                    }
                }
            }
        }

        return var2;
    }

    public String toString() {
        return this.world + ", " + this.x + ", " + this.z;
    }

    public @NonNull Chunk toChunk() {
        return this.getBukkitWorld().getChunkAt(this.x, this.z);
    }

    public @NotNull String asDataString() {
        return CommaDataSplitStrategy.toString(new Object[]{this.world, this.x, this.z});
    }

    public static SimpleChunkLocation fromDataString(@NotNull String data) {
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 3);
        String worldName = splitter.nextString();
        int x = splitter.nextInt();
        int z = splitter.nextInt();
        return new SimpleChunkLocation(worldName, x, z);
    }

    public static SimpleChunkLocation fromString(@NotNull String str) {
        return fromDataString(str);
    }

    @Override
    public @NonNull BundledData simpleData() {
        return BundledData.of(
                SimpleMappingDataEntry.of("world", world),
                SimpleMappingDataEntry.of("x", x),
                SimpleMappingDataEntry.of("z", z)
        );
    }

    @Override
    public @NotNull DataBundleSchema<? extends SimpleChunkLocation> simpleDataTemplate() {
        return DATA_TEMPLATE;
    }
}
