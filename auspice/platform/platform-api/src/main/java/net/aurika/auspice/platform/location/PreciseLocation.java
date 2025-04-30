package net.aurika.auspice.platform.location;

import net.aurika.auspice.platform.location.direction.Directional;
import net.aurika.auspice.platform.location.floating.Float3Pos;
import net.aurika.auspice.platform.location.vector.Vector3;
import net.aurika.auspice.platform.world.World;
import net.aurika.common.annotation.data.Immutable;
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

import static net.aurika.auspice.platform.location.LocationProperties.*;

@Immutable
public interface PreciseLocation extends PreciseLocationAware, Float3Pos, Directional, Examinable {

  @Contract("_ -> new")
  static @NotNull PreciseLocation preciseLocation(@NotNull PreciseLocationAware precise) {
    Validate.Arg.notNull(precise, "precise");
    return PreciseLocation.preciseLocation(
        precise.world(),                                               // world
        precise.preciseX(), precise.preciseY(), precise.preciseZ(),    // position
        precise.pitch(), precise.yaw()                                 // direction
    );
  }

  @Contract("_, _, _, _, _, _ -> new")
  @ExaminableConstructor(publicType = PreciseLocation.class, properties = {VAL_WORLD, VAL_X, VAL_Y, VAL_Z, VAL_PITCH, VAL_YAW})
  static @NotNull PreciseLocation preciseLocation(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    return new PreciseLocationImpl(world, x, y, z, pitch, yaw);
  }

  @Contract("_ -> new")
  static @NotNull PreciseLocation zero(@NotNull World world) {
    return preciseLocation(world, 0.0, 0.0, 0.0, 0f, 0f);
  }

  @Contract(value = "_ -> new", pure = true)
  @NotNull PreciseLocation add(@NotNull Vector3 vec);

  @Contract(value = "_ -> new", pure = true)
  @NotNull PreciseLocation subtract(@NotNull Vector3 vec);

  @Contract(value = "_ -> new", pure = true)
  @NotNull PreciseLocation multiply(@NotNull Vector3 vec);

  @Contract(value = "_ -> new", pure = true)
  @NotNull PreciseLocation divide(@NotNull Vector3 vec);

  @Override
  @ExaminablePropertyGetter(VAL_WORLD)
  @NotNull World world();

  @Override
  default double floatX() { return preciseX(); }

  @ExaminablePropertyGetter(VAL_X)
  double preciseX();

  @Override
  default double floatY() { return preciseY(); }

  @ExaminablePropertyGetter(VAL_Y)
  double preciseY();

  @Override
  default double floatZ() { return preciseZ(); }

  @ExaminablePropertyGetter(VAL_Z)
  double preciseZ();

  @Override
  @ExaminablePropertyGetter(VAL_PITCH)
  float pitch();

  @Override
  @ExaminablePropertyGetter(VAL_YAW)
  float yaw();

  @Override
  default @NotNull String examinableName() {
    return PreciseLocation.class.getSimpleName();
  }

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.concat(
        Stream.of(ExaminableProperty.of(VAL_WORLD, world())      // world
        ), Stream.concat(
            Float3Pos.super.examinableProperties(),              // x, y, x
            Directional.super.examinableProperties()             // pitch, yaw
        )
    );
  }

}

final class PreciseLocationImpl implements PreciseLocation {

  private final @NotNull World world;
  private final double x;
  private final double y;
  private final double z;
  private final float pitch;
  private final float yaw;

  PreciseLocationImpl(@NotNull World world, double x, double y, double z, float pitch, float yaw) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
    this.pitch = pitch;
    this.yaw = yaw;
  }

  @Override
  public @NotNull PreciseLocation add(@NotNull Vector3 vec) {
    return new PreciseLocationImpl(world, x + vec.vectorX(), y + vec.vectorY(), z + vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull PreciseLocation subtract(@NotNull Vector3 vec) {
    return new PreciseLocationImpl(world, x - vec.vectorX(), y - vec.vectorY(), z - vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull PreciseLocation multiply(@NotNull Vector3 vec) {
    return new PreciseLocationImpl(world, x * vec.vectorX(), y * vec.vectorY(), z * vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull PreciseLocation divide(@NotNull Vector3 vec) {
    return new PreciseLocationImpl(world, x / vec.vectorX(), y / vec.vectorY(), z / vec.vectorZ(), pitch, yaw);
  }

  @Override
  public @NotNull World world() { return world; }

  @Override
  public double preciseX() { return x; }

  @Override
  public double preciseY() { return y; }

  @Override
  public double preciseZ() { return z; }

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
    if (!(o instanceof PreciseLocation)) return false;
    PreciseLocation location = (PreciseLocation) o;
    return Double.compare(x, location.floatX()) == 0 && Double.compare(y, location.floatY()) == 0 && Double.compare(
        z, location.floatZ()) == 0 && Float.compare(pitch, location.pitch()) == 0 && Float.compare(
        yaw, location.yaw()) == 0 && Objects.equals(world, location.world());
  }

  @Override
  public String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}
