package net.aurika.common.data.struct;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class DataString extends DataPart implements DataScalar {

  private final @NotNull String value;

  public DataString(@NotNull String value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public @NotNull String valueObject() { return value; }

  @Override
  public @NotNull DataType type() { return DataType.STRING; }

}
