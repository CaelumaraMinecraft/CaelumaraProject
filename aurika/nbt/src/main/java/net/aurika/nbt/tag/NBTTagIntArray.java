package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.internal.SurroundingNBTStream;
import net.aurika.nbt.stream.token.NBTToken;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;

public class NBTTagIntArray extends NBTTag<int[]> {
    private int[] value;

    @NotNull
    public static NBTTagIntArray of(int... value) {
        return new NBTTagIntArray(value.clone());
    }

    private NBTTagIntArray(int[] value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public void setValue(int[] value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagIntArray> type() {
        return NBTTagType.INT_ARRAY;
    }

    public int[] value() {
        return this.value.clone();
    }

    public IntBuffer view() {
        return IntBuffer.wrap(this.value).asReadOnlyBuffer();
    }

    @NotNull
    public NBTStream stream() {
        return new SurroundingNBTStream(new NBTToken.IntArrayStart(OptionalInt.of(this.value.length)), new NBTStream() {
            private static final int BUFFER_SIZE = 4096;
            private int i = 0;

            @Nullable
            public NBTToken nextOrNull() {
                if (this.i >= NBTTagIntArray.this.value.length) {
                    return null;
                } else {
                    int length = Math.min(4096, NBTTagIntArray.this.value.length - this.i);
                    IntBuffer buffer = IntBuffer.wrap(NBTTagIntArray.this.value, this.i, length).asReadOnlyBuffer();
                    this.i += length;
                    return new NBTToken.IntArrayContent(buffer);
                }
            }
        }, new NBTToken.IntArrayEnd());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            NBTTagIntArray that = (NBTTagIntArray) o;
            return Arrays.equals(this.value, that.value);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = 1;
        result = 31 * result + Arrays.hashCode(this.value);
        return result;
    }

    @NotNull
    public String toString() {
        return this.getClass().getSimpleName() + Arrays.toString(this.value());
    }
}
