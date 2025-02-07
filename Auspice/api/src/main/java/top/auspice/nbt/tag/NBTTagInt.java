package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.token.NBTToken;

public class NBTTagInt extends NBTTagNumber<Integer> {
    private int value;

    @NotNull
    public static NBTTagInt of(int value) {
        return new NBTTagInt(value);
    }

    private NBTTagInt(int value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagInt> type() {
        return NBTTagType.INT;
    }

    @NotNull
    public Integer value() {
        return this.value;
    }

    public void setValue(Integer value) {
    }

    public int valueAsInt() {
        return this.value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.Int(this.value));
    }
}
