package net.aurika.auspice.constants.location;

import net.aurika.auspice.platform.location.direction.Directional;
import net.aurika.auspice.platform.location.floating.Float3Pos;
import net.aurika.auspice.platform.location.vector.Vector3;
import net.aurika.auspice.platform.world.WorldNameAware;
import net.aurika.common.annotation.data.Immutable;
import net.aurika.common.data.string.DataStringRepresentation;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.uitl.string.split.CommaDataSplitStrategy;
import net.aurika.common.validate.Validate;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@Immutable
public final class SimplePreciseLocation implements WorldNameAware, Float3Pos, Directional, DataStringRepresentation {

  @Contract("_, _, _, _, _, _ -> new")
  @ExaminableConstructor(publicType = SimplePreciseLocation.class, properties = {VAL_WORLD$NAME, VAL_X, VAL_Y, VAL_Z, VAL_PITCH, VAL_YAW})
  public static @NotNull SimplePreciseLocation simplePreciseLocation(@NotNull String worldName, double x, double y, double z, float pitch, float yaw) {
    return new SimplePreciseLocation(worldName, x, y, z, pitch, yaw);
  }

  private final @NotNull String worldName;
  private final double x;
  private final double y;
  private final double z;
  private final float pitch;
  private final float yaw;

  private SimplePreciseLocation(@NotNull String worldName, double x, double y, double z) {
    this(worldName, x, y, z, 0.0F, 0.0F);
  }

  private SimplePreciseLocation(@NotNull String worldName, double x, double y, double z, float pitch, float yaw) {
    Validate.Arg.notNull(worldName, "worldName");
    this.worldName = worldName;
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public @NotNull SimplePreciseLocation add(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new SimplePreciseLocation(worldName, x + vec.vectorX(), y + vec.vectorY(), z + vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull SimplePreciseLocation subtract(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new SimplePreciseLocation(worldName, x - vec.vectorX(), y - vec.vectorY(), z - vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull SimplePreciseLocation multiply(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new SimplePreciseLocation(worldName, x * vec.vectorX(), y * vec.vectorY(), z * vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull SimplePreciseLocation divide(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new SimplePreciseLocation(worldName, x / vec.vectorX(), y / vec.vectorY(), z / vec.vectorZ(), pitch, yaw);
  }


  @Override
  @ExaminablePropertyGetter(VAL_WORLD$NAME)
  public @NotNull String worldName() {
    return worldName;
  }

  @ExaminablePropertyGetter(VAL_X)
  public double floatX() { return x; }

  @ExaminablePropertyGetter(VAL_Y)
  public double floatY() { return y; }

  @ExaminablePropertyGetter(VAL_Z)
  public double floatZ() { return z; }

  @ExaminablePropertyGetter(VAL_PITCH)
  public float pitch() { return pitch; }

  @ExaminablePropertyGetter(VAL_YAW)
  public float yaw() { return yaw; }

  @Override
  public @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of(VAL_WORLD$NAME, worldName),
        ExaminableProperty.of(VAL_X, x),
        ExaminableProperty.of(VAL_Y, y),
        ExaminableProperty.of(VAL_Z, z),
        ExaminableProperty.of(VAL_PITCH, pitch),
        ExaminableProperty.of(VAL_YAW, yaw)
    );
  }

  @Contract("_ -> new")
  public static @NotNull SimplePreciseLocation fromString(String str) {
    return fromDataString(str);
  }

  @Contract("_ -> new")
  public static @NotNull SimplePreciseLocation fromDataString(@NotNull String data) {
    Validate.Arg.notNull(data, "data");
    CommaDataSplitStrategy splitter = new CommaDataSplitStrategy(data, 6);
    String worldName = splitter.nextString();
    double x = splitter.nextDouble();
    double y = splitter.nextDouble();
    double z = splitter.nextDouble();
    float yaw = splitter.nextFloat();
    float pitch = splitter.nextFloat();
    return new SimplePreciseLocation(worldName, x, y, z, pitch, yaw);
  }

  @Override
  public @NotNull String asDataString() {
    return CommaDataSplitStrategy.toString(new Object[]{this.worldName, this.x, this.y, this.z, this.yaw, this.pitch});
  }

}
