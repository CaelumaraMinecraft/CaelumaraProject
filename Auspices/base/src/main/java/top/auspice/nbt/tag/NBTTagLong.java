package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.token.NBTToken;

public class NBTTagLong extends NBTTagNumber<Long> {
    private long value;

    @NotNull
    public static NBTTagLong of(long value) {
        return new NBTTagLong(value);
    }

    private NBTTagLong(long value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagLong> type() {
        return NBTTagType.LONG;
    }

    @NotNull
    public Long value() {
        return this.value;
    }

    public void setValue(Long value) {
    }

    public long valueAsLong() {
        return this.value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.Long(this.value));
    }
}
