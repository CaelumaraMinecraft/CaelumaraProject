package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataByte extends DataPart implements DataNumber {

  private final byte value;

  public DataByte(byte value) { this.value = value; }

  public byte value() { return value; }

  @Override
  public @NotNull Byte valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.BYTE; }

}
