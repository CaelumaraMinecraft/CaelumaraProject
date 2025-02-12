//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.aurika.nbt.snbt.reader;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.NBTTagId;
import net.aurika.nbt.snbt.reader.SNBTToken.CompoundStart;
import net.aurika.nbt.snbt.reader.SNBTToken.EntrySeparator;
import net.aurika.nbt.snbt.reader.SNBTToken.Text;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.exception.NBTParseException;
import net.aurika.nbt.stream.token.NBTToken;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

public final class SNBTReader implements NBTStream {
    @NotNull
    private final Iterator<SNBTTokenWithMetadata> input;
    @NotNull
    private final Deque<State> stateStack;
    @NotNull
    private final Deque<NBTToken> tokenQueue;
    @NotNull
    private final Deque<SNBTTokenWithMetadata> readAgainStack;
    private int charIndex;
    private static final int BUFFER_SIZE = 4096;

    public SNBTReader(@NotNull Iterator<SNBTTokenWithMetadata> input) {
        Objects.requireNonNull(input, "input");
        this.input = input;
        this.stateStack = new ArrayDeque<>(CollectionsKt.listOf(new State.ReadValue(true)));
        this.tokenQueue = new ArrayDeque<>();
        this.readAgainStack = new ArrayDeque<>();
    }

    private SNBTTokenWithMetadata read() {
        SNBTTokenWithMetadata token = this.readAgainStack.pollFirst();
        if (token != null) {
            return token;
        } else if (!this.input.hasNext()) {
            throw new NBTParseException(this.errorPrefix() + "Unexpected end of input");
        } else {
            SNBTTokenWithMetadata next = this.input.next();
            this.charIndex = next.getCharIndex();
            return next;
        }
    }

    private String errorPrefix() {
        return "At character index " + this.charIndex + ": ";
    }

    private NBTParseException unexpectedTokenError(SNBTToken token) {
        return new NBTParseException(this.errorPrefix() + "Unexpected token: " + token);
    }

    private NBTParseException unexpectedTokenSpecificError(SNBTToken token, String expected) {
        return new NBTParseException(this.errorPrefix() + "Unexpected token: " + token + ", expected " + expected);
    }

    @Nullable
    public NBTToken nextOrNull() {
        NBTToken token;
        for (token = this.tokenQueue.pollFirst(); token == null; token = this.tokenQueue.pollFirst()) {
            State state = this.stateStack.peekLast();
            if (state == null) {
                return null;
            }

            this.fillTokenStack(state);
        }

        return token;
    }

    private void fillTokenStack(State state) {
        if (state instanceof State.ReadValue) {
            this.readValue(((State.ReadValue) state).getMustBeCompound());
        } else if (state instanceof State.InCompound) {
            this.advanceCompound();
        } else if (state instanceof State.CompoundEntryName) {
            this.readName();
        } else if (state instanceof State.InList) {
            this.advanceList();
        } else if (state instanceof State.InByteArray) {
            this.advanceArray(
                    NBTToken.Byte.class,
                    ByteBuffer::allocate,
                    (ByteBuffer buffer, NBTToken.Byte t) -> {
                        Objects.requireNonNull(buffer);
                        Objects.requireNonNull(t);
                        buffer.put(t.getValue());
                    },
                    (ByteBuffer buffer) -> {
                        Objects.requireNonNull(buffer);
                        ByteBuffer readOnlyBuffer = buffer.flip().asReadOnlyBuffer();
                        Objects.requireNonNull(readOnlyBuffer);
                        return new NBTToken.ByteArrayContent(readOnlyBuffer);
                    },
                    NBTToken.ByteArrayEnd::new
            );
        } else if (state instanceof State.InIntArray) {
            this.advanceArray(
                    NBTToken.Int.class,
                    IntBuffer::allocate,
                    (IntBuffer buffer, NBTToken.Int t) -> {
                        Objects.requireNonNull(buffer);
                        Objects.requireNonNull(t);
                        buffer.put(t.getValue());
                    },
                    (IntBuffer buffer) -> {
                        Objects.requireNonNull(buffer);
                        IntBuffer readOnlyBuffer = buffer.flip().asReadOnlyBuffer();
                        Objects.requireNonNull(readOnlyBuffer);
                        return new NBTToken.IntArrayContent(readOnlyBuffer);
                    },
                    NBTToken.IntArrayEnd::new
            );
        } else {
            if (!(state instanceof State.InLongArray)) {
                throw new IllegalStateException(this.errorPrefix() + "Unknown state: " + state);
            }
            this.advanceArray(
                    NBTToken.Long.class,
                    LongBuffer::allocate,
                    (LongBuffer buffer, NBTToken.Long t) -> {
                        Objects.requireNonNull(buffer);
                        Objects.requireNonNull(t);
                        buffer.put(t.getValue());
                    },
                    (LongBuffer buffer) -> {
                        Objects.requireNonNull(buffer);
                        LongBuffer readOnlyBuffer = buffer.flip().asReadOnlyBuffer();
                        Objects.requireNonNull(readOnlyBuffer);
                        return new NBTToken.LongArrayContent(readOnlyBuffer);
                    },
                    NBTToken.LongArrayEnd::new
            );
        }

    }

