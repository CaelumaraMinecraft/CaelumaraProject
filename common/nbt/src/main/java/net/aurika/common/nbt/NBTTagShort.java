package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.ShortBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagShort extends NBTTagNumber {

  @Contract("_ -> new")
  static @NotNull NBTTagShort nbtTagShort(@NotNull ShortBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagShort(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagShort nbtTagShort(short value) { return new NBTTagShortImpl(value); }

  @Override
  default @NotNull NBTTagType<NBTTagShort> nbtTagType() { return NBTTagType.SHORT; }

  short value();

  void value(short value);

  @Override
  @NotNull Short valueAsObject();

  @Override
  @NotNull ShortBinaryTag asBinaryTag();

}

class NBTTagShortImpl extends NBTTagNumberImpl implements NBTTagShort {

  private short value;

  NBTTagShortImpl(short value) { this.value = value; }

  @Override
  public short value() { return this.value; }

  @Override
  public void value(short value) { this.value = value; }

  @Override
  public @NotNull Short valueAsObject() { return value; }

  @Override
  public @NotNull ShortBinaryTag asBinaryTag() {
    return ShortBinaryTag.shortBinaryTag(value());
  }

}
