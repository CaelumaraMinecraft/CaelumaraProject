package net.aurika.util.number;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class _Float extends AbstractFloatingPointNumber {

  private final float value;
  private final @NotNull NumberType type;

  public _Float(float value) {
    this.value = value;
    this.type = NumberType.FLOAT;
    boolean requirement$iv = !Float.isNaN(this.getValue());
    if (!requirement$iv) {
      String var6 = "Value is not finite, but NaN: " + this.getValue();
      throw new NonFiniteNumberException(var6);
    } else {
      requirement$iv = !Float.isInfinite(this.getValue());
      if (!requirement$iv) {
        String var7 = "Value is not finite, but Infinity: " + this.getValue();
        throw new NonFiniteNumberException(var7);
      }
    }
  }

  @NotNull
  public Float getValue() {
    return this.value;
  }

  @NotNull
  public NumberType getType() {
    return this.type;
  }

  public boolean isNegative() {
    return this.getValue() < 0.0F;
  }

  public boolean isPositive() {
    return this.getValue() > 0.0F;
  }

  public boolean isZero() {
    return this.getValue() == 0.0F;
  }

  private float getConvert(AnyNumber $this$convert) {
    return $this$convert.getValue().floatValue();
  }

  @NotNull
  public AnyNumber constructNew(@NotNull Number value) {
    Objects.requireNonNull(value);
    return new _Float(value.floatValue());
  }

  @NotNull
  public AnyNumber unaryMinus() {
    return this.constructNew(-this.getValue());
  }

  @NotNull
  public AnyNumber unaryPlus() {
    return this.constructNew(this.getValue());
  }

  @NotNull
  public AnyNumber inc() {
    return this.constructNew(this.getValue() + 1.0F);
  }

  @NotNull
  public AnyNumber dec() {
    return this.constructNew(this.getValue() - 1.0F);
  }

  @NotNull
  public AnyNumber plus(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    float var10001 = this.getValue();
    return this.constructNew(var10001 + other.getValue().floatValue());
  }

  @NotNull
  public AnyNumber minus(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    float var10001 = this.getValue();
    return this.constructNew(var10001 - other.getValue().floatValue());
  }

  @NotNull
  public AnyNumber times(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    float var10001 = this.getValue();
    return this.constructNew(var10001 * other.getValue().floatValue());
  }

  @NotNull
  public AnyNumber div(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    float var10001 = this.getValue();
    return this.constructNew(var10001 / other.getValue().floatValue());
  }

  @NotNull
  public AnyNumber rem(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    float var10001 = this.getValue();
    return this.constructNew(var10001 % other.getValue().floatValue());
  }

  public int compareTo(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    float var10000 = this.getValue();
    return Float.compare(var10000, other.getValue().floatValue());
  }

}
