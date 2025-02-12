package top.auspice.constants.location;

import net.aurika.annotations.data.Immutable;
import net.aurika.checker.Checker;
import net.aurika.data.api.DataStringRepresentation;
import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.DataUnits;
import net.aurika.data.api.structure.DataUnitsLike;
import net.aurika.data.api.structure.SimpleDataMapObjectTemplate;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import net.aurika.utils.string.CommaDataSplitStrategy;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

@Immutable
public class SimpleLocation implements DataStringRepresentation, DataUnitsLike {
    private final @NotNull String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public static final SimpleDataMapObjectTemplate<SimpleLocation> DATA_TEMPLATE = SimpleDataMapObjectTemplate.of(
            SimpleLocation.class,
            data -> new SimpleLocation(
                    data.getString("world"),
                    data.getDouble("x"),
                    data.getDouble("y"),
                    data.getDouble("z"),
                    data.getFloat("yaw"),
                    data.getFloat("pitch")
            ),
            "world", DataUnitType.STRING,
            "x", DataUnitType.DOUBLE,
            "y", DataUnitType.DOUBLE,
            "z", DataUnitType.DOUBLE,
            "yaw", DataUnitType.FLOAT,
            "pitch", DataUnitType.FLOAT
    );

    public SimpleLocation(@NotNull String worldName, double x, double y, double z) {
        this(worldName, x, y, z, 0.0F, 0.0F);
    }

    public SimpleLocation(@NotNull String worldName, double x, double y, double z, float yaw, float pitch) {
        Checker.Arg.notNull(worldName, "worldName");
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
        Checker.Arg.notNull(data, "data");
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
    public @NonNull DataUnits simpleData() {
        return DataUnits.of(
                SimpleMappingDataEntry.of("world", world),
                SimpleMappingDataEntry.of("x", x),
                SimpleMappingDataEntry.of("y", y),
                SimpleMappingDataEntry.of("z", z),
                SimpleMappingDataEntry.of("yaw", yaw),
                SimpleMappingDataEntry.of("pitch", pitch)
        );
    }

    @Override
    public @NotNull SimpleDataMapObjectTemplate<? extends SimpleLocation> simpleDataTemplate() {
        return DATA_TEMPLATE;
    }
}
