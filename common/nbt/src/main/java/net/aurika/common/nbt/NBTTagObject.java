package net.aurika.common.nbt;

import org.jetbrains.annotations.NotNull;

/**
 * Marks a {@link NBTTag} value is a mutable class.
 */
public interface NBTTagObject<T extends Object> extends NBTTag { // TODO rename
    /**
     * Gets the raw value of the implement of {@link NBTTagObject}.
     * @return the raw value
     */
    @NotNull T rawValue();

    /**
     * Gets the raw value of the implement of {@link NBTTagObject}. It
     * isn't the raw data, and changes on the returned value will not
     * apply to the implement of {@link NBTTagObject}.
     * @return the value
     */
    @NotNull T value();

    /**
     * Sets the raw value of the implement of {@link NBTTagObject}.
     */
    void rawValue(@NotNull T value);

    /**
     * Sets the value of the implement of {@link NBTTagObject}. This
     * method will copy the new value, and changes on the value will
     * not apply to the implement of {@link NBTTagObject}.
     */
    void value(@NotNull T value);

    @Override
    default @NotNull T valueAsObject() {
        return this.value();
    }
}
