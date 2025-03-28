package net.aurika.common.nbt;

import net.aurika.validate.Validate;
import net.kyori.adventure.nbt.ByteArrayBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public interface NBTTagByteArray extends NBTTagArray<byte[]>, NBTTagObject<byte[]> {

  static @NotNull NBTTagByteArray nbtTagByteArray(byte @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagByteArrayImpl(value.clone());
  }

  static @NotNull NBTTagByteArray nbtTagByteArrayRaw(byte @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagByteArrayImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagByteArray> nbtTagType() {
    return NBTTagType.BYTE_ARRAY;
  }

  @Override
  default byte @NotNull [] value() {
    return this.rawValue().clone();
  }

  @Override
  byte @NotNull [] rawValue();

  @Override
  default void value(byte @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.rawValue(value.clone());
  }

  @Override
  void rawValue(byte @NotNull [] value);

  default @NotNull ByteBuffer view() {
    return ByteBuffer.wrap(this.rawValue()).asReadOnlyBuffer();
  }

  @Override
  default @NotNull ByteArrayBinaryTag asBinaryTag() {
    return ByteArrayBinaryTag.byteArrayBinaryTag(this.rawValue());
  }

}

class NBTTagByteArrayImpl extends NBTTagArrayImpl<byte[]> implements NBTTagByteArray {

  private byte @NotNull [] value;

  NBTTagByteArrayImpl(byte @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public byte @NotNull [] rawValue() {
    return this.value;
  }

  @Override
  public void rawValue(byte @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

}
