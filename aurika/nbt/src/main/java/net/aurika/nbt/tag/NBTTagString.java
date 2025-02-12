package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.token.NBTToken;

import java.util.Objects;

public class NBTTagString extends NBTTag<String> {
    private String value;

    public static @NotNull NBTTagString of(@NotNull String value) {
        return new NBTTagString(value);
    }

    private NBTTagString(@NotNull String value) {
        this.value = Objects.requireNonNull(value, "value is null");
    }

    public @NotNull NBTTagType<NBTTagString> type() {
        return NBTTagType.STRING;
    }

    public @NotNull String value() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public @NotNull NBTStream stream() {
        return NBTStream.of(new NBTToken.String(this.value));
    }
}
