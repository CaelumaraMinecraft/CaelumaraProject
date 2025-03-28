package net.aurika.common.nbt;

import net.kyori.adventure.nbt.IntBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagInt extends NBTTagNumber {

  static @NotNull NBTTagInt nbtTagInt(int value) {
    return new NBTTagIntImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagInt> nbtTagType() {
    return NBTTagType.INT;
  }

  @Override
  default @NotNull Integer valueAsObject() {
    return this.value();
  }

  void value(int value);

  int value();

  @Override
  default @NotNull IntBinaryTag asBinaryTag() {
    return IntBinaryTag.intBinaryTag(this.value());
  }

}

class NBTTagIntImpl extends NBTTagImpl implements NBTTagInt {

  private int value;

  NBTTagIntImpl(int value) {
    this.value = value;
  }

  @Override
  public void value(int value) {
    this.value = value;
  }

  @Override
  public int value() {
    return this.value;
  }

}
