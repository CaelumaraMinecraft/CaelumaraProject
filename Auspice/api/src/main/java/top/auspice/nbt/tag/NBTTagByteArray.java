package top.auspice.nbt.tag;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.internal.SurroundingNBTStream;
import top.auspice.nbt.stream.token.NBTToken;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;
import java.util.OptionalInt;

public class NBTTagByteArray extends NBTTag<byte[]> {
    private byte[] value;

    @NotNull
    public static NBTTagByteArray of(byte... value) {
        return new NBTTagByteArray(value.clone());
    }

    private NBTTagByteArray(byte[] value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @NotNull
    public NBTTagType<NBTTagByteArray> type() {
        return NBTTagType.BYTE_ARRAY;
    }

    public byte[] value() {
        return this.value.clone();
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    @NotNull
    public ByteBuffer view() {
        return ByteBuffer.wrap(this.value).asReadOnlyBuffer();
    }

    @NotNull
    public NBTStream stream() {
        return new SurroundingNBTStream(new NBTToken.ByteArrayStart(OptionalInt.of(this.value.length)), new NBTStream() {
            private static final int BUFFER_SIZE = 4096;
            private int i = 0;

            @Nullable
            public NBTToken nextOrNull() {
                if (this.i >= NBTTagByteArray.this.value.length) {
                    return null;
                } else {
                    int length = Math.min(4096, NBTTagByteArray.this.value.length - this.i);
                    ByteBuffer buffer = ByteBuffer.wrap(NBTTagByteArray.this.value, this.i, length).asReadOnlyBuffer();
                    this.i += length;
                    return new NBTToken.ByteArrayContent(buffer);
                }
            }
        }, new NBTToken.ByteArrayEnd());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            NBTTagByteArray that = (NBTTagByteArray) o;
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
