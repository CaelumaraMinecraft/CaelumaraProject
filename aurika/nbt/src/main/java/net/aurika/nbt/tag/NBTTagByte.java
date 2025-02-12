package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.token.NBTToken;

public class NBTTagByte extends NBTTagNumber<Byte> {
    private byte value;

    @NotNull
    public static NBTTagByte fromInt(int value) {
        return of((byte) value);
    }

    @NotNull
    public static NBTTagByte of(byte value) {
        return new NBTTagByte(value);
    }

    protected NBTTagByte(byte value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagByte> type() {
        return NBTTagType.BYTE;
    }

    @NotNull
    public Byte value() {
        return this.value;
    }

    public void setValue(Byte value) {
        this.value = value;
    }

    public byte valueAsByte() {
        return this.value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.Byte(this.value));
    }
}
