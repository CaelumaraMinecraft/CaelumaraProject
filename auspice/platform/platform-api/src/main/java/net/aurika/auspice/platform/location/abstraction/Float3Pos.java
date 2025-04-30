package net.aurika.auspice.platform.location.abstraction;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public interface Float3Pos extends FloatXAware, FloatYAware, FloatZAware, Examinable {

  @Override
  double floatX();

  @Override
  double floatY();

  @Override
  double floatZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", this.floatX()),
        ExaminableProperty.of("y", this.floatY()),
        ExaminableProperty.of("z", this.floatZ())
    );
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
  public double floatX() { return this.x; }

  @Override
  public double floatY() { return this.y; }

  @Override
  public double floatZ() { return this.z; }

  @Override
  public int hashCode() { return Objects.hash(x, y, z); }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Float3PosImpl)) return false;
    Float3PosImpl float3D = (Float3PosImpl) o;
    return Double.compare(x, float3D.x) == 0
        && Double.compare(y, float3D.y) == 0
        && Double.compare(z, float3D.z) == 0;
  }

  @Override
  public @NotNull String toString() { return StringExaminer.simpleEscaping().examine(this); }

}
