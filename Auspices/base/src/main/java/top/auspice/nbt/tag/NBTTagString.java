package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;

public class NBTTagString extends NBTTag<String> {
    private String value;

    @NotNull
    public static NBTTagString of(@NotNull String value) {
        return new NBTTagString(value);
    }

    private NBTTagString(@NotNull String value) {
        this.value = (String)Objects.requireNonNull(value, "value is null");
    }

    @NotNull
    public NBTTagType<NBTTagString> type() {
        return NBTTagType.STRING;
    }

    @NotNull
    public String value() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.String(this.value));
    }
}
