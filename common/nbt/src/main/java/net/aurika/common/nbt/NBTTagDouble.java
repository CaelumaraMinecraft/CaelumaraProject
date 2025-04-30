package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface NBTTagDouble extends NBTTagNumber {

  @Contract("_ -> new")
  static @NotNull NBTTagDouble nbtTagDouble(@NotNull DoubleBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    return nbtTagDouble(tag.value());
  }

  @Contract("_ -> new")
  static @NotNull NBTTagDouble nbtTagDouble(double value) { return new NBTTagDoubleImpl(value); }

  @Override
  default @NotNull NBTTagType<NBTTagDouble> nbtTagType() { return NBTTagType.DOUBLE; }

  @Override
  @NotNull Double valueAsObject();

  /**
   * Gets the value.
   *
   * @return the value
   */
  double value();

  /**
   * Sets the value
   *
   * @param value the new value
   */
  void value(double value);

  @Override
  @NotNull DoubleBinaryTag asBinaryTag();

}

class NBTTagDoubleImpl extends NBTTagNumberImpl implements NBTTagDouble {

  private double value;

  NBTTagDoubleImpl(double value) { this.value = value; }

  public double value() { return this.value; }

  public void value(double value) { this.value = value; }

  @Override
  public @NotNull Double valueAsObject() { return this.value; }

  @Override
  public @NotNull DoubleBinaryTag asBinaryTag() {
    return DoubleBinaryTag.doubleBinaryTag(this.value());
  }

}
