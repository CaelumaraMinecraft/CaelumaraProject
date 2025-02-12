package net.aurika.nbt.stream.impl;

import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.NBTTagId;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.exception.NBTParseException;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.OptionalInt;

public class NBTReader implements NBTStream {
    @NotNull
    private final DataInput input;
    @NotNull
    private final Deque<State> stateStack;

    public NBTReader(@NotNull DataInput input) {
        Intrinsics.checkNotNullParameter(input, "input");

        this.input = input;
        this.stateStack = (Deque) (new ArrayDeque(CollectionsKt.listOf(new State.Initial())));
    }

    @Nullable
    public NBTToken nextOrNull() throws IOException {
        State var10000 = this.stateStack.pollLast();
        if (var10000 == null) {
            return null;
        } else {
            State state = var10000;
            if (state instanceof State.ListEntry) {
                if (((State.ListEntry) state).getRemaining() == 0) {
                    return new NBTToken.ListEnd();
                }

                this.stateStack.addLast(new State.ListEntry(((State.ListEntry) state).getRemaining() - 1, ((State.ListEntry) state).getElementId()));
                state = new State.ReadValue(((State.ListEntry) state).getElementId());
            }

            if (state instanceof State.Initial) {
                if (this.input.readUnsignedByte() != NBTTagId.COMPOUND.id()) {
                    throw new NBTParseException("NBT stream does not start with a compound tag");
                } else {
                    this.stateStack.addLast(new State.CompoundStart());
                    String var20 = this.input.readUTF();
                    Intrinsics.checkNotNullExpressionValue(var20, "readUTF(...)");
                    Optional var22 = Optional.of(NBTTagId.COMPOUND);
                    Intrinsics.checkNotNullExpressionValue(var22, "of(...)");
                    return new NBTToken.Name(var20, var22);
                }
            } else if (state instanceof State.CompoundStart) {
                this.stateStack.addLast(new State.CompoundEntryName());
                return new NBTToken.CompoundStart();
            } else if (state instanceof State.CompoundEntryName) {
                NBTTagId id = NBTTagId.fromId(this.input.readUnsignedByte());
                if (id == NBTTagId.END) {
                    return new NBTToken.CompoundEnd();
                } else {
                    this.stateStack.addLast(new State.CompoundEntryName());
                    this.stateStack.addLast(new State.ReadValue(id));
                    String var19 = this.input.readUTF();
                    Intrinsics.checkNotNullExpressionValue(var19, "readUTF(...)");
                    Optional var21 = Optional.of(id);
                    Intrinsics.checkNotNullExpressionValue(var21, "of(...)");
                    return new NBTToken.Name(var19, var21);
                }
            } else if (state instanceof State.ReadValue) {
                NBTToken token;

                switch (((State.ReadValue) state).getId()) {
                    case BYTE -> token = new NBTToken.Byte(this.input.readByte());
                    case SHORT -> token = new NBTToken.Short(this.input.readShort());
                    case INT -> token = new NBTToken.Int(this.input.readInt());
                    case LONG -> token = new NBTToken.Long(this.input.readLong());
                    case FLOAT -> token = new NBTToken.Float(this.input.readFloat());
                    case DOUBLE -> token = new NBTToken.Double(this.input.readDouble());
                    case BYTE_ARRAY -> {
                        int size = this.input.readInt();
                        this.stateStack.addLast(new State.ReadByteArray(size));
                        token = new NBTToken.ByteArrayStart(OptionalInt.of(size));
                    }
                    case STRING -> {
                        String var17 = this.input.readUTF();
                        Intrinsics.checkNotNullExpressionValue(var17, "readUTF(...)");
                        token = new NBTToken.String(var17);
                    }
                    case LIST -> {
                        NBTTagId elementId = NBTTagId.fromId(this.input.readUnsignedByte());
                        int size = this.input.readInt();
                        this.stateStack.addLast(new State.ListEntry(size, elementId));
                        token = new NBTToken.ListStart(OptionalInt.of(size), Optional.of(elementId));
                    }
                    case COMPOUND -> {
                        this.stateStack.addLast(new State.CompoundEntryName());
                        token = new NBTToken.CompoundStart();
                    }
                    case INT_ARRAY -> {
                        int size = this.input.readInt();
                        this.stateStack.addLast(new State.ReadIntArray(size));
                        OptionalInt var15 = OptionalInt.of(size);
                        Intrinsics.checkNotNullExpressionValue(var15, "of(...)");
                        token = new NBTToken.IntArrayStart(var15);
                    }
                    case LONG_ARRAY -> {
                        int size = this.input.readInt();
                        this.stateStack.addLast(new State.ReadLongArray(size));
                        OptionalInt var14 = OptionalInt.of(size);
                        Intrinsics.checkNotNullExpressionValue(var14, "of(...)");
                        token = new NBTToken.LongArrayStart(var14);
                    }
                    case END -> throw new NBTParseException("Invalid id: " + ((State.ReadValue) state).getId());
                    default -> throw new NoWhenBranchMatchedException();
                }

                return token;

            } else if (state instanceof State.ReadByteArray) {
                if (((State.ReadByteArray) state).getRemaining() == 0) {
                    return new NBTToken.ByteArrayEnd();
                } else {
                    ByteBuffer buffer = ByteBuffer.allocate((int) Math.min(8192.0F, (double) ((State.ReadByteArray) state).getRemaining()));
                    this.input.readFully(buffer.array(), buffer.position(), buffer.remaining());
                    this.stateStack.addLast(new State.ReadByteArray(((State.ReadByteArray) state).getRemaining() - buffer.remaining()));
                    ByteBuffer var13 = buffer.asReadOnlyBuffer();
                    Intrinsics.checkNotNullExpressionValue(var13, "asReadOnlyBuffer(...)");
                    return new NBTToken.ByteArrayContent(var13);
                }
            } else if (state instanceof State.ReadIntArray) {
                if (((State.ReadIntArray) state).getRemaining() == 0) {
                    return new NBTToken.IntArrayEnd();
                } else {
                    ByteBuffer buffer = ByteBuffer.allocate((int) Math.min(8192.0F, (double) (((State.ReadIntArray) state).getRemaining() * 4)));
                    this.input.readFully(buffer.array(), buffer.position(), buffer.remaining());
                    this.stateStack.addLast(new State.ReadIntArray(((State.ReadIntArray) state).getRemaining() - buffer.remaining() / 4));
                    IntBuffer var12 = buffer.asIntBuffer().asReadOnlyBuffer();
                    Intrinsics.checkNotNullExpressionValue(var12, "asReadOnlyBuffer(...)");
                    return new NBTToken.IntArrayContent(var12);
                }
            } else if (state instanceof State.ReadLongArray) {
                if (((State.ReadLongArray) state).getRemaining() == 0) {
                    return new NBTToken.LongArrayEnd();
                } else {
                    ByteBuffer buffer = ByteBuffer.allocate((int) Math.min(8192.0F, (double) (((State.ReadLongArray) state).getRemaining() * 8)));
                    this.input.readFully(buffer.array(), buffer.position(), buffer.remaining());
                    this.stateStack.addLast(new State.ReadLongArray(((State.ReadLongArray) state).getRemaining() - buffer.remaining() / 8));
                    LongBuffer var10002 = buffer.asLongBuffer().asReadOnlyBuffer();
                    Intrinsics.checkNotNullExpressionValue(var10002, "asReadOnlyBuffer(...)");
                    return new NBTToken.LongArrayContent(var10002);
                }
            } else {
                throw new AssertionError("Missing state handler (switch patterns wen)");
            }
        }
    }

