package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.FloatBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagFloat extends NBTTagNumber {

  @Contract("_ -> new")
  static @NotNull NBTTagFloat nbtTagFloat(@NotNull FloatBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagFloat(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagFloat nbtTagFloat(float value) { return new NBTTagFloatImpl(value); }

  @Override
  default @NotNull NBTTagType<NBTTagFloat> nbtTagType() { return NBTTagType.FLOAT; }

  float value();

  void value(float value);

  @Override
  @NotNull Float valueAsObject();

  @Override
  @NotNull FloatBinaryTag asBinaryTag();

}

class NBTTagFloatImpl extends NBTTagNumberImpl implements NBTTagFloat {

  private float value;

  NBTTagFloatImpl(float value) {
    this.value = value;
  }

  public float value() { return this.value; }

  public void value(float value) { this.value = value; }

  @Override
  public @NotNull Float valueAsObject() { return this.value; }

  @Override
  public @NotNull FloatBinaryTag asBinaryTag() {
    return FloatBinaryTag.floatBinaryTag(this.value());
  }

}
