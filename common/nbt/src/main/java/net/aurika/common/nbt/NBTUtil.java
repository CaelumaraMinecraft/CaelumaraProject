package net.aurika.common.nbt;

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class NBTUtil {

  public static @NotNull NBTTag tagFromSNBT(@NotNull String string) {
    try {
      CompoundBinaryTag compound = TagStringIO.get().asCompound(string);
      return NBTTag.nbtTag(compound);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private NBTUtil() { }

}
