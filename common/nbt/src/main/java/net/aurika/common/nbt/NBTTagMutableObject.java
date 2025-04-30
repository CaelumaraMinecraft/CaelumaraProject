package net.aurika.common.nbt;

import org.jetbrains.annotations.NotNull;

/**
 * Marks a {@link NBTTag} value is a mutable class.
 */
public interface NBTTagMutableObject<T> extends NBTTag {

  /**
   * Gets the raw value of the implement of {@link NBTTagMutableObject}.
   *
   * @return the raw value
   */
  @NotNull T valueRaw();

  /**
   * Gets the value of the implement of {@link NBTTagMutableObject}.
   * It isn't the raw data, and changes on the returned value will not
   * apply to the implement of {@link NBTTagMutableObject}.
   *
   * @return the value
   */
  @NotNull T valueCopy();

  /**
   * Sets the raw value of the implement of {@link NBTTagMutableObject}.
   */
  void valueRaw(@NotNull T value);

  /**
   * Sets the value of the implement of {@link NBTTagMutableObject}.
   * This method will copy the new value, and changes on the value will
   * not apply to the implement of {@link NBTTagMutableObject}.
   */
  void valueCopy(@NotNull T value);

  @Override
  default @NotNull T valueAsObject() {
    return this.valueCopy();
  }

}
