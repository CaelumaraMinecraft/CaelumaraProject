package net.aurika.common.nbt;

import net.aurika.validate.Validate;
import net.kyori.adventure.nbt.LongArrayBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagLongArray extends NBTTagArray<long[]> {

  static @NotNull NBTTagLongArray nbtTagLongArray(long @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagLongArrayImpl(value.clone());
  }

  static @NotNull NBTTagLongArray nbtTagLongArrayRaw(long @NotNull [] value) {
    return new NBTTagLongArrayImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagLongArray> nbtTagType() {
    return NBTTagType.LONG_ARRAY;
  }

  @Override
  default long @NotNull [] value() {
    return this.rawValue().clone();
  }

  @Override
  long @NotNull [] rawValue();

  default void value(long @NotNull [] value) {
    this.rawValue(value.clone());
  }

  @Override
  void rawValue(long @NotNull [] value);

  @Override
  default @NotNull LongArrayBinaryTag asBinaryTag() {
    return LongArrayBinaryTag.longArrayBinaryTag(this.rawValue());
  }

}

class NBTTagLongArrayImpl extends NBTTagArrayImpl<long[]> implements NBTTagLongArray {

  private long[] value;

  NBTTagLongArrayImpl(long @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public long @NotNull [] rawValue() {
    return this.value;
  }

  @Override
  public void rawValue(long @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

}