    private interface State {
        final class Initial implements State {
            public Initial() {
            }

        }

        class CompoundStart implements State {
            public CompoundStart() {
            }

        }

        class CompoundEntryName implements State {
            public CompoundEntryName() {
            }

        }

        class ListEntry implements State {
            private final int remaining;

            @NotNull
            private final NBTTagId elementId;

            public ListEntry(int remaining, @NotNull NBTTagId elementId) {
                Intrinsics.checkNotNullParameter(elementId, "elementId");

                this.remaining = remaining;
                this.elementId = elementId;
            }

            public final int getRemaining() {
                return this.remaining;
            }

            @NotNull
            public final NBTTagId getElementId() {
                return this.elementId;
            }

            public final int component1() {
                return this.remaining;
            }

            @NotNull
            public final NBTTagId component2() {
                return this.elementId;
            }

            @NotNull
            public final ListEntry copy(int remaining, @NotNull NBTTagId elementId) {
                Intrinsics.checkNotNullParameter(elementId, "elementId");
                return new ListEntry(remaining, elementId);
            }

            @NotNull
            public String toString() {
                return "ListEntry(remaining=" + this.remaining + ", elementId=" + this.elementId + ')';
            }

            public int hashCode() {
                int result = Integer.hashCode(this.remaining);
                result = result * 31 + this.elementId.hashCode();
                return result;
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                } else if (!(other instanceof ListEntry var2)) {
                    return false;
                } else {
                    if (this.remaining != var2.remaining) {
                        return false;
                    } else {
                        return this.elementId == var2.elementId;
                    }
                }
            }

        }

