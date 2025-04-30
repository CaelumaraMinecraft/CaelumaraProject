package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.ByteArrayBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public interface NBTTagByteArray extends NBTTagArray<byte[]>, NBTTagMutableObject<byte[]> {

  @Contract("_ -> new")
  static @NotNull NBTTagByteArray nbtTagByteArray(@NotNull ByteArrayBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagByteArraySynced(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagByteArray nbtTagByteArrayCloned(byte @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagByteArrayImpl(value.clone());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagByteArray nbtTagByteArraySynced(byte @NotNull ... value) {
    Validate.Arg.notNull(value, "value");
    return new NBTTagByteArrayImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagByteArray> nbtTagType() {
    return NBTTagType.BYTE_ARRAY;
  }

  @Override
  byte @NotNull [] valueRaw();

  @Override
  byte @NotNull [] valueCopy();

  @Override
  void valueRaw(byte @NotNull [] value);

  @Override
  void valueCopy(byte @NotNull [] value);

  @Deprecated
  default @NotNull ByteBuffer view() {
    return ByteBuffer.wrap(this.valueRaw()).asReadOnlyBuffer();
  }

  @Override
  @NotNull ByteArrayBinaryTag asBinaryTag();

}

class NBTTagByteArrayImpl extends NBTTagArrayImpl<byte[]> implements NBTTagByteArray {

  private byte @NotNull [] value;

  NBTTagByteArrayImpl(byte @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public byte @NotNull [] valueRaw() { return this.value; }

  @Override
  public byte @NotNull [] valueCopy() { return this.value.clone(); }

  @Override
  public void valueRaw(byte @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public void valueCopy(byte @NotNull [] value) {
    Validate.Arg.notNull(value, "value");
    this.value = value.clone();
  }

  @Override
  public @NotNull ByteArrayBinaryTag asBinaryTag() {
    return ByteArrayBinaryTag.byteArrayBinaryTag(this.value);
  }

}
