package top.auspice.constants.location;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.annotations.data.Immutable;
import top.auspice.data.object.DataStringRepresentation;
import top.auspice.data.object.structure.DataStructureObject;
import top.auspice.utils.Checker;
import top.auspice.utils.string.CommaDataSplitStrategy;

import java.util.Map;

@Immutable
public class SimpleLocation implements DataStringRepresentation, DataStructureObject {
    private final @NotNull String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public SimpleLocation(@NotNull String worldName, double x, double y, double z) {
        this(worldName, x, y, z, 0.0F, 0.0F);
    }

    public SimpleLocation(@NotNull String worldName, double x, double y, double z, float yaw, float pitch) {
        Checker.Argument.checkNotNull(worldName, "worldName");
        this.world = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public @NotNull String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public static SimpleLocation fromString(String str) {
        return fromDataString(str);
    }

    public static SimpleLocation fromDataString(String data) {
        Checker.Argument.checkNotNull(data, "data");
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 6);
        String worldName = splitter.nextString();
        int x = splitter.nextInt();
        int y = splitter.nextInt();
        int z = splitter.nextInt();
        float yaw = splitter.nextFloat();
        float pitch = splitter.nextFloat();
        return new SimpleLocation(worldName, x, y, z, yaw, pitch);
    }

    @Override
    public @NotNull String asDataString() {
        return CommaDataSplitStrategy.toString(new Object[]{this.world, this.x, this.y, this.z, this.yaw, this.pitch});
    }

    @Override
    public @NonNull Map<String, Object> getData() {
        return Map.of("world", this.world, "x", this.x, "y", this.y, "z", this.z, "yaw", this.yaw, "pitch", this.pitch);
    }
}
