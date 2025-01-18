package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;

public class NBTTagBool extends NBTTagByte {
    @NotNull
    public static NBTTagBool of(boolean bool) {
        return new NBTTagBool(bool);
    }

    private NBTTagBool(boolean bool) {
        super((byte) (bool ? 1 : 0));
    }

    public boolean valueAsBool() {
        return super.value() != 0;
    }
}
