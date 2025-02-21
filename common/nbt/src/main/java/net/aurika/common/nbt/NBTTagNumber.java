package net.aurika.common.nbt;

import net.kyori.adventure.nbt.NumberBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagNumber extends NBTTag {
    @Override
    @NotNull Number valueAsObject();

    @Override
    @NotNull NumberBinaryTag asBinaryTag();
}

abstract class NBTTagNumberImpl implements NBTTagNumber {
}
