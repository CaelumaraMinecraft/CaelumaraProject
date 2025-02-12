package net.aurika.nbt.stream;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.IOFunction;
import net.aurika.nbt.NBTTagId;
import net.aurika.nbt.stream.exception.NBTWriteException;
import net.aurika.nbt.stream.impl.NBTReader;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Objects;

public final class NBTBinaryIO {

    private NBTBinaryIO() {
    }

    public static @NotNull NBTStream read(@NotNull DataInput input) {
        Objects.requireNonNull(input);
        return new NBTReader(input);
    }

    public static <R> R readUsing(@NotNull DataInput input, @NotNull IOFunction<? super NBTStream, ? extends R> transform) throws IOException {
        Objects.requireNonNull(input);
        Objects.requireNonNull(transform);
        return transform.apply(read(input));
    }

    public static void write(@NotNull DataOutput output, @NotNull NBTStreamable tokens) throws IOException {
        Objects.requireNonNull(output, "output");
        Objects.requireNonNull(tokens, "tokens");
        NBTStream tokenStream = tokens.stream().calculateOptionalInfo();
        boolean seenFirstName = false;
        String nextName = null;
        NBTToken token;

        while (true) {
            NBTToken var10000 = tokenStream.nextOrNull();
            if (var10000 == null) {
                return;
            }

            token = var10000;
            if (!seenFirstName) {
                if (!(token instanceof NBTToken.Name)) {
                    throw new NBTWriteException("Expected first token to be a name");
                }

                seenFirstName = true;
            }

            if (token instanceof NBTToken.Name) {
                nextName = ((NBTToken.Name) token).getName();
            } else if (token instanceof NBTToken.ByteArrayStart) {
                writeIdAndNameIfNeeded(output, NBTTagId.BYTE_ARRAY, nextName);
                nextName = null;
                output.writeInt(((NBTToken.ByteArrayStart) token).getSize().orElseThrow(NullPointerException::new));
            } else if (token instanceof NBTToken.ByteArrayContent) {
                byte[] copy = new byte[((NBTToken.ByteArrayContent) token).getBuffer().remaining()];
                ((NBTToken.ByteArrayContent) token).getBuffer().get(copy);
                output.write(copy);
            } else if (!(token instanceof NBTToken.ByteArrayEnd)) {
                if (token instanceof NBTToken.Byte) {
                    writeIdAndNameIfNeeded(output, NBTTagId.BYTE, nextName);
                    nextName = null;
                    output.writeByte(((NBTToken.Byte) token).getValue());
                } else if (token instanceof NBTToken.CompoundStart) {
                    writeIdAndNameIfNeeded(output, NBTTagId.COMPOUND, nextName);
                    nextName = null;
                } else if (token instanceof NBTToken.CompoundEnd) {
                    output.writeByte(NBTTagId.END.id());
                } else if (token instanceof NBTToken.Double) {
                    writeIdAndNameIfNeeded(output, NBTTagId.DOUBLE, nextName);
                    nextName = null;
                    output.writeDouble(((NBTToken.Double) token).getValue());
                } else if (token instanceof NBTToken.Float) {
                    writeIdAndNameIfNeeded(output, NBTTagId.FLOAT, nextName);
                    nextName = null;
                    output.writeFloat(((NBTToken.Float) token).getValue());
                } else if (token instanceof NBTToken.IntArrayStart) {
                    writeIdAndNameIfNeeded(output, NBTTagId.INT_ARRAY, nextName);
                    nextName = null;
                    output.writeInt(((NBTToken.IntArrayStart) token).getSize().orElseThrow(NullPointerException::new));
                } else if (token instanceof NBTToken.IntArrayContent) {
                    IntBuffer buffer = ((NBTToken.IntArrayContent) token).getBuffer();

                    while (buffer.hasRemaining()) {
                        output.writeInt(buffer.get());
                    }
                } else if (!(token instanceof NBTToken.IntArrayEnd)) {
                    if (token instanceof NBTToken.Int) {
                        writeIdAndNameIfNeeded(output, NBTTagId.INT, nextName);
                        nextName = null;
                        output.writeInt(((NBTToken.Int) token).getValue());
                    } else if (token instanceof NBTToken.ListStart) {
                        writeIdAndNameIfNeeded(output, NBTTagId.LIST, nextName);
                        nextName = null;
                        output.writeByte(((NBTToken.ListStart) token).getElementId().orElseThrow(NullPointerException::new).id());
                        output.writeInt(((NBTToken.ListStart) token).getSize().orElseThrow(NullPointerException::new));
                    } else if (!(token instanceof NBTToken.ListEnd)) {
                        if (token instanceof NBTToken.LongArrayStart) {
                            writeIdAndNameIfNeeded(output, NBTTagId.LONG_ARRAY, nextName);
                            nextName = null;
                            output.writeInt(((NBTToken.LongArrayStart) token).getSize().orElseThrow(NullPointerException::new));
                        } else if (token instanceof NBTToken.LongArrayContent) {
                            LongBuffer buffer = ((NBTToken.LongArrayContent) token).getBuffer();

                            while (buffer.hasRemaining()) {
                                output.writeLong(buffer.get());
                            }
                        } else if (!(token instanceof NBTToken.LongArrayEnd)) {
                            if (token instanceof NBTToken.Long) {
                                writeIdAndNameIfNeeded(output, NBTTagId.LONG, nextName);
                                nextName = null;
                                output.writeLong(((NBTToken.Long) token).getValue());
                            } else if (token instanceof NBTToken.Short) {
                                writeIdAndNameIfNeeded(output, NBTTagId.SHORT, nextName);
                                nextName = null;
                                output.writeShort(((NBTToken.Short) token).getValue());
                            } else {
                                if (!(token instanceof NBTToken.String)) {
                                    throw new NBTWriteException("Unknown token: " + token);
                                }

                                writeIdAndNameIfNeeded(output, NBTTagId.STRING, nextName);
                                nextName = null;
                                output.writeUTF(((NBTToken.String) token).getValue());
                            }
                        }
                    }
                }
            }
        }
    }

    private static void writeIdAndNameIfNeeded(DataOutput output, NBTTagId id, String name) throws IOException {
        if (name != null) {
            output.writeByte(id.id());
            output.writeUTF(name);
        }
    }
}
