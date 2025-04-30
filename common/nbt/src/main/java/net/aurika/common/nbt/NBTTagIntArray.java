package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.IntArrayBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagIntArray extends NBTTagArray<int[]> {

  @Contract("_ -> new")
  static @NotNull NBTTagIntArray nbtTagIntArray(@NotNull IntArrayBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagIntArraySynced(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagIntArray nbtTagIntArrayCloned(int @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagIntArrayImpl(value.clone());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagIntArray nbtTagIntArraySynced(int @NotNull ... value) {
    return new NBTTagIntArrayImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagIntArray> nbtTagType() {
    return NBTTagType.INT_ARRAY;
  }

  @Override
  int @NotNull [] valueRaw();

  @Override
  int @NotNull [] valueCopy();

  @Override
  void valueRaw(int @NotNull [] valueObject);

  @Override
  void valueCopy(int @NotNull [] value);

  @Override
  @NotNull IntArrayBinaryTag asBinaryTag();

}

class NBTTagIntArrayImpl extends NBTTagArrayImpl<int[]> implements NBTTagIntArray {

  private int[] value;

  NBTTagIntArrayImpl(int[] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public int @NotNull [] valueRaw() {
    return this.value;
  }

  @Override
  public int @NotNull [] valueCopy() {
    return this.value.clone();
  }

  @Override
  public void valueRaw(int @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public void valueCopy(int @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value.clone();
  }

  @Override
  public @NotNull IntArrayBinaryTag asBinaryTag() {
    return IntArrayBinaryTag.intArrayBinaryTag(this.valueRaw());
  }

}
