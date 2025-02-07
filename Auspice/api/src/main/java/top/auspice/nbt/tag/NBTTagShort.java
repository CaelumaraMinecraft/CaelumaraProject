package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.token.NBTToken;

public class NBTTagShort extends NBTTagNumber<Short> {
    private short value;

    @NotNull
    public static NBTTagShort of(short value) {
        return new NBTTagShort(value);
    }

    private NBTTagShort(short value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagShort> type() {
        return NBTTagType.SHORT;
    }

    @NotNull
    public Short value() {
        return this.value;
    }

    public void setValue(Short value) {
        this.value = value;
    }

    public short valueAsShort() {
        return this.value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.Short(this.value));
    }
}
