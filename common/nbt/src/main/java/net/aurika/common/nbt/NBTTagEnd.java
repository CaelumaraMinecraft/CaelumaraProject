package net.aurika.common.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.EndBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagEnd extends NBTTag {

  NBTTagEnd INSTANCE = new NBTTagEnd() {
    @Override
    public @NotNull BinaryTag asBinaryTag() {
      return EndBinaryTag.endBinaryTag();
    }

    @Override
    public int hashCode() {
      return System.identityHashCode(this);
    }

    @Override
    public @NotNull String toString() {
      return this.getClass().getSimpleName();
    }
  };

  static NBTTagEnd nbtTagEnd() {
    return INSTANCE;
  }

  @Override
  default @NotNull NBTTagType<NBTTagEnd> nbtTagType() {
    return NBTTagType.END;
  }

  @Override
  default @NotNull Void valueAsObject() {
    return null;
  }

}
