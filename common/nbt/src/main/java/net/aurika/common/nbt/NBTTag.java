package net.aurika.common.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagLike;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * This class is a mutable implement of {@link BinaryTagLike}.
 */
public interface NBTTag extends NBTTagLike, BinaryTagLike {

  /**
   * Gets the tag type {@link NBTTagType}.
   */
  @NotNull NBTTagType<? extends NBTTag> nbtTagType();

  /**
   * Gets the tag value as a java object.
   */
  @NotNull Object valueAsObject();

  @Override
  default @NotNull NBTTag asNBTTag() {
    return this;
  }

  @Override
  @NotNull BinaryTag asBinaryTag();

}

abstract class NBTTagImpl implements NBTTag {

  protected NBTTagImpl() {
  }

  public int hashCode() {
    return this.valueAsObject().hashCode();
  }

  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof NBTTag that) {
      return Objects.equals(this.valueAsObject(), that.valueAsObject());
    } else {
      return false;
    }
  }

  public @NotNull String toString() {
    return this.getClass().getSimpleName() + "[" + this.valueAsObject() + ']';
  }

}
