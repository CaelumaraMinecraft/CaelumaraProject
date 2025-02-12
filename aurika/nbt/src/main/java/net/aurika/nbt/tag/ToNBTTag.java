package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;

public interface ToNBTTag<T extends NBTTag<?>> {
    @NotNull
    T toNBTTag();
}
