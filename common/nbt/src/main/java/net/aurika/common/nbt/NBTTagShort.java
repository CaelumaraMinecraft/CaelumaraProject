package net.aurika.common.nbt;

import net.kyori.adventure.nbt.ShortBinaryTag;
import org.jetbrains.annotations.NotNull;

public interface NBTTagShort extends NBTTagNumber {

    static @NotNull NBTTagShort nbtTagShort(short value) {
        return new NBTTagShortImpl(value);
    }

    @Override
    default @NotNull NBTTagType<NBTTagShort> nbtTagType() {
        return NBTTagType.SHORT;
    }

    void value(short value);

    short value();

    @Override
    default @NotNull Short valueAsObject() {
        return this.value();
    }

    @Override
    default @NotNull ShortBinaryTag asBinaryTag() {
        return ShortBinaryTag.shortBinaryTag(value());
    }
}

class NBTTagShortImpl extends NBTTagNumberImpl implements NBTTagShort {
    private short value;

    NBTTagShortImpl(short value) {
        this.value = value;
    }

    @Override
    public void value(short value) {
        this.value = value;
    }

    @Override
    public short value() {
        return this.value;
    }
}
