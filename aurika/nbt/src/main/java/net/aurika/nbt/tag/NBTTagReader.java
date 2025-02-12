package net.aurika.nbt.tag;

import org.jetbrains.annotations.NotNull;
import net.aurika.nbt.NBTTagId;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.exception.NBTParseException;
import net.aurika.nbt.stream.internal.SurroundingNBTStream;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.Arrays;
import java.util.Objects;

public final class NBTTagReader {

    private NBTTagReader() {
    }

    @NotNull
    public static NBTRootEntry readRoot(@NotNull NBTStream tokens) throws IOException {
        Objects.requireNonNull(tokens);
        tokens = tokens.calculateOptionalInfo();
        NBTToken name = tokens.nextOrNull();
        if (!(name instanceof NBTToken.Name)) {
            throw new NBTParseException("Expected root name");
        } else if (((NBTToken.Name) name).getId().orElseThrow(NullPointerException::new) != NBTTagId.COMPOUND) {
            throw new NBTParseException("Expected compound tag for root tag");
        } else {
            NBTTagCompound tag = readCompound(tokens);
            return new NBTRootEntry(((NBTToken.Name) name).getName(), tag);
        }
    }

    @NotNull
    public static NBTTagCompound readCompound(@NotNull NBTStream tokens) throws IOException {
        Objects.requireNonNull(tokens);
        NBTStream tokensStream = tokens.calculateOptionalInfo();
        if (!(tokensStream.nextOrNull() instanceof NBTToken.CompoundStart)) {
            throw new NBTParseException("Expected compound start");
        } else {
            NBTTagCompound builder = NBTTagCompound.empty();
            Objects.requireNonNull(builder);

            while (true) {
                NBTToken token = tokensStream.nextOrNull();
                if (token == null) {
                    throw new NBTParseException("Expected compound end");
                }

                if (token instanceof NBTToken.CompoundEnd) {
                    return builder;
                }

                if (!(token instanceof NBTToken.Name)) {
                    throw new NBTParseException("Expected name, got " + token);
                }

                Objects.requireNonNull(tokensStream);

                NBTTag<?> value = readValue(tokensStream, ((NBTToken.Name) token).getId().map((it) -> NBTTagType.fromId(Objects.requireNonNull(it))).orElse(null));
                Objects.requireNonNull(value);
                builder.set(((NBTToken.Name) token).getName(), value);
            }
        }
    }

    private static NBTTagByteArray readByteArray(NBTStream tokens) throws IOException {
        NBTToken start = tokens.nextOrNull();
        if (!(start instanceof NBTToken.ByteArrayStart)) {
            throw new NBTParseException("Expected byte array start");
        } else {
            ByteBuffer buffer = ByteBuffer.allocate(((NBTToken.ByteArrayStart) start).getSize().orElseThrow(NullPointerException::new));
            Objects.requireNonNull(buffer);

            while (true) {
                NBTToken token = tokens.nextOrNull();
                if (token == null) {
                    throw new NBTParseException("Expected byte array end");
                }

                if (token instanceof NBTToken.ByteArrayEnd) {
                    if (buffer.hasRemaining()) {
                        throw new NBTParseException("Not all bytes received");
                    }

                    byte[] var4 = buffer.array();
                    return NBTTagByteArray.of(Arrays.copyOf(var4, var4.length));
                }

                if (!(token instanceof NBTToken.ByteArrayContent)) {
                    throw new NBTParseException("Expected byte array content, got " + token);
                }

                buffer.put(((NBTToken.ByteArrayContent) token).getBuffer());
            }
        }
    }

    private static NBTTagIntArray readIntArray(NBTStream tokens) throws IOException {
        NBTToken start = tokens.nextOrNull();
        if (!(start instanceof NBTToken.IntArrayStart)) {
            throw new NBTParseException("Expected int array start");
        } else {
            IntBuffer buffer = IntBuffer.allocate(((NBTToken.IntArrayStart) start).getSize().orElseThrow(NullPointerException::new));

            while (true) {
                NBTToken token = tokens.nextOrNull();
                if (token == null) {
                    throw new NBTParseException("Expected int array end");
                }

                if (token instanceof NBTToken.IntArrayEnd) {
                    if (buffer.hasRemaining()) {
                        throw new NBTParseException("Not all ints received");
                    }

                    int[] var4 = buffer.array();
                    return NBTTagIntArray.of(Arrays.copyOf(var4, var4.length));
                }

                if (!(token instanceof NBTToken.IntArrayContent)) {
                    throw new NBTParseException("Expected int array content, got " + token);
                }

                buffer.put(((NBTToken.IntArrayContent) token).getBuffer());
            }
        }
    }

