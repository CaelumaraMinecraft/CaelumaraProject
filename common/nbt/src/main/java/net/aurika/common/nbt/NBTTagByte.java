package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.ByteBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagByte extends NBTTagNumber {

  @Contract("_ -> new")
  static @NotNull NBTTagByte nbtTagByte(@NotNull ByteBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagByte(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagByte nbtTagByte(byte value) {
    return new NBTTagByteImpl(value);
  }

  @Contract("_ -> new")
  static @NotNull NBTTagByte nbtTagBool(boolean value) {
    return new NBTTagByteImpl((byte) (value ? 1 : 0));
  }

  @Override
  default @NotNull NBTTagType<NBTTagByte> nbtTagType() { return NBTTagType.BYTE; }

  @Override
  @NotNull Byte valueAsObject();

  /**
   * Sets the value.
   */
  void value(byte value);

  /**
   * Sets the boolean value
   */
  void booleanValue(boolean value);

  /**
   * Gets the value.
   */
  byte value();

  /**
   * Gets the value as boolean
   */
  boolean booleanValue();

  @Override
  @NotNull ByteBinaryTag asBinaryTag();

}

class NBTTagByteImpl extends NBTTagNumberImpl implements NBTTagByte {

  private byte value;

  protected NBTTagByteImpl(byte value) { this.value = value; }

  @Override
  public byte value() { return this.value; }

  @Override
  public boolean booleanValue() { return this.value() != 0; }

  @Override
  public void value(byte value) { this.value = value; }

  @Override
  public void booleanValue(boolean value) {
    this.value = (byte) (value ? 1 : 0);
  }

  @Override
  public @NotNull Byte valueAsObject() { return this.value; }

  @Override
  public @NotNull ByteBinaryTag asBinaryTag() {
    return ByteBinaryTag.byteBinaryTag(value);
  }

}
