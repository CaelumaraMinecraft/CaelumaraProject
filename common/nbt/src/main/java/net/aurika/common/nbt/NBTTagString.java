package net.aurika.common.nbt;

import net.aurika.validate.Validate;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public interface NBTTagString extends NBTTag {

    static @NotNull NBTTagString nbtTagString(@NotNull String value) {
        return new NBTTagStringImpl(value);
    }

    @Override
    default @NotNull NBTTagType<NBTTagString> nbtTagType() {
        return NBTTagType.STRING;
    }

    @NotNull String value();

    void value(@NotNull String value);

    @Override
    default @NotNull String valueAsObject() {
        return this.value();
    }

    @Override
    default @NotNull BinaryTag asBinaryTag() {
        return StringBinaryTag.stringBinaryTag(this.value());
    }
}

class NBTTagStringImpl extends NBTTagImpl implements NBTTagString {
    private String value;

    NBTTagStringImpl(@NotNull String value) {
        this.value = Objects.requireNonNull(value, "value is null");
    }

    @Override
    public @NotNull NBTTagType<NBTTagString> nbtTagType() {
        return NBTTagType.STRING;
    }

    @Override
    public @NotNull String value() {
        return value;
    }

    @Override
    public void value(@NotNull String value) {
        Validate.Arg.notNull(value, "value");
        this.value = value;
    }
}
