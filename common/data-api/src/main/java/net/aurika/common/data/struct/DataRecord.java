package net.aurika.common.data.struct;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public final class DataRecord extends DataPart implements DataCollection<DataRecord.Component> {

  private final @NotNull Component @NotNull [] value;

  public DataRecord(@NotNull Component @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value.clone();
  }

  @Override
  public int size() {
    return value.length;
  }

  @Override
  public @NotNull DataRecord.Component get(int index) throws IndexOutOfBoundsException {
    return value[index];
  }

  @Override
  public @NotNull Iterator<Component> iterator() {
    return new Itr();
  }

  public @NotNull DataPart get(@NotNull String name) throws NoSuchSubPartException {
    Validate.Arg.notNull(name, "name");
    for (int i = 0; i < value.length; i++) {
      if (value[i].name().equals(name)) {
        return value[i].part;
      }
    }
    throw new NoSuchSubPartException(name);
  }

  public @Nullable DataPart find(@NotNull String name) {
    Validate.Arg.notNull(name, "name");
    for (int i = 0; i < value.length; i++) {
      if (value[i].name().equals(name)) {
        return value[i].part;
      }
    }
    return null;
  }

  @Override
  public @NotNull Component @NotNull [] valueObject() { return this.value.clone(); }

  @Override
  public @NotNull DataType type() {
    return DataType.RECORD;
  }

  public final class Component {

    private final int index;
    private final @NotNull String name;
    private final @NotNull DataPart part;

    public Component(int index, @NotNull String name, @NotNull DataPart part) {
      Validate.Arg.notNull(name, "name");
      Validate.Arg.notNull(part, "part");
      this.index = index;
      this.name = name;
      this.part = part;
    }

    public int index() {
      return this.index;
    }

    public @NotNull String name() {
      return this.name;
    }

    public @NotNull DataPart part() {
      return this.part;
    }

    public @NotNull DataRecord record() {
      return DataRecord.this;
    }

  }

  public class Itr implements java.util.Iterator<@NotNull Component> {

    private int index = 0;

    @Override
    public boolean hasNext() {
      return index < DataRecord.this.size();
    }

    @Override
    public @NotNull Component next() {
      return DataRecord.this.value[index++];
    }

  }

}
