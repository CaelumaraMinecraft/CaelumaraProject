package net.aurika.common.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagDouble extends NBTTagNumber {

  static @NotNull NBTTagDouble nbtTagDouble(double value) {
    return new NBTTagDoubleImpl(value);
  }

  @Override
  default @NotNull NBTTagType<NBTTagDouble> nbtTagType() {
    return NBTTagType.DOUBLE;
  }

  @Override
  default @NotNull Double valueAsObject() {
    return this.value();
  }

  double value();

  void value(double value);

  @Override
  default @NotNull BinaryTag asBinaryTag() {
    return DoubleBinaryTag.doubleBinaryTag(this.value());
  }

}

class NBTTagDoubleImpl extends NBTTagNumberImpl implements NBTTagDouble {

  private double value;

  NBTTagDoubleImpl(double value) {
    this.value = value;
  }

  public double value() {
    return this.value;
  }

  public void value(double value) {
    this.value = value;
  }

}
