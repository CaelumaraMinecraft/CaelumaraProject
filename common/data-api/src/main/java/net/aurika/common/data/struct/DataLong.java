package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataLong extends DataPart implements DataNumber {

  private final long value;

  public DataLong(long value) { this.value = value; }

  public long value() { return value; }

  @Override
  public @NotNull Long valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.LONG; }

}