        class ReadValue implements State {

            @NotNull
            private final NBTTagId id;

            public ReadValue(@NotNull NBTTagId id) {
                Intrinsics.checkNotNullParameter(id, "id");

                this.id = id;
            }

            @NotNull
            public final NBTTagId getId() {
                return this.id;
            }

            @NotNull
            public final NBTTagId component1() {
                return this.id;
            }

            @NotNull
            public final ReadValue copy(@NotNull NBTTagId id) {
                Intrinsics.checkNotNullParameter(id, "id");
                return new ReadValue(id);
            }

            @NotNull
            public String toString() {
                return "ReadValue(id=" + this.id + ')';
            }

            public int hashCode() {
                return this.id.hashCode();
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                } else if (!(other instanceof ReadValue var2)) {
                    return false;
                } else {
                    return this.id == var2.id;
                }
            }

        }

        class ReadByteArray implements State {

            private final int remaining;

            public ReadByteArray(int remaining) {
                this.remaining = remaining;
            }

            public final int getRemaining() {
                return this.remaining;
            }

            public final int component1() {
                return this.remaining;
            }

            @NotNull
            public final ReadByteArray copy(int remaining) {
                return new ReadByteArray(remaining);
            }

            @NotNull
            public String toString() {
                return "ReadByteArray(remaining=" + this.remaining + ')';
            }

            public int hashCode() {
                return Integer.hashCode(this.remaining);
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                } else if (!(other instanceof ReadByteArray var2)) {
                    return false;
                } else {
                    return this.remaining == var2.remaining;
                }
            }

        }

        class ReadIntArray implements State {

            private final int remaining;

            public ReadIntArray(int remaining) {
                this.remaining = remaining;
            }

            public final int getRemaining() {
                return this.remaining;
            }

            public final int component1() {
                return this.remaining;
            }

            @NotNull
            public final ReadIntArray copy(int remaining) {
                return new ReadIntArray(remaining);
            }

            @NotNull
            public String toString() {
                return "ReadIntArray(remaining=" + this.remaining + ')';
            }

            public int hashCode() {
                return Integer.hashCode(this.remaining);
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                } else if (!(other instanceof ReadIntArray var2)) {
                    return false;
                } else {
                    return this.remaining == var2.remaining;
                }
            }

        }

        class ReadLongArray implements State {

            private final int remaining;

            public ReadLongArray(int remaining) {
                this.remaining = remaining;
            }

            public final int getRemaining() {
                return this.remaining;
            }

            public final int component1() {
                return this.remaining;
            }

            @NotNull
            public final ReadLongArray copy(int remaining) {
                return new ReadLongArray(remaining);
            }

            @NotNull
            public String toString() {
                return "ReadLongArray(remaining=" + this.remaining + ')';
            }

            public int hashCode() {
                return Integer.hashCode(this.remaining);
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                } else if (!(other instanceof ReadLongArray var2)) {
                    return false;
                } else {
                    return this.remaining == var2.remaining;
                }
            }
        }

    }

}
