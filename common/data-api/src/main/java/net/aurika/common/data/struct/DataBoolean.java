package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public final class DataBoolean extends DataPart implements DataScalar {

  public static final DataBoolean TRUE = new DataBoolean(true);
  public static final DataBoolean FALSE = new DataBoolean(false);

  private final boolean value;

  private DataBoolean(boolean value) { this.value = value; }

  public boolean value() { return value; }

  @Override
  public @NotNull Boolean valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.BOOLEAN; }

}
