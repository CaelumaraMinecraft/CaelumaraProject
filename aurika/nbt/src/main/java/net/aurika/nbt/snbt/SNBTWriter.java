package net.aurika.nbt.snbt;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.exception.NBTWriteException;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.ArrayDeque;
import java.util.Objects;

public class SNBTWriter {
    @NotNull
    private final ArrayDeque<WriteState> stateStack = new ArrayDeque<>();

    public SNBTWriter() {
    }

    public void write(@NotNull Appendable output, @NotNull NBTStream tokens) throws IOException {
        Objects.requireNonNull(output);
        Objects.requireNonNull(tokens);

        while (true) {
            WriteState state = this.stateStack.peekLast();
            NBTToken token = tokens.nextOrNull();
            if (token == null) {
                return;
            }

            if (token instanceof NBTToken.Name) {
                if (!(state instanceof WriteState.Compound)) {
                    throw new NBTWriteException("Names can only appear inside compounds");
                }

                if (((WriteState.Compound) state).getHasPrevious()) {
                    output.append(',');
                    this.replaceLast(new WriteState.Compound(false));
                }

                output.append(Elusion.escapeIfNeeded(((NBTToken.Name) token).getName())).append(':');
            } else if (token instanceof NBTToken.ByteArrayStart) {
                output.append("[B;");
            } else if (token instanceof NBTToken.ByteArrayContent) {
                if (state instanceof WriteState.WritingArray) {
                    output.append(',');
                } else {
                    this.stateStack.addLast(new WriteState.WritingArray());
                }

                ByteBuffer buffer = ((NBTToken.ByteArrayContent) token).getBuffer();

                while (buffer.hasRemaining()) {
                    output.append(String.valueOf(buffer.get())).append('B');
                    if (buffer.hasRemaining()) {
                        output.append(',');
                    }
                }
            } else if (token instanceof NBTToken.ByteArrayEnd) {
                if (state instanceof WriteState.WritingArray) {
                    this.stateStack.removeLast();
                }

                output.append(']');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.Byte) {
                output.append(String.valueOf(((NBTToken.Byte) token).getValue())).append('B');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.CompoundStart) {
                output.append('{');
                this.stateStack.addLast(new WriteState.Compound(false));
            } else if (token instanceof NBTToken.CompoundEnd) {
                output.append('}');
                this.stateStack.removeLast();
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.Double) {
                output.append(String.valueOf(((NBTToken.Double) token).getValue())).append('D');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.Float) {
                output.append(String.valueOf(((NBTToken.Float) token).getValue())).append('F');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.IntArrayStart) {
                output.append("[I;");
            } else if (token instanceof NBTToken.IntArrayContent) {
                if (state instanceof WriteState.WritingArray) {
                    output.append(',');
                } else {
                    this.stateStack.addLast(new WriteState.WritingArray());
                }

                IntBuffer buffer = ((NBTToken.IntArrayContent) token).getBuffer();

                while (buffer.hasRemaining()) {
                    output.append(String.valueOf(buffer.get()));
                    if (buffer.hasRemaining()) {
                        output.append(',');
                    }
                }
            } else if (token instanceof NBTToken.IntArrayEnd) {
                if (state instanceof WriteState.WritingArray) {
                    this.stateStack.removeLast();
                }

                output.append(']');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.Int) {
                output.append(String.valueOf(((NBTToken.Int) token).getValue()));
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.ListStart) {
                output.append('[');
                this.stateStack.addLast(new WriteState.List(((NBTToken.ListStart) token).getSize().orElseThrow(SNBTWriter::write$lambda$0)));
            } else if (token instanceof NBTToken.ListEnd) {
                output.append(']');
                this.stateStack.removeLast();
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.LongArrayStart) {
                output.append("[L;");
            } else if (token instanceof NBTToken.LongArrayContent) {
                if (state instanceof WriteState.WritingArray) {
                    output.append(',');
                } else {
                    this.stateStack.addLast(new WriteState.WritingArray());
                }

                LongBuffer buffer = ((NBTToken.LongArrayContent) token).getBuffer();

                while (buffer.hasRemaining()) {
                    output.append(String.valueOf(buffer.get())).append('L');
                    if (buffer.hasRemaining()) {
                        output.append(',');
                    }
                }
            } else if (token instanceof NBTToken.LongArrayEnd) {
                if (state instanceof WriteState.WritingArray) {
                    this.stateStack.removeLast();
                }

                output.append(']');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.Long) {
                output.append(String.valueOf(((NBTToken.Long) token).getValue())).append('L');
                this.handleValueEnd(output);
            } else if (token instanceof NBTToken.Short) {
                output.append(String.valueOf(((NBTToken.Short) token).getValue())).append('S');
                this.handleValueEnd(output);
            } else {
                if (!(token instanceof NBTToken.String)) {
                    throw new NBTWriteException("Unknown token: " + token);
                }

                output.append(Elusion.escapeIfNeeded(((NBTToken.String) token).getValue()));
                this.handleValueEnd(output);
            }
        }
    }

    private void handleValueEnd(Appendable output) throws IOException {
        WriteState state = this.stateStack.pollLast();
        if (state != null) {
            if (state instanceof WriteState.List) {
                int remainingValues = ((WriteState.List) state).getRemainingValues() - 1;
                this.stateStack.addLast(new WriteState.List(remainingValues));
                if (remainingValues > 0) {
                    output.append(',');
                }
            } else {
                if (!(state instanceof WriteState.Compound)) {
                    throw new NBTWriteException("Unexpected state: " + state);
                }

                this.stateStack.addLast(new WriteState.Compound(true));
            }

        }
    }

    private void replaceLast(WriteState state) {
        this.stateStack.removeLast();
        this.stateStack.addLast(state);
    }

    private static IllegalStateException write$lambda$0() {
        return new IllegalStateException();
    }

    private interface WriteState {

        final class List implements WriteState {
            private final int remainingValues;

            public List(int remainingValues) {
                this.remainingValues = remainingValues;
            }

            public int getRemainingValues() {
                return this.remainingValues;
            }

            public int component1() {
                return this.remainingValues;
            }

            @NotNull
            public List copy(int remainingValues) {
                return new List(remainingValues);
            }

            @NotNull
            public String toString() {
                return "List(remainingValues=" + this.remainingValues + ')';
            }

            public int hashCode() {
                return Integer.hashCode(this.remainingValues);
            }

            public boolean equals(@Nullable Object other) {
                if (this == other) {
                    return true;
                } else if (!(other instanceof List var2)) {
                    return false;
                } else {
                    return this.remainingValues == var2.remainingValues;
                }
            }
        }

        final class Compound implements WriteState {
            private final boolean hasPrevious;

            public Compound(boolean hasPrevious) {
                this.hasPrevious = hasPrevious;
            }

            public boolean getHasPrevious() {
                return this.hasPrevious;
            }
        }

        final class WritingArray implements WriteState {
            public WritingArray() {
            }
        }
    }
}
