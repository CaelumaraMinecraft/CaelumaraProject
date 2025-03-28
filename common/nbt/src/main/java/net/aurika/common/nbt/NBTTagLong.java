package net.aurika.common.nbt;

import net.kyori.adventure.nbt.LongBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagLong extends NBTTagNumber {

  static @NotNull NBTTagLong nbtTagLong(long value) {
    return new NBTTagLongImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagLong> nbtTagType() {
    return NBTTagType.LONG;
  }

  @Override
  default @NotNull Long valueAsObject() {
    return this.value();
  }

  void value(long value);

  long value();

  @Override
  default @NotNull LongBinaryTag asBinaryTag() {
    return LongBinaryTag.longBinaryTag(this.value());
  }

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

}
