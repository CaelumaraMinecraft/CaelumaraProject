package net.aurika.nbt.stream;

import org.jetbrains.annotations.NotNull;

public interface NBTStreamable {
    @NotNull NBTStream stream();
}
