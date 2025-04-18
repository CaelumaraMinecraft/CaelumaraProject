package net.aurika.common.data.struct;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public final class DataArray extends DataPart implements DataCollection<@NotNull DataPart> {

  private final @NotNull DataPart @NotNull [] value;

  public DataArray(@NotNull DataPart @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value.clone();
  }

  @Override
  public int size() { return value.length; }

  @Override
  public @NotNull DataPart get(int index) throws IndexOutOfBoundsException {
    return value[index];
  }

  @Override
  public @NotNull Iterator<@NotNull DataPart> iterator() { return new Itr(); }

  public @NotNull DataPart @NotNull [] value() { return value.clone(); }

  @Override
  public @NotNull DataPart @NotNull [] valueObject() { return value(); }

  @Override
  public @NotNull DataType type() { return DataType.ARRAY; }

  final class Itr implements java.util.Iterator<@NotNull DataPart> {

    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < DataArray.this.size();
    }

    @Override
    public @NotNull DataPart next() {
      return DataArray.this.value[index++];
    }

  }

}
