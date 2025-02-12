package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.token.NBTToken;

public class NBTTagFloat extends NBTTagNumber<Float> {
    private float value;

    @NotNull
    public static NBTTagFloat of(float value) {
        return new NBTTagFloat(value);
    }

    private NBTTagFloat(float value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagFloat> type() {
        return NBTTagType.FLOAT;
    }

    @NotNull
    public Float value() {
        return this.value;
    }

    public void setValue(Float value) {
    }

    public float valueAsFloat() {
        return this.value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.Float(this.value));
    }
}
