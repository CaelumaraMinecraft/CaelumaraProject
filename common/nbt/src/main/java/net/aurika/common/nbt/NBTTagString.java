package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface NBTTagString extends NBTTag {

  @Contract("_ -> new")
  static @NotNull NBTTagString nbtTagString(@NotNull StringBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagString(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagString nbtTagString(@NotNull String value) { return new NBTTagStringImpl(value); }

  @Override
  default @NotNull NBTTagType<NBTTagString> nbtTagType() { return NBTTagType.STRING; }

  @NotNull String value();

  void value(@NotNull String value);

  @Override
  @NotNull String valueAsObject();

  @Override
  @NotNull BinaryTag asBinaryTag();

}

class NBTTagStringImpl extends NBTTagImpl implements NBTTagString {

  private String value;

  NBTTagStringImpl(@NotNull String value) {
    this.value = Objects.requireNonNull(value, "value is null");
  }

  @Override
  public @NotNull NBTTagType<NBTTagString> nbtTagType() {
    return NBTTagType.STRING;
  }

  @Override
  public @NotNull String value() {
    return value;
  }

  @Override
  public void value(@NotNull String value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public @NotNull String valueAsObject() { return value; }

  @Override
  public @NotNull BinaryTag asBinaryTag() {
    return StringBinaryTag.stringBinaryTag(this.value());
  }

}
