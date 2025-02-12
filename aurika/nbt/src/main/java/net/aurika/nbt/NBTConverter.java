package net.aurika.nbt;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.tag.NBTTag;
import net.aurika.nbt.tag.NBTTagType;

public interface NBTConverter<T extends NBTTag<?>, C> {
    @NotNull NBTTagType<T> getType();

    @NotNull T fromNBT(C nbt);

    C toNBT(@NotNull T tag);
}
