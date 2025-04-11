package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataShort extends DataPart {

  private final short value;

  public DataShort(short value) { this.value = value; }

  public short value() { return value; }

  @Override
  public @NotNull Short valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.SHORT; }

}
