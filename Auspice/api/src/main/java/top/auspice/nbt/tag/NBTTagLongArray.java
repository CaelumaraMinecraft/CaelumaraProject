package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.internal.SurroundingNBTStream;
import top.auspice.nbt.stream.token.NBTToken;

import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.OptionalInt;

public class NBTTagLongArray extends NBTTag<long[]> {
    private long[] value;

    @NotNull
    public static NBTTagLongArray of(long... value) {
        return new NBTTagLongArray((long[]) ((long[]) value).clone());
    }

    private NBTTagLongArray(long[] value) {
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagLongArray> type() {
        return NBTTagType.LONG_ARRAY;
    }

    public long[] value() {
        return (long[]) this.value.clone();
    }

    public void setValue(long[] value) {
        this.value = value;
    }

    @NotNull
    public LongBuffer view() {
        return LongBuffer.wrap(this.value).asReadOnlyBuffer();
    }

    @NotNull
    public NBTStream stream() {
        return new SurroundingNBTStream(new NBTToken.LongArrayStart(OptionalInt.of(this.value.length)), new NBTStream() {
            private static final int BUFFER_SIZE = 4096;
            private int i = 0;

            @Nullable
            public NBTToken nextOrNull() {
                if (this.i >= NBTTagLongArray.this.value.length) {
                    return null;
                } else {
                    int length = Math.min(4096, NBTTagLongArray.this.value.length - this.i);
                    LongBuffer buffer = LongBuffer.wrap(NBTTagLongArray.this.value, this.i, length).asReadOnlyBuffer();
                    this.i += length;
                    return new NBTToken.LongArrayContent(buffer);
                }
            }
        }, new NBTToken.LongArrayEnd());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            NBTTagLongArray that = (NBTTagLongArray) o;
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
