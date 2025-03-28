package net.aurika.util.number;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class _Double extends AbstractFloatingPointNumber {

  private final double value;
  private final @NotNull NumberType type;

  public _Double(double value) {
    this.value = value;
    this.type = NumberType.DOUBLE;
    boolean requirement$iv = !Double.isNaN(this.getValue());
    if (!requirement$iv) {
      String var7 = "Value is not finite, but NaN: " + this.getValue();
      throw new NonFiniteNumberException(var7);
    } else {
      requirement$iv = !Double.isInfinite(this.getValue());
      if (!requirement$iv) {
        String var8 = "Value is not finite, but Infinity: " + this.getValue();
        throw new NonFiniteNumberException(var8);
      }
    }
  }

  public @NotNull Double getValue() {
    return this.value;
  }

  public @NotNull NumberType getType() {
    return this.type;
  }

  public boolean isNegative() {
    return this.getValue() < 0.0;
  }

  public boolean isPositive() {
    return this.getValue() > 0.0;
  }

  public boolean isZero() {
    return this.getValue() == 0.0;
  }

  private double getConvert(AnyNumber $this$convert) {
    return $this$convert.getValue().doubleValue();
  }

  public @NotNull AnyNumber constructNew(@NotNull Number value) {
    Objects.requireNonNull(value, "value");
    return new _Double(value.doubleValue());
  }

  public @NotNull AnyNumber unaryMinus() {
    return this.constructNew(-this.getValue());
  }

  public @NotNull AnyNumber unaryPlus() {
    return this.constructNew(this.getValue());
  }

  public @NotNull AnyNumber inc() {
    return this.constructNew(this.getValue() + 1.0);
  }

  public @NotNull AnyNumber dec() {
    return this.constructNew(this.getValue() - 1.0);
  }

  public @NotNull AnyNumber plus(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    double var10001 = this.getValue();
    return this.constructNew(var10001 + other.getValue().doubleValue());
  }

  public @NotNull AnyNumber minus(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    double var10001 = this.getValue();
    return this.constructNew(var10001 - other.getValue().doubleValue());
  }

  public @NotNull AnyNumber times(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    double var10001 = this.getValue();
    return this.constructNew(var10001 * other.getValue().doubleValue());
  }

  public @NotNull AnyNumber div(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    double var10001 = this.getValue();
    return this.constructNew(var10001 / other.getValue().doubleValue());
  }

  public @NotNull AnyNumber rem(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    double var10001 = this.getValue();
    return this.constructNew(var10001 % other.getValue().doubleValue());
  }

  public int compareTo(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    double var10000 = this.getValue();
    return Double.compare(var10000, other.getValue().doubleValue());
  }

}
