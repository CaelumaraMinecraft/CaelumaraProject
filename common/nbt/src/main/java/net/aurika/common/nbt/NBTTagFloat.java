package net.aurika.common.nbt;

import net.kyori.adventure.nbt.FloatBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagFloat extends NBTTagNumber {

  static @NotNull NBTTagFloat nbtTagFloat(float value) {
    return new NBTTagFloatImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagFloat> nbtTagType() {
    return NBTTagType.FLOAT;
  }

  void value(float value);

  float value();

  @Override
  default @NotNull Float valueAsObject() {
    return this.value();
  }

  @Override
  default @NotNull FloatBinaryTag asBinaryTag() {
    return FloatBinaryTag.floatBinaryTag(this.value());
  }

}

class NBTTagFloatImpl extends NBTTagNumberImpl implements NBTTagFloat {

  private float value;

  NBTTagFloatImpl(float value) {
    this.value = value;
  }

  public void value(float value) {
    this.value = value;
  }

  public float value() {
    return this.value;
  }

}
