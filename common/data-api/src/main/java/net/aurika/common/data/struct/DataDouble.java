package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataDouble extends DataPart implements DataNumber {

  private final double value;

  public DataDouble(double value) { this.value = value; }

  public double value() { return value; }

  @Override
  public @NotNull Double valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.DOUBLE; }

}
