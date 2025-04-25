package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public interface Location extends WorldAware, Float3Pos, Directional, Examinable {

  String VAL_WORLD = "world";

  @Contract("_, _, _, _, _, _ -> new")
  @ExaminableConstructor(publicType = Location.class, properties = {VAL_WORLD, VAL_X, VAL_Y, VAL_Z, VAL_PITCH, VAL_YAW})
  static @NotNull Location location(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    return new LocationImpl(world, x, y, z, pitch, yaw);
  }

  @Contract("_ -> new")
  static @NotNull Location zero(@NotNull World world) {
    return location(world, 0.0, 0.0, 0.0, 0f, 0f);
  }

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location add(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location subtract(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location multiply(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location divide(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location add(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location subtract(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location multiply(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Location divide(@NotNull Float3Pos pos);

  @Override
  @ExaminablePropertyGetter(VAL_WORLD)
  @NotNull World world();

  @Override
  @ExaminablePropertyGetter(VAL_X)
  double floatX();

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  double floatY();

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  double floatZ();

  @Override
  @ExaminablePropertyGetter(VAL_PITCH)
  float pitch();

  @Override
  @ExaminablePropertyGetter(VAL_YAW)
  float yaw();

  @Override
  default @NotNull String examinableName() {
    return Location.class.getSimpleName();
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.concat(
        Stream.of(ExaminableProperty.of("world", world())  // world
        ), Stream.concat(
            Float3Pos.super.examinableProperties(),              // x, y, x
            Directional.super.examinableProperties()             // pitch, yaw
        )
    );
  }

}

final class LocationImpl implements Location {

  private final @NotNull World world;
  private final double x;
  private final double y;
  private final double z;
  private final float pitch;
  private final float yaw;

  LocationImpl(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public @NotNull Location add(@NotNull Vector3 vec) {
    return new LocationImpl(world, x + vec.vectorX(), y + vec.vectorY(), z + vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location subtract(@NotNull Vector3 vec) {
    return new LocationImpl(world, x - vec.vectorX(), y - vec.vectorY(), z - vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location multiply(@NotNull Vector3 vec) {
    return new LocationImpl(world, x * vec.vectorX(), y * vec.vectorY(), z * vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location divide(@NotNull Vector3 vec) {
    return new LocationImpl(world, x / vec.vectorX(), y / vec.vectorY(), z / vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location add(@NotNull Float3Pos pos) {
    return new LocationImpl(world, x + pos.floatX(), y + pos.floatY(), z + pos.floatZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location subtract(@NotNull Float3Pos pos) {
    return new LocationImpl(world, x - pos.floatX(), y - pos.floatY(), z - pos.floatZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location multiply(@NotNull Float3Pos pos) {
    return new LocationImpl(world, x * pos.floatX(), y - pos.floatY(), z * pos.floatZ(), pitch, yaw);
  }

  @Override
  public @NotNull Location divide(@NotNull Float3Pos pos) {
    return new LocationImpl(world, x / pos.floatX(), y / pos.floatY(), z / pos.floatZ(), pitch, yaw);
  }

  @Override
  public @NotNull World world() { return world; }

  @Override
  public double floatX() { return x; }

  @Override
  public double floatY() { return y; }

  @Override
  public double floatZ() { return z; }

  @Override
  public float pitch() { return pitch; }

  @Override
  public float yaw() { return yaw; }

  @Override
  public int hashCode() {
    return Objects.hash(world(), x, y, z, pitch, yaw);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Location)) return false;
    Location location = (Location) o;
    return Double.compare(x, location.floatX()) == 0 && Double.compare(y, location.floatY()) == 0 && Double.compare(
        z, location.floatZ()) == 0 && Float.compare(pitch, location.pitch()) == 0 && Float.compare(
        yaw, location.yaw()) == 0 && Objects.equals(world, location.world());
  }

  @Override
  public String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}
