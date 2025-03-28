package net.aurika.util.number;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class _Int extends AbstractAnyNumber {

  private final int value;
  private final @NotNull NumberType type;

  public _Int(int value) {
    this.value = value;
    this.type = NumberType.INT;
  }

  public @NotNull Integer getValue() {
    return this.value;
  }

  public @NotNull NumberType getType() {
    return this.type;
  }

  public boolean isNegative() {
    return this.getValue() < 0;
  }

  public boolean isPositive() {
    return this.getValue() > 0;
  }

  public boolean isZero() {
    return this.getValue() == 0;
  }

  private int getConvert(AnyNumber $this$convert) {
    return $this$convert.getValue().intValue();
  }

  public @NotNull AnyNumber constructNew(@NotNull Number value) {
    Objects.requireNonNull(value);
    return new _Int(value.intValue());
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
    return this.constructNew(this.getValue() + 1);
  }

  @NotNull
  public AnyNumber dec() {
    return this.constructNew(this.getValue() - 1);
  }

  @NotNull
  public AnyNumber plus(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    int var10001 = this.getValue();
    return this.constructNew(var10001 + other.getValue().intValue());
  }

  @NotNull
  public AnyNumber minus(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    int var10001 = this.getValue();
    return this.constructNew(var10001 - other.getValue().intValue());
  }

  @NotNull
  public AnyNumber times(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    int var10001 = this.getValue();
    return this.constructNew(var10001 * other.getValue().intValue());
  }

  @NotNull
  public AnyNumber div(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    int var10001 = this.getValue();
    return this.constructNew(var10001 / other.getValue().intValue());
  }

  @NotNull
  public AnyNumber rem(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    int var10001 = this.getValue();
    return this.constructNew(var10001 % other.getValue().intValue());
  }

  public int compareTo(@NotNull AnyNumber other) {
    Objects.requireNonNull(other);
    int var10000 = this.getValue();
    return Integer.compare(var10000, other.getValue().intValue());
  }

}
