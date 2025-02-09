package top.auspice.constants.location;

import net.aurika.annotations.data.Immutable;
import net.aurika.checker.Checker;
import net.aurika.data.api.DataStringRepresentation;
import net.aurika.data.api.structure.DataMetaType;
import net.aurika.data.api.structure.SimpleData;
import net.aurika.data.api.structure.SimpleDataObject;
import net.aurika.data.api.structure.SimpleDataObjectTemplate;
import net.aurika.data.api.structure.entries.MapDataEntry;
import net.aurika.utils.string.CommaDataSplitStrategy;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

@Immutable
public class SimpleLocation implements DataStringRepresentation, SimpleDataObject {
    private final @NotNull String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public static final SimpleDataObjectTemplate<SimpleLocation> DATA_TEMPLATE = SimpleDataObjectTemplate.of(
            SimpleLocation.class,
            data -> new SimpleLocation(
                    data.getString("world"),
                    data.getDouble("x"),
                    data.getDouble("y"),
                    data.getDouble("z"),
                    data.getFloat("yaw"),
                    data.getFloat("pitch")
            ),
            "world", DataMetaType.STRING,
            "x", DataMetaType.DOUBLE,
            "y", DataMetaType.DOUBLE,
            "z", DataMetaType.DOUBLE,
            "yaw", DataMetaType.FLOAT,
            "pitch", DataMetaType.FLOAT
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
    public @NonNull SimpleData simpleData() {
        return SimpleData.of(
                MapDataEntry.of("world", world),
                MapDataEntry.of("x", x),
                MapDataEntry.of("y", y),
                MapDataEntry.of("z", z),
                MapDataEntry.of("yaw", yaw),
                MapDataEntry.of("pitch", pitch)
        );
    }

    @Override
    public @NotNull SimpleDataObjectTemplate<? extends SimpleLocation> simpleDataTemplate() {
        return DATA_TEMPLATE;
    }
}
