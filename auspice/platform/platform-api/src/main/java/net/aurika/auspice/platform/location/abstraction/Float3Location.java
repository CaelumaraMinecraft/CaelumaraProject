package net.aurika.auspice.platform.location.abstraction;

import net.aurika.auspice.platform.location.vector.Vector3;
import net.aurika.auspice.platform.world.World;
import net.aurika.auspice.platform.world.WorldAware;
import net.aurika.common.validate.Validate;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public interface Float3Location extends WorldAware, Float3Pos {

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location add(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location subtract(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location multiply(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location divide(@NotNull Vector3 vec);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location add(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location subtract(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location multiply(@NotNull Float3Pos pos);

  @Override
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Location divide(@NotNull Float3Pos pos);

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.concat(
        Stream.of(ExaminableProperty.of("world", world())),  // world
        Float3Pos.super.examinableProperties()                     // x, y, z
    );
  }

  @Override
  public @NotNull World world();

  @Override
  public double floatX();

  @Override
  public double floatY();

  @Override
  public double floatZ();

}

final class Float3LocationImpl implements Float3Location {

  private final @NotNull World world;
  private final double x;
  private final double y;
  private final double z;

  public Float3LocationImpl(@NotNull World world, double x, double y, double z) {
    Validate.Arg.notNull(world, "world");
    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Float3Location add(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new Float3LocationImpl(world, x + vec.vectorX(), y + vec.vectorY(), z + vec.vectorZ());
  }

  @Override
  public @NotNull Float3Location subtract(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new Float3LocationImpl(world, x - vec.vectorX(), y - vec.vectorY(), z - vec.vectorZ());
  }

  @Override
  public @NotNull Float3Location multiply(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new Float3LocationImpl(world, x * vec.vectorX(), y * vec.vectorY(), z * vec.vectorZ());
  }

  @Override
  public @NotNull Float3Location divide(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vec");
    return new Float3LocationImpl(world, x / vec.vectorX(), y / vec.vectorY(), z / vec.vectorZ());
  }

  @Override
  public @NotNull Float3Location add(@NotNull Float3Pos pos) {
    Validate.Arg.notNull(pos, "pos");
    return new Float3LocationImpl(world, x + pos.floatX(), y + pos.floatY(), z + pos.floatZ());
  }

  @Override
  public @NotNull Float3Location subtract(@NotNull Float3Pos pos) {
    Validate.Arg.notNull(pos, "pos");
    return new Float3LocationImpl(world, x - pos.floatX(), y - pos.floatY(), z - pos.floatZ());
  }

  @Override
  public @NotNull Float3Location multiply(@NotNull Float3Pos pos) {
    Validate.Arg.notNull(pos, "pos");
    return new Float3LocationImpl(world, x - pos.floatX(), y * pos.floatY(), z * pos.floatZ());
  }

  @Override
  public @NotNull Float3Location divide(@NotNull Float3Pos pos) {
    Validate.Arg.notNull(pos, "pos");
    return new Float3LocationImpl(world, x / pos.floatX(), y / pos.floatY(), z / pos.floatZ());
  }

  @Override
  public @NotNull World world() {
    return this.world;
  }

  @Override
  public double floatX() {
    return this.x;
  }

  @Override
  public double floatY() {
    return this.y;
  }

  @Override
  public double floatZ() {
    return this.z;
  }

  @Override
  public int hashCode() {
    return Objects.hash(world, x, y, z);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Float3Location)) return false;
    Float3Location that = (Float3Location) o;
    return Double.compare(x, that.floatX()) == 0
        && Double.compare(y, that.floatY()) == 0
        && Double.compare(z, that.floatZ()) == 0
        && Objects.equals(world, that.world());
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}
