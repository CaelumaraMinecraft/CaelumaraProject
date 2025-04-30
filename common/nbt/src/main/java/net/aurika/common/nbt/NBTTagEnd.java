package net.aurika.common.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.EndBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagEnd extends NBTTag {

  static @NotNull NBTTagEnd nbtTagEnd() { return NBTTagEndImpl.INSTANCE; }

  @Override
  default @NotNull NBTTagType<NBTTagEnd> nbtTagType() { return NBTTagType.END; }

  @Override
  default @NotNull Void valueAsObject() { return null; }

}

final class NBTTagEndImpl implements NBTTagEnd {

  static final NBTTagEndImpl INSTANCE = new NBTTagEndImpl();

  @Override
  public @NotNull BinaryTag asBinaryTag() {
    return EndBinaryTag.endBinaryTag();
  }

  @Override
  public int hashCode() { return 0; }

  @Override
  public @NotNull String toString() {
    return this.getClass().getSimpleName();
  }

}
