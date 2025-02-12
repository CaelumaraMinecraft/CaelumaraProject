package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.token.NBTToken;

public class NBTTagDouble extends NBTTagNumber<Double> {
    private double value;

    @NotNull
    public static NBTTagDouble of(double value) {
        return new NBTTagDouble(value);
    }

    private NBTTagDouble(double value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagDouble> type() {
        return NBTTagType.DOUBLE;
    }

    @NotNull
    public Double value() {
        return this.value;
    }

    public void setValue(Double value) {
    }

    public double valueAsDouble() {
        return this.value;
    }

    @NotNull
    public NBTStream stream() {
        return NBTStream.of(new NBTToken.Double(this.value));
    }
}
