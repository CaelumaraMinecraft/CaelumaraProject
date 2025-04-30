package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.IntBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagInt extends NBTTagNumber {

  @Contract("_ -> new")
  static @NotNull NBTTagInt nbtTagInt(@NotNull IntBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagInt(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagInt nbtTagInt(int value) { return new NBTTagIntImpl(value); }

  @Override
  default @NotNull NBTTagType<NBTTagInt> nbtTagType() { return NBTTagType.INT; }

  int value();

  void value(int value);

  @Override
  @NotNull Integer valueAsObject();

  @Override
  @NotNull IntBinaryTag asBinaryTag();

}

class NBTTagIntImpl extends NBTTagImpl implements NBTTagInt {

  private int value;

  NBTTagIntImpl(int value) { this.value = value; }

  @Override
  public int value() { return this.value; }

  @Override
  public void value(int value) { this.value = value; }

  @Override
  public @NotNull Integer valueAsObject() { return this.value; }

  @Override
  public @NotNull IntBinaryTag asBinaryTag() {
    return IntBinaryTag.intBinaryTag(this.value());
  }

}
