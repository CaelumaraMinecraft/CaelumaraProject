package net.aurika.nbt.stream.token;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.NBTTagId;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface NBTToken {
    default boolean isTerminal() {
        NBTTagId var10000 = this.getTagId();
        return var10000 != null && var10000.isTerminal();
    }

    @Nullable
    default NBTTagId getTagId() {
        return null;
    }

    class Name implements NBTToken {
        @NotNull
        private final java.lang.String name;
        @NotNull
        private final Optional<NBTTagId> id;

        public Name(@NotNull java.lang.String name, @NotNull Optional<NBTTagId> id) {
            Objects.requireNonNull(name);
            Objects.requireNonNull(id);
            this.name = name;
            this.id = id;
        }

        @NotNull
        public final java.lang.String getName() {
            return this.name;
        }

        @NotNull
        public final Optional<NBTTagId> getId() {
            return this.id;
        }
    }

    class ByteArrayStart implements NBTToken {
        @NotNull
        private final OptionalInt size;
        @NotNull
        private final NBTTagId tagId;

        public ByteArrayStart(@NotNull OptionalInt size) {
            Objects.requireNonNull(size);

            this.size = size;
            this.tagId = NBTTagId.BYTE_ARRAY;
        }

        @NotNull
        public final OptionalInt getSize() {
            return this.size;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class ByteArrayContent implements NBTToken {
        @NotNull
        private final ByteBuffer buffer;

        public ByteArrayContent(@NotNull ByteBuffer buffer) {
            Objects.requireNonNull(buffer);
            this.buffer = buffer;
            if (!this.buffer.isReadOnly()) {
                java.lang.String var3 = "buffer must be read-only";
                throw new IllegalArgumentException(var3);
            }
        }

        @NotNull
        public final ByteBuffer getBuffer() {
            return this.buffer;
        }
    }

    class ByteArrayEnd implements NBTToken {
        public ByteArrayEnd() {
        }
    }

    class CompoundStart implements NBTToken {
        @NotNull
        private final NBTTagId tagId;

        public CompoundStart() {
            this.tagId = NBTTagId.COMPOUND;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class CompoundEnd implements NBTToken {
        public CompoundEnd() {
        }
    }

    class String implements NBTToken {
        @NotNull
        private final java.lang.String value;
        @NotNull
        private final NBTTagId tagId;

        public String(@NotNull java.lang.String value) {
            Objects.requireNonNull(value);

            this.value = value;
            this.tagId = NBTTagId.STRING;
        }

        @NotNull
        public final java.lang.String getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class Double implements NBTToken {
        private final double value;
        @NotNull
        private final NBTTagId tagId;

        public Double(double value) {
            this.value = value;
            this.tagId = NBTTagId.DOUBLE;
        }

        public final double getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class Float implements NBTToken {
        private final float value;
        @NotNull
        private final NBTTagId tagId;

        public Float(float value) {
            this.value = value;
            this.tagId = NBTTagId.FLOAT;
        }

        public final float getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class Long implements NBTToken {
        private final long value;
        @NotNull
        private final NBTTagId tagId;

        public Long(long value) {
            this.value = value;
            this.tagId = NBTTagId.LONG;
        }

        public final long getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class Int implements NBTToken {
        private final int value;
        @NotNull
        private final NBTTagId tagId;

        public Int(int value) {
            this.value = value;
            this.tagId = NBTTagId.INT;
        }

        public final int getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class Short implements NBTToken {
        private final short value;
        @NotNull
        private final NBTTagId tagId;

        public Short(short value) {
            this.value = value;
            this.tagId = NBTTagId.SHORT;
        }

        public final short getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class Byte implements NBTToken {
        private final byte value;
        @NotNull
        private final NBTTagId tagId;

        public Byte(byte value) {
            this.value = value;
            this.tagId = NBTTagId.BYTE;
        }

        public final byte getValue() {
            return this.value;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class IntArrayStart implements NBTToken {
        @NotNull
        private final OptionalInt size;
        @NotNull
        private final NBTTagId tagId;

        public IntArrayStart(@NotNull OptionalInt size) {
            Objects.requireNonNull(size);

            this.size = size;
            this.tagId = NBTTagId.INT_ARRAY;
        }

        @NotNull
        public final OptionalInt getSize() {
            return this.size;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class IntArrayContent implements NBTToken {
        @NotNull
        private final IntBuffer buffer;

        public IntArrayContent(@NotNull IntBuffer buffer) {
            Objects.requireNonNull(buffer);

            this.buffer = buffer;
            if (!this.buffer.isReadOnly()) {
                java.lang.String var3 = "buffer must be read-only";
                throw new IllegalArgumentException(var3);
            }
        }

        @NotNull
        public final IntBuffer getBuffer() {
            return this.buffer;
        }
    }

    class IntArrayEnd implements NBTToken {
        public IntArrayEnd() {
        }
    }

    class ListStart implements NBTToken {
        @NotNull
        private final OptionalInt size;
        @NotNull
        private final Optional<NBTTagId> elementId;
        @NotNull
        private final NBTTagId tagId;

        public ListStart(@NotNull OptionalInt size, @NotNull Optional<NBTTagId> elementId) {
            Objects.requireNonNull(size);
            Objects.requireNonNull(elementId);

            this.size = size;
            this.elementId = elementId;
            this.tagId = NBTTagId.LIST;
        }

        @NotNull
        public final OptionalInt getSize() {
            return this.size;
        }

        @NotNull
        public final Optional<NBTTagId> getElementId() {
            return this.elementId;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class ListEnd implements NBTToken {
        public ListEnd() {
        }
    }

    class LongArrayStart implements NBTToken {
        @NotNull
        private final OptionalInt size;
        @NotNull
        private final NBTTagId tagId;

        public LongArrayStart(@NotNull OptionalInt size) {
            Objects.requireNonNull(size);

            this.size = size;
            this.tagId = NBTTagId.LONG_ARRAY;
        }

        @NotNull
        public final OptionalInt getSize() {
            return this.size;
        }

        @NotNull
        public NBTTagId getTagId() {
            return this.tagId;
        }
    }

    class LongArrayContent implements NBTToken {
        @NotNull
        private final LongBuffer buffer;

        public LongArrayContent(@NotNull LongBuffer buffer) {
            Objects.requireNonNull(buffer);

            this.buffer = buffer;
            if (!this.buffer.isReadOnly()) {
                java.lang.String var3 = "buffer must be read-only";
                throw new IllegalArgumentException(var3);
            }
        }

        @NotNull
        public final LongBuffer getBuffer() {
            return this.buffer;
        }
    }

    class LongArrayEnd implements NBTToken {
        public LongArrayEnd() {
        }
    }
}