    private void readValue(boolean mustBeCompound) {
        this.stateStack.removeLast();
        SNBTToken token = this.read().getToken();
        if (token instanceof CompoundStart) {
            this.stateStack.addLast(new State.InCompound());
            this.stateStack.addLast(new State.CompoundEntryName());
            this.tokenQueue.addLast(new NBTToken.CompoundStart());
        } else if (mustBeCompound) {
            throw this.unexpectedTokenSpecificError(token, CompoundStart.INSTANCE.toString());
        } else {
            if (token instanceof SNBTToken.ListLikeStart) {
                this.prepareListLike();
            } else {
                if (!(token instanceof Text)) {
                    throw this.unexpectedTokenError(token);
                }

                NBTToken NBTToken = ((Text) token).getQuoted() ? new NBTToken.String(((Text) token).getContent()) : this.getTokenFor(((Text) token).getContent());
                this.tokenQueue.addLast(NBTToken);
            }

        }
    }

    private void advanceCompound() {
        SNBTToken token = this.read().getToken();
        if (token instanceof SNBTToken.CompoundEnd) {
            this.stateStack.removeLast();
            this.tokenQueue.addLast(new NBTToken.CompoundEnd());
        } else {
            if (!(token instanceof SNBTToken.Separator)) {
                throw this.unexpectedTokenError(token);
            }

            this.stateStack.addLast(new State.CompoundEntryName());
        }

    }

    private void readName() {
        this.stateStack.removeLast();
        SNBTToken token = this.read().getToken();
        Text textToken = token instanceof Text ? (Text) token : null;
        if ((token instanceof Text ? (Text) token : null) == null) {
            throw this.unexpectedTokenSpecificError(token, "Text");
        } else {
            token = this.read().getToken();
            if (!(token instanceof EntrySeparator)) {
                throw this.unexpectedTokenSpecificError(token, EntrySeparator.INSTANCE.toString());
            } else {
                this.stateStack.addLast(new State.ReadValue(false));
                String var10003 = textToken.getContent();
                this.tokenQueue.addLast(new NBTToken.Name(var10003, Optional.empty()));
            }
        }
    }

    private void advanceList() {
        SNBTToken token = this.read().getToken();
        if (token instanceof SNBTToken.ListLikeEnd) {
            this.stateStack.removeLast();
            this.tokenQueue.addLast(new NBTToken.ListEnd());
        } else {
            if (!(token instanceof SNBTToken.Separator)) {
                throw this.unexpectedTokenError(token);
            }

            this.stateStack.addLast(new State.ReadValue(false));
        }

    }

    private <T extends Buffer, L extends NBTToken> void advanceArray(Class<L> tagType, IntFunction<T> allocator, BiConsumer<T, L> putter, Function<T, NBTToken> contentProducer, Supplier<NBTToken> endProducer) {
        boolean isEnd = false;
        T buffer = allocator.apply(BUFFER_SIZE);

        while (true) {
            Intrinsics.checkNotNull(buffer);
            if (buffer.hasRemaining()) {
                SNBTToken token = this.read().getToken();
                if (!(token instanceof Text)) {
                    throw this.unexpectedTokenSpecificError(token, "Text");
                }

                NBTToken nextValue = this.getTokenFor(((Text) token).getContent());
                if (!tagType.isInstance(nextValue)) {
                    throw new NBTParseException(this.errorPrefix() + "Expected " + tagType.getSimpleName() + " token, got " + nextValue);
                }

                putter.accept(buffer, tagType.cast(nextValue));
                token = this.read().getToken();
                if (!(token instanceof SNBTToken.ListLikeEnd)) {
                    if (!(token instanceof SNBTToken.Separator)) {
                        throw this.unexpectedTokenError(token);
                    }
                    continue;
                }

                isEnd = true;
            }

            this.tokenQueue.addLast(contentProducer.apply(buffer));
            if (isEnd) {
                this.stateStack.removeLast();
                this.tokenQueue.addLast(endProducer.get());
            }

            return;
        }
    }

