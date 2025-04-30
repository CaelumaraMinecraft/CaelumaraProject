package net.aurika.common.nbt;

import net.kyori.adventure.nbt.NumberBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagNumber extends NBTTag {

  default void value(@NotNull Number value) {
    if (this instanceof NBTTagByte) {
      ((NBTTagByte) this).value(value.byteValue());
    }
  }

  @Override
  @NotNull Number valueAsObject();

  @Override
  @NotNull NumberBinaryTag asBinaryTag();

}

abstract class NBTTagNumberImpl extends NBTTagImpl implements NBTTagNumber { }
