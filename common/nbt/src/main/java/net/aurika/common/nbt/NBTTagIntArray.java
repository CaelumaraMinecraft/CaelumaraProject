package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagIntArray extends NBTTagArray<int[]> {

  static @NotNull NBTTagIntArray nbtTagIntArray(int @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagIntArrayImpl(value.clone());
  }

  static @NotNull NBTTagIntArray nbtTagIntArrayRaw(int @NotNull ... value) {
    return new NBTTagIntArrayImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagIntArray> nbtTagType() {
    return NBTTagType.INT_ARRAY;
  }

  @Override
  default int @NotNull [] value() {
    return this.rawValue().clone();
  }

  @Override
  int @NotNull [] rawValue();

  @Override
  default void value(int @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.rawValue(value.clone());
  }

  @Override
  void rawValue(int @NotNull [] valueObject);

  @Override
  default @NotNull IntArrayBinaryTag asBinaryTag() {
    return IntArrayBinaryTag.intArrayBinaryTag(this.rawValue());
  }

}

class NBTTagIntArrayImpl extends NBTTagArrayImpl<int[]> implements NBTTagIntArray {

  private int[] value;

  NBTTagIntArrayImpl(int[] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public int @NotNull [] rawValue() {
    return this.value;
  }

  @Override
  public void rawValue(int @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

}
