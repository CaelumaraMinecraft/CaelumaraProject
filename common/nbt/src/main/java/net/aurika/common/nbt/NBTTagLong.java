package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.LongBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagLong extends NBTTagNumber {

  @Contract("_ -> new")
  static @NotNull NBTTagLong nbtTagLong(@NotNull LongBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagLong(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagLong nbtTagLong(long value) { return new NBTTagLongImpl(value); }

  @Override
  default @NotNull NBTTagType<NBTTagLong> nbtTagType() { return NBTTagType.LONG; }

  long value();

  void value(long value);

  @Override
  @NotNull Long valueAsObject();

  @Override
  @NotNull LongBinaryTag asBinaryTag();

}

class NBTTagLongImpl extends NBTTagNumberImpl implements NBTTagLong {

  private long value;

  NBTTagLongImpl(long value) {
    this.value = value;
  }

  @Override
  public void value(long value) {
    this.value = value;
  }

  @Override
  public long value() {
    return this.value;
  }

  @Override
  public @NotNull Long valueAsObject() { return this.value; }

  @Override
  public @NotNull LongBinaryTag asBinaryTag() {
    return LongBinaryTag.longBinaryTag(this.value());
  }

}