    private void prepareListLike() {
        int initialCharIndex = this.charIndex;
        SNBTTokenWithMetadata typing = this.read();
        SNBTToken text = typing.getToken();
        Deque<NBTToken> var10000;
        OptionalInt var10003;
        if (text instanceof Text && !((Text) text).getQuoted() && ((Text) text).getContent().length() == 1) {
            SNBTTokenWithMetadata separatorCheck = this.read();
            if (separatorCheck.getToken() instanceof SNBTToken.ListTypeSeparator) {
                char var5 = ((Text) text).getContent().charAt(0);
                if (var5 == 'B') {
                    this.stateStack.addLast(new State.InByteArray());
                    var10000 = this.tokenQueue;
                    var10003 = OptionalInt.empty();
                    Objects.requireNonNull(var10003, "empty(...)");
                    var10000.addLast(new NBTToken.ByteArrayStart(var10003));
                } else if (var5 == 'I') {
                    this.stateStack.addLast(new State.InIntArray());
                    var10000 = this.tokenQueue;
                    var10003 = OptionalInt.empty();
                    Objects.requireNonNull(var10003, "empty(...)");
                    var10000.addLast(new NBTToken.IntArrayStart(var10003));
                } else {
                    if (var5 != 'L') {
                        throw new NBTParseException(this.errorPrefix() + "Invalid array type: " + ((Text) text).getContent());
                    }

                    this.stateStack.addLast(new State.InLongArray());
                    var10000 = this.tokenQueue;
                    var10003 = OptionalInt.empty();
                    Objects.requireNonNull(var10003, "empty(...)");
                    var10000.addLast(new NBTToken.LongArrayStart(var10003));
                }

                return;
            }

            this.readAgainStack.addFirst(separatorCheck);
        }

        this.readAgainStack.addFirst(typing);
        this.charIndex = initialCharIndex;
        this.stateStack.addLast(new State.InList());
        this.stateStack.addLast(new State.ReadValue(false));
        var10000 = this.tokenQueue;
        var10003 = OptionalInt.empty();
        Objects.requireNonNull(var10003, "empty(...)");
        Optional<NBTTagId> var10004 = Optional.empty();
        Objects.requireNonNull(var10004, "empty(...)");
        var10000.addLast(new NBTToken.ListStart(var10003, var10004));
    }

    private NBTToken getTokenFor(String valueString) {
        char last = valueString.charAt(valueString.length() - 1);
        NBTToken var10000;
        String var10002;
        NBTToken var4;
        if (last == 'B' || last == 'b') {
            try {
                var10002 = valueString.substring(0, valueString.length() - 1);
                Objects.requireNonNull(var10002, "substring(...)");
                var4 = new NBTToken.Byte(Byte.parseByte(var10002));
            } catch (NumberFormatException var12) {
                var4 = new NBTToken.String(valueString);
            }

            var10000 = var4;
        } else if (last == 'L' || last == 'l') {
            try {
                var10002 = valueString.substring(0, valueString.length() - 1);
                Objects.requireNonNull(var10002, "substring(...)");
                var4 = new NBTToken.Long(Long.parseLong(var10002));
            } catch (NumberFormatException var11) {
                var4 = new NBTToken.String(valueString);
            }

            var10000 = var4;
        } else if (last == 'S' || last == 's') {
            try {
                var10002 = valueString.substring(0, valueString.length() - 1);
                Objects.requireNonNull(var10002, "substring(...)");
                var4 = new NBTToken.Short(Short.parseShort(var10002));
            } catch (NumberFormatException var10) {
                var4 = new NBTToken.String(valueString);
            }

            var10000 = var4;
        } else if (last == 'F' || last == 'f') {
            try {
                var10002 = valueString.substring(0, valueString.length() - 1);
                Objects.requireNonNull(var10002, "substring(...)");
                var4 = new NBTToken.Float(Float.parseFloat(var10002));
            } catch (NumberFormatException var9) {
                var4 = new NBTToken.String(valueString);
            }

            var10000 = var4;
        } else if (last == 'D' || last == 'd') {
            try {
                var10002 = valueString.substring(0, valueString.length() - 1);
                Objects.requireNonNull(var10002, "substring(...)");
                var4 = new NBTToken.Double(Double.parseDouble(var10002));
            } catch (NumberFormatException var8) {
                var4 = new NBTToken.String(valueString);
            }

            var10000 = var4;
        } else {
            try {
                new NBTToken.Int(Integer.parseInt(valueString));
            } catch (NumberFormatException ignored) {
            }

            try {
                new NBTToken.Double(Double.parseDouble(valueString));
            } catch (NumberFormatException ignored) {
            }

            boolean isTrue = StringsKt.equals(valueString, "true", true);
            if (isTrue || StringsKt.equals(valueString, "false", true)) {
                new NBTToken.Byte((byte) (isTrue ? 1 : 0));
            }

            var10000 = new NBTToken.String(valueString);
        }

        return var10000;
    }

    private interface ArrayAdvancer<T extends Buffer, L extends NBTToken> {
        @Nullable
        Class<L> tagType();

        T allocate();

        void put(T var1, L var2);

        @Nullable
        NBTToken produceContent(T var1);

        @Nullable
        NBTToken produceEnd();
    }

    private interface State {
        final class CompoundEntryName implements State {
            public CompoundEntryName() {
            }
        }

        final class InByteArray implements State {
            public InByteArray() {
            }
        }

        final class InCompound implements State {
            public InCompound() {
            }
        }

        final class InIntArray implements State {
            public InIntArray() {
            }
        }

        final class InList implements State {
            public InList() {
            }
        }

        final class InLongArray implements State {
            public InLongArray() {
            }
        }

        final class ReadValue implements State {
            private final boolean mustBeCompound;

            public ReadValue(boolean mustBeCompound) {
                this.mustBeCompound = mustBeCompound;
            }

            public boolean getMustBeCompound() {
                return this.mustBeCompound;
            }
        }
    }
}
