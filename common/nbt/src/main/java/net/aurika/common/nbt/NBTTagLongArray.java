package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.LongArrayBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagLongArray extends NBTTagArray<long[]> {

  @Contract("_ -> new")
  static @NotNull NBTTagLongArray nbtTagLongArray(@NotNull LongArrayBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagLongArraySynced(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagLongArray nbtTagLongArrayCloned(long @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagLongArrayImpl(value.clone());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagLongArray nbtTagLongArraySynced(long @NotNull [] value) {
    return new NBTTagLongArrayImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagLongArray> nbtTagType() { return NBTTagType.LONG_ARRAY; }

  @Override
  long @NotNull [] valueCopy();

  @Override
  long @NotNull [] valueRaw();

  void valueCopy(long @NotNull [] value);

  @Override
  void valueRaw(long @NotNull [] value);

  @Override
  @NotNull LongArrayBinaryTag asBinaryTag();

}

class NBTTagLongArrayImpl extends NBTTagArrayImpl<long[]> implements NBTTagLongArray {

  private long[] value;

  NBTTagLongArrayImpl(long @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public long @NotNull [] valueRaw() { return this.value; }

  @Override
  public long @NotNull [] valueCopy() {
    return this.value.clone();
  }

  @Override
  public void valueRaw(long @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public void valueCopy(long @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.valueRaw(value.clone());
  }

  @Override
  public @NotNull LongArrayBinaryTag asBinaryTag() {
    return LongArrayBinaryTag.longArrayBinaryTag(this.valueRaw());
  }

}
