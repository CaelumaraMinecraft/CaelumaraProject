package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;

public class NBTTagBool extends NBTTagByte {

    public static @NotNull NBTTagBool of(boolean value) {
        return new NBTTagBool(value);
    }

    private NBTTagBool(boolean value) {
        super((byte) (value ? 1 : 0));
    }

    public boolean valueAsBool() {
        return super.value() != 0;
    }
}
