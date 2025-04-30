package net.aurika.auspice.platform.location.abstraction;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import net.kyori.examination.string.StringExaminer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.stream.Stream;

public interface Float2Pos extends FloatXAware, FloatZAware, Examinable {

  @Contract(value = "_, _ -> new", pure = true)
  static @NotNull Float2Pos float2Pos(double x, double z) { return new Float2PosImpl(x, z); }

  static @NotNull Float2Pos zero() { return Float2PosImpl.ZERO; }

  @Override
  double floatX();

  @Override
  double floatZ();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return Stream.of(
        ExaminableProperty.of("x", floatX()),
        ExaminableProperty.of("z", floatZ())
    );
  }

}

final class Float2PosImpl implements Float2Pos {

  static final Float2Pos ZERO = new Float2PosImpl(0.0, 0.0);

  private final double x;
  private final double z;

  public Float2PosImpl(double x, double z) {
    this.x = x;
    this.z = z;
  }

  @Override
  public double floatX() { return this.x; }

  @Override
  public double floatZ() { return this.z; }

  @Override
  public int hashCode() {
    return Objects.hash(x, z);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Float2PosImpl)) return false;
    Float2PosImpl that = (Float2PosImpl) obj;
    return Double.compare(x, that.x) == 0 && Double.compare(z, that.z) == 0;
  }

  @Override
  public @NotNull String toString() {
    return StringExaminer.simpleEscaping().examine(this);
  }

}