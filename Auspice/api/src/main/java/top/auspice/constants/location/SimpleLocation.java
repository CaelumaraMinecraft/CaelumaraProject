package top.auspice.constants.location;

import net.aurika.annotations.data.Immutable;
import net.aurika.ecliptor.api.DataStringRepresentation;
import net.aurika.ecliptor.api.structured.FunctionsDataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredData;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.api.structured.scalars.DataScalar;
import net.aurika.ecliptor.api.structured.scalars.DataScalarType;
import net.aurika.util.string.CommaDataSplitStrategy;
import net.aurika.validate.Validate;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Immutable
public class SimpleLocation implements DataStringRepresentation, StructuredDataObject {
    private final @NotNull String world;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public static final FunctionsDataStructSchema<SimpleLocation> DATA_TEMPLATE = FunctionsDataStructSchema.of(
            SimpleLocation.class,
            data -> new SimpleLocation(
                    data.getString("world"),
                    data.getDouble("x"),
                    data.getDouble("y"),
                    data.getDouble("z"),
                    data.getFloat("yaw"),
                    data.getFloat("pitch")
            ),
            SimpleLocation::fromDataString,
            SimpleLocation::asDataString,
            "world", DataScalarType.STRING,
            "x", DataScalarType.DOUBLE,
            "y", DataScalarType.DOUBLE,
            "z", DataScalarType.DOUBLE,
            "yaw", DataScalarType.FLOAT,
            "pitch", DataScalarType.FLOAT
    );

    public SimpleLocation(@NotNull String worldName, double x, double y, double z) {
        this(worldName, x, y, z, 0.0F, 0.0F);
    }

    public SimpleLocation(@NotNull String worldName, double x, double y, double z, float yaw, float pitch) {
        Validate.Arg.notNull(worldName, "worldName");
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

    @Contract("_ -> new")
    public static @NotNull SimpleLocation fromDataString(@NotNull String data) {
        Validate.Arg.notNull(data, "data");
        CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 6);
        String worldName = splitter.nextString();
        double x = splitter.nextDouble();
        double y = splitter.nextDouble();
        double z = splitter.nextDouble();
        float yaw = splitter.nextFloat();
        float pitch = splitter.nextFloat();
        return new SimpleLocation(worldName, x, y, z, yaw, pitch);
    }

    @Override
    public @NotNull String asDataString() {
        return CommaDataSplitStrategy.toString(new Object[]{this.world, this.x, this.y, this.z, this.yaw, this.pitch});
    }

    @Override
    public @NonNull StructuredData structuredData() {
        return StructuredData.structuredData(
                Map.of(
                        "world", DataScalar.stringDataScalar(world),
                        "x", DataScalar.doubleDataScalar(x),
                        "y", DataScalar.doubleDataScalar(y),
                        "z", DataScalar.doubleDataScalar(z),
                        "yaw", DataScalar.floatDataScalar(yaw),
                        "pitch", DataScalar.floatDataScalar(pitch)
                )
        );
    }

    @Override
    public @NotNull FunctionsDataStructSchema<? extends SimpleLocation> dataStructSchema() {
        return DATA_TEMPLATE;
    }
}
