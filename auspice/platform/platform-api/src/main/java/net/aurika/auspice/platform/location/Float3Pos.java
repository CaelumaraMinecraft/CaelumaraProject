package net.aurika.auspice.platform.location;

import net.aurika.common.examination.ExaminablePropertyGetter;
import net.aurika.common.examination.reflection.ExaminableConstructor;
import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public interface Float3Pos extends FloatXAware, FloatYAware, FloatZAware, Examinable {

  String VAL_X = "x";
  String VAL_Y = "y";
  String VAL_Z = "z";

  @Contract(value = "_, _, _ -> new", pure = true)
  @ExaminableConstructor(publicType = Float3Pos.class, properties = {VAL_X, VAL_Y, VAL_Z})
  static @NotNull Float3Pos float3Pos(double x, double y, double z) { return new Float3PosImpl(x, y, z); }

  @Override
  @ExaminablePropertyGetter(VAL_X)
  double floatX();

  @Override
  @ExaminablePropertyGetter(VAL_Y)
  double floatY();

  @Override
  @ExaminablePropertyGetter(VAL_Z)
  double floatZ();

  // ======== Vector Operations ========
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos add(@NotNull Vector3 vec);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos subtract(@NotNull Vector3 vec);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos multiply(@NotNull Vector3 vec);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos divide(@NotNull Vector3 vec);

  // ======== Float3Pos Operations =======
  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos add(@NotNull Float3Pos pos);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos subtract(@NotNull Float3Pos pos);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos multiply(@NotNull Float3Pos pos);

  @Contract(value = "_ -> new", pure = true)
  @NotNull Float3Pos divide(@NotNull Float3Pos pos);

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", this.floatX()),
        ExaminableProperty.of("y", this.floatY()),
        ExaminableProperty.of("z", this.floatZ())
    );
  }

  public static final class NaturalComparator implements Comparator<Float3Pos> {

    public static final NaturalComparator INSTANCE = new NaturalComparator();

    private NaturalComparator() { }

    @Override
    public int compare(@NotNull Float3Pos first, @NotNull Float3Pos second) {
      Validate.Arg.notNull(first, "first");
      Validate.Arg.notNull(second, "second");
      int y = Double.compare(first.floatY(), second.floatY());
      if (y != 0) {
        return y;
      } else {
        int x = Double.compare(first.floatX(), second.floatX());
        if (x != 0) {
          return x;
        } else {
          int z = Double.compare(first.floatZ(), second.floatZ());
          return z;
        }
      }
    }

  }

}

final class Float3PosImpl implements Float3Pos {

  private final double x;
  private final double y;
  private final double z;

  Float3PosImpl(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  @Override
  public @NotNull Float3Pos add(@NotNull Vector3 vec) {
    Validate.Arg.notNull(vec, "vector");
    return new Float3PosImpl(x + vec.vectorX(), y + vec.vectorY(), z + vec.vectorZ());
  }

  @Override
  public @NotNull Float3Pos subtract(@NotNull Vector3 vec) {
    return new Float3PosImpl(x - vec.vectorX(), y - vec.vectorY(), z - vec.vectorZ());
  }

  @Override
  public @NotNull Float3Pos multiply(@NotNull Vector3 vec) {
    return new Float3PosImpl(x * vec.vectorX(), y * vec.vectorY(), z * vec.vectorZ());
  }

  @Override
  public @NotNull Float3Pos divide(@NotNull Vector3 vec) {
    return new Float3PosImpl(x / vec.vectorX(), y / vec.vectorY(), z / vec.vectorZ());
  }

  @Override
  public @NotNull Float3Pos add(@NotNull Float3Pos pos) {
    return new Float3PosImpl(x + pos.floatX(), y + pos.floatY(), z + pos.floatZ());
  }

  @Override
  public @NotNull Float3Pos subtract(@NotNull Float3Pos pos) {
    return new Float3PosImpl(x - pos.floatX(), y - pos.floatY(), z - pos.floatZ());
  }

  @Override
  public @NotNull Float3Pos multiply(@NotNull Float3Pos pos) {
    return new Float3PosImpl(x * pos.floatX(), y * pos.floatY(), z * pos.floatZ());
  }

  @Override
  public @NotNull Float3Pos divide(@NotNull Float3Pos pos) {
    return new Float3PosImpl(x / pos.floatX(), y / pos.floatY(), z / pos.floatZ());
  }

  @Override
  public double floatX() { return this.x; }

  @Override
  public double floatY() { return this.y; }

  @Override
  public double floatZ() { return this.z; }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Float3PosImpl)) return false;
    Float3PosImpl float3D = (Float3PosImpl) o;
    return Double.compare(x, float3D.x) == 0
        && Double.compare(y, float3D.y) == 0
        && Double.compare(z, float3D.z) == 0;
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}
