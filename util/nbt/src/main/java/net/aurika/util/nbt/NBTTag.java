package net.aurika.util.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;

public abstract class NBTTag {
    public abstract BinaryTagType<? extends BinaryTag> type();
}
