package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataChar extends DataPart implements DataScalar {

  private final char value;

  public DataChar(char value) { this.value = value; }

  public char value() { return value; }

  @Override
  public @NotNull Character valueObject() {
    return value;
  }

  @Override
  public @NotNull DataType type() {
    return DataType.CHAR;
  }

}
