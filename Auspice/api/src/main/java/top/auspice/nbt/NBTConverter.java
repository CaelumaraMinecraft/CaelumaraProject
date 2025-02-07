package top.auspice.nbt;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.tag.NBTTag;
import top.auspice.nbt.tag.NBTTagType;

public interface NBTConverter<T extends NBTTag<?>, C> {
    @NotNull NBTTagType<T> getType();

    @NotNull T fromNBT(C nbt);

    C toNBT(@NotNull T tag);
}