    private static NBTTagLongArray readLongArray(NBTStream tokens) throws IOException {
        NBTToken start = tokens.nextOrNull();
        if (!(start instanceof NBTToken.LongArrayStart)) {
            throw new NBTParseException("Expected long array start");
        } else {
            LongBuffer buffer = LongBuffer.allocate(((NBTToken.LongArrayStart) start).getSize().orElseThrow(NullPointerException::new));

            while (true) {
                NBTToken token = tokens.nextOrNull();
                if (token == null) {
                    throw new NBTParseException("Expected long array end");
                }

                if (token instanceof NBTToken.LongArrayEnd) {
                    if (buffer.hasRemaining()) {
                        throw new NBTParseException("Not all longs received");
                    }

                    long[] var4 = buffer.array();
                    return NBTTagLongArray.of(Arrays.copyOf(var4, var4.length));
                }

                if (!(token instanceof NBTToken.LongArrayContent)) {
                    throw new NBTParseException("Expected long array content, got " + token);
                }

                buffer.put(((NBTToken.LongArrayContent) token).getBuffer());
            }
        }
    }

    private static <T extends NBTTag<?>> NBTTagList<T> readList(NBTStream tokens) throws IOException {
        NBTToken start = tokens.nextOrNull();
        if (!(start instanceof NBTToken.ListStart)) {
            throw new NBTParseException("Expected list start");
        } else {
            NBTTagId var10001 = ((NBTToken.ListStart) start).getElementId().orElseThrow(NullPointerException::new);
            Objects.requireNonNull(var10001);
            NBTTagType<NBTTag<?>> elementType = NBTTagType.fromId(var10001);
            NBTTagList<NBTTag<?>> builder = NBTTagList.empty(elementType);
            int i = 0;

            for (int var5 = ((NBTToken.ListStart) start).getSize().orElseThrow(NullPointerException::new); i < var5; ++i) {
                NBTTag<?> tag = readValue(tokens, elementType);
                builder.add(tag);
            }

            if (!(tokens.nextOrNull() instanceof NBTToken.ListEnd)) {
                throw new NBTParseException("Expected list end");
            } else {
                return (NBTTagList<T>) builder;
            }
        }
    }

    private static <T extends NBTTag<?>> T readValue(NBTStream tokens, NBTTagType<T> id) throws IOException {
        NBTStream tokensStream = tokens;
        if (id == null) {
            NBTToken next = tokensStream.nextOrNull();
            if (next == null) {
                throw new NBTParseException("Expected value, got end of stream");
            }

            NBTTagId var10001 = next.getTagId();
            Objects.requireNonNull(var10001);
            id = NBTTagType.fromId(var10001);
            tokensStream = new SurroundingNBTStream(next, tokensStream, null);
        }

        NBTTag<?> tag;
        switch (id.id()) {
            case BYTE_ARRAY:
                tag = readByteArray(tokensStream);
                break;
            case BYTE:
                NBTToken var30 = requireNextToken(tokensStream);
                Objects.requireNonNull(var30, "null cannot be cast to non-null type " + NBTToken.Byte.class.getName());
                tag = NBTTagByte.of(((NBTToken.Byte) var30).getValue());
                break;
            case COMPOUND:
                tag = readCompound(tokensStream);
                break;
            case DOUBLE:
                NBTToken var26 = requireNextToken(tokensStream);
                Objects.requireNonNull(var26, "null cannot be cast to non-null type " + NBTToken.Double.class.getName());
                tag = NBTTagDouble.of(((NBTToken.Double) var26).getValue());
                break;
            case END:
                throw new NBTParseException("Unexpected END id");
            case FLOAT:
                NBTToken var23 = requireNextToken(tokensStream);
                Objects.requireNonNull(var23, "null cannot be cast to non-null type " + NBTToken.Float.class.getName());
                tag = NBTTagFloat.of(((NBTToken.Float) var23).getValue());
                break;
            case INT_ARRAY:
                tag = readIntArray(tokensStream);
                break;
            case INT:
                NBTToken var19 = requireNextToken(tokensStream);
                Objects.requireNonNull(var19, "null cannot be cast to non-null type " + NBTToken.Int.class.getName());
                tag = NBTTagInt.of(((NBTToken.Int) var19).getValue());
                break;
            case LIST:
                tag = readList(tokensStream);
                break;
            case LONG_ARRAY:
                tag = readLongArray(tokensStream);
                break;
            case LONG:
                NBTToken var14 = requireNextToken(tokensStream);
                Objects.requireNonNull(var14, "null cannot be cast to non-null type " + NBTToken.Long.class.getName());
                tag = NBTTagLong.of(((NBTToken.Long) var14).getValue());
                break;
            case SHORT:
                NBTToken var11 = requireNextToken(tokensStream);
                Objects.requireNonNull(var11, "null cannot be cast to non-null type " + NBTToken.Short.class.getName());
                tag = NBTTagShort.of(((NBTToken.Short) var11).getValue());
                break;
            case STRING:
                NBTToken var7 = requireNextToken(tokensStream);
                Objects.requireNonNull(var7, "null cannot be cast to non-null type " + NBTToken.String.class.getName());
                tag = NBTTagString.of(((NBTToken.String) var7).getValue());
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }

        return id.cast(tag);
    }

    private static NBTToken requireNextToken(NBTStream tokens) throws IOException {
        NBTToken linToken = tokens.nextOrNull();
        if (linToken == null) {
            throw new NBTParseException("Unexpected end of stream");
        } else {
            return linToken;
        }
    }
}
