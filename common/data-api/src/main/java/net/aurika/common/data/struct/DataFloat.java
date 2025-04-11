package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataFloat extends DataPart implements DataNumber {

  private final float value;

  public DataFloat(float value) { this.value = value; }

  public float value() { return value; }

  @Override
  public @NotNull Float valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.FLOAT; }

}
