package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataInt extends DataPart implements DataNumber {

  private final int value;

  public DataInt(int value) { this.value = value; }

  public int value() { return value; }

  @Override
  public @NotNull Integer valueObject() { return value; }

  @Override
  public @NotNull DataType type() {
    return DataType.INT;
  }

}
