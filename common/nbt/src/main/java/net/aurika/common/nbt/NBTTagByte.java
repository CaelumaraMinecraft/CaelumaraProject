package net.aurika.common.nbt;

import net.kyori.adventure.nbt.ByteBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagByte extends NBTTagNumber {

  static @NotNull NBTTagByte nbtTagByte(byte value) {
    return new NBTTagByteImpl(value);
  }

  static @NotNull NBTTagByte nbtTagBool(boolean value) {
    return new NBTTagByteImpl((byte) (value ? 1 : 0));
  }

  @Override
  default @NotNull NBTTagType<NBTTagByte> nbtTagType() {
    return NBTTagType.BYTE;
  }

  @Override
  default @NotNull Byte valueAsObject() {
    return this.value();
  }

  /**
   * Sets the value.
   */
  void value(byte value);

  /**
   * Sets the boolean value
   */
  default void value(boolean value) {
    this.value((byte) (value ? 1 : 0));
  }

  /**
   * Gets the value.
   */
  byte value();

  /**
   * Gets the value as boolean
   */
  default boolean booleanValue() {
    return this.value() != 0;
  }

  @Override
  default @NotNull ByteBinaryTag asBinaryTag() {
    return ByteBinaryTag.byteBinaryTag(this.value());
  }

}

class NBTTagByteImpl extends NBTTagNumberImpl implements NBTTagByte {

  private byte value;

  protected NBTTagByteImpl(byte value) {
    this.value = value;
  }

  /**
   * Sets the value.
   */
  @Override
  public void value(byte value) {
    this.value = value;
  }

  /**
   * Gets the value.
   */
  @Override
  public byte value() {
    return this.value;
  }

}
