package net.aurika.auspice.constants.location;

import net.aurika.auspice.server.location.*;
import net.aurika.common.annotations.data.Immutable;
import net.aurika.ecliptor.api.DataStringRepresentation;
import net.aurika.ecliptor.api.structured.StructuredData;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.api.structured.FunctionsDataStructSchema;
import net.aurika.ecliptor.api.structured.scalars.DataScalar;
import net.aurika.ecliptor.api.structured.scalars.DataScalarType;
import net.aurika.util.string.CommaDataSplitStrategy;
import net.aurika.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import top.auspice.server.location.*;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Immutable
public class SimpleBlockLocation implements Cloneable, DataStringRepresentation, BlockPoint3D, StructuredDataObject, Examinable {
    private final @NonNull String world;
    private final int x;
    private final int y;
    private final int z;

    public static final FunctionsDataStructSchema<SimpleBlockLocation> DATA_SCHEMA = FunctionsDataStructSchema.of(
            SimpleBlockLocation.class,
            data -> new SimpleBlockLocation(
                    data.getString("world"),
                    data.getInt("x"),
                    data.getInt("y"),
                    data.getInt("z")
            ),
            plain -> {
                CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(plain, 4);
                return new SimpleBlockLocation(splitter.nextString(), splitter.nextInt(), splitter.nextInt(), splitter.nextInt());
            },
            o -> CommaDataSplitStrategy.toString()
            "world", DataScalarType.STRING,
            "x", DataScalarType.INT,
            "y", DataScalarType.INT,
            "z", DataScalarType.INT
    );

    public SimpleBlockLocation(@NonNull String world, int x, int y, int z) {
        Validate.Arg.notNull(world, "world", "World name cannot be null");
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static SimpleBlockLocation of(@NonNull String worldName, @NonNull BlockVector3 var1) {
        Validate.Arg.notNull(worldName, "worldName", "Cannot get simple location of a null location");
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

    public static SimpleBlockLocation of(@NonNull Block var0) {
        Objects.requireNonNull(var0, "Cannot get simple location of a null block");
        return new SimpleBlockLocation(var0.getWorld().getName(), var0.getX(), var0.getY(), var0.getZ());
    }

    public BlockVector3 toBlockVector() {
        return BlockVector3.of(this.x, this.y, this.z);
    }

    public static Supplier<SimpleBlockLocation> resolve(Block var0) {
        return () -> of(var0);
    }

    public static Supplier<SimpleBlockLocation> resolve(Entity var0) {
        return () -> of(var0.getLocation());
    }

    public @NonNull SimpleChunkLocation toSimpleChunkLocation() {
        return new SimpleChunkLocation(this.world, this.x >> 4, this.z >> 4);
    }

    public @NonNull Block getBlock() {
        return this.getBukkitWorld().getBlockAt(this.x, this.y, this.z);
    }

    public @NonNull String getWorld() {
        return this.world;
    }

    public @Nullable World getBukkitWorld() {
        return (World) Objects.requireNonNull(BukkitWorld.getWorld(this.world, this));
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
        int var1 = 434 + this.world.hashCode();
        var1 = var1 * 31 + this.x;
        var1 = var1 * 31 + this.y;
        return var1 * 31 + this.z;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof SimpleBlockLocation pos) {
            return this.x == pos.x && this.y == pos.y && this.z == pos.z && Objects.equals(this.world, pos.world);
        }
        return false;
    }

    public boolean equalsIgnoreWorld(SimpleBlockLocation var1) {
        return this.x == var1.x && this.y == var1.y && this.z == var1.z;
    }

    public String toString() {
        return this.world + ", " + this.x + ", " + this.y + ", " + this.z;
    }

    public @NonNull Vector toVector() {
        return new Vector(this.x, this.y, this.z);
    }

    public @NonNull SimpleBlockLocation getRelative(int var1, int var2, int var3) {
        return new SimpleBlockLocation(this.world, this.x + var1, this.y + var2, this.z + var3);
    }

    public boolean validateWorld() {
        return !Strings.isNullOrEmpty(this.world) && this.getBukkitWorld() != null;
    }

    public double distanceSquared(@NonNull SimpleBlockLocation var1) {
        return Math.sqrt(this.distance(var1));
    }

    public double distance(@NonNull SimpleBlockLocation var1) {
        Objects.requireNonNull(var1, "Cannot check distance between a null location");
        if (!Objects.equals(this.world, var1.world)) {
            throw new IllegalArgumentException("Cannot measure distance between " + this.world + " and " + var1.world);
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

    public static SimpleBlockLocation fromString(@NotNull String str) {
        return fromDataString(str);
    }

    public static SimpleBlockLocation fromDataString(@NotNull String data) {
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 4);
        String world = splitter.nextString();
        int x = splitter.nextInt();
        int y = splitter.nextInt();
        int z = splitter.nextInt();
        return new SimpleBlockLocation(world, x, y, z);
    }

    @Override
    public @NotNull String asDataString() {
        return CommaDataSplitStrategy.toString(new Object[]{this.world, this.x, this.y, this.z});
    }

    @Override
    public @NonNull StructuredData structuredData() {
        return StructuredData.structuredData(
                Map.of(
                        "world", DataScalar.stringDataScalar(world),
                        "x", DataScalar.intDataScalar(x),
                        "y", DataScalar.intDataScalar(y),
                        "z", DataScalar.intDataScalar(z)
                )
        );
    }

    @Override
    public @NotNull FunctionsDataStructSchema<? extends SimpleBlockLocation> dataStructSchema() {
        return DATA_SCHEMA;
    }

    @Override
    public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.concat(
                Stream.of(ExaminableProperty.of("world", world)),
                BlockPoint3D.super.examinableProperties()
        );
    }
}
