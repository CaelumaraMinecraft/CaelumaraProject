package net.aurika.nbt.stream.impl;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.NBTTagId;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.exception.NBTParseException;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.IOException;
import java.util.*;

public class OptionalInfoCalculator implements NBTStream {
    @NotNull
    private final NBTStream original;
    @Nullable
    private Deque<NBTToken> tokenBuffer;

    public OptionalInfoCalculator(@NotNull NBTStream original) {
        Objects.requireNonNull(original);

        this.original = original;
    }

    @Nullable
    public NBTToken nextOrNull() throws IOException {
        if (this.tokenBuffer != null) {
            Deque<NBTToken> var10000 = this.tokenBuffer;
            Objects.requireNonNull(var10000);
            NBTToken next = var10000.pollFirst();
            if (next != null) {
                return next;
            }

            this.tokenBuffer = null;
        }

        NBTToken next = this.original.nextOrNull();
        if (next == null) {
            return null;
        } else {
            TokenAndBuffer tokenAndBuffer = this.fillIfNeeded(next);
            if (tokenAndBuffer.getBuffer() != null && !tokenAndBuffer.getBuffer().isEmpty()) {
                this.tokenBuffer = tokenAndBuffer.getBuffer();
            }

            return tokenAndBuffer.getToken();
        }
    }

    @NotNull
    public NBTStream calculateOptionalInfo() {
        return this;
    }

    private TokenAndBuffer fillIfNeeded(NBTToken token) throws IOException {
        if (!(token instanceof NBTToken.ListStart) || ((NBTToken.ListStart) token).getSize().isPresent() && ((NBTToken.ListStart) token).getElementId().isPresent()) {
            if (token instanceof NBTToken.ByteArrayStart) {
                return ((NBTToken.ByteArrayStart) token).getSize().isPresent() ? new TokenAndBuffer(token, null) : this.getFilled(new ByteArrayStartFill());
            } else if (token instanceof NBTToken.IntArrayStart) {
                return ((NBTToken.IntArrayStart) token).getSize().isPresent() ? new TokenAndBuffer(token, null) : this.getFilled(new IntArrayStartFill());
            } else if (token instanceof NBTToken.LongArrayStart && ((NBTToken.LongArrayStart) token).getSize().isEmpty()) {
                return this.getFilled(new LongArrayStartFill());
            } else {
                return token instanceof NBTToken.Name && ((NBTToken.Name) token).getId().isEmpty() ? this.getFilled(new NameFill(((NBTToken.Name) token).getName())) : new TokenAndBuffer(token, null);
            }
        } else {
            return this.getFilled(new ListStartFill((NBTToken.ListStart) token));
        }
    }

    private TokenAndBuffer getFilled(OptionalFill fill) throws IOException {
        ArrayDeque<NBTToken> buffer = new ArrayDeque<>();
        ArrayDeque<NBTToken> consumedTokenStack = new ArrayDeque<>();

        while (true) {
            NBTToken next = buffer.pollFirst();
            if (next == null) {
                NBTToken originalNext = this.original.nextOrNull();
                if (originalNext == null) {
                    throw new NBTParseException("Optional value not filled by the end of token stream");
                }

                TokenAndBuffer tokenAndBuffer = this.fillIfNeeded(originalNext);
                buffer.add(tokenAndBuffer.getToken());
                consumedTokenStack.add(tokenAndBuffer.getToken());
                if (tokenAndBuffer.getBuffer() != null) {
                    buffer.addAll(tokenAndBuffer.getBuffer());
                    consumedTokenStack.addAll(tokenAndBuffer.getBuffer());
                }
            } else {
                NBTToken filled = fill.tryFill(next);
                if (filled != null) {
                    return new TokenAndBuffer(filled, consumedTokenStack);
                }
            }
        }
    }

    private static final class TokenAndBuffer {
        @NotNull
        private final NBTToken token;
        @Nullable
        private final Deque<NBTToken> buffer;

        public TokenAndBuffer(@NotNull NBTToken token, @Nullable Deque<NBTToken> buffer) {
            Objects.requireNonNull(token);

            this.token = token;
            this.buffer = buffer;
        }

        @NotNull
        public NBTToken getToken() {
            return this.token;
        }

        @Nullable
        public Deque<NBTToken> getBuffer() {
            return this.buffer;
        }
    }

    private static final class ListStartFill implements OptionalFill {
        private final int knownSize;
        @Nullable
        private final ValueCounter counter;
        @Nullable
        private NBTTagId elementId;

        public ListStartFill(@NotNull NBTToken.ListStart listStart) {
            Objects.requireNonNull(listStart);

            if (listStart.getSize().isPresent()) {
                this.knownSize = listStart.getSize().getAsInt();
                this.counter = null;
            } else {
                this.knownSize = -1;
                this.counter = new ValueCounter();
            }

            this.elementId = listStart.getElementId().orElse(null);
        }

        @Nullable
        public NBTToken tryFill(@NotNull NBTToken token) {
            Objects.requireNonNull(token);
            if (this.counter == null || !this.counter.isNested()) {
                NBTTagId elementId = this.elementId;
                if (elementId == null) {
                    if (token instanceof NBTToken.ListEnd) {
                        elementId = NBTTagId.END;
                    } else {
                        NBTTagId var10000 = token.getTagId();
                        if (var10000 == null) {
                            throw new NBTParseException("Token doesn't represent a tag directly: " + token);
                        }

                        elementId = var10000;
                    }

                    this.elementId = elementId;
                }

                if (this.counter == null) {
                    OptionalInt var3 = OptionalInt.of(this.knownSize);
                    Intrinsics.checkNotNullExpressionValue(var3, "of(...)");
                    Intrinsics.checkNotNull(elementId);
                    Optional<NBTTagId> var4 = Optional.of(elementId);
                    Intrinsics.checkNotNullExpressionValue(var4, "of(...)");
                    return new NBTToken.ListStart(var3, var4);
                }

                if (token instanceof NBTToken.ListEnd) {
                    OptionalInt var10002 = OptionalInt.of(this.counter.count());
                    Intrinsics.checkNotNullExpressionValue(var10002, "of(...)");
                    Intrinsics.checkNotNull(elementId);
                    Optional<NBTTagId> var10003 = Optional.of(elementId);
                    return new NBTToken.ListStart(var10002, var10003);
                }
            }

            this.counter.add(token);
            return null;
        }
    }

    private static final class ByteArrayStartFill implements OptionalFill {
        private int size;

        public ByteArrayStartFill() {
        }

        @Nullable
        public NBTToken tryFill(@NotNull NBTToken token) {
            Objects.requireNonNull(token, "token");
            if (token instanceof NBTToken.ByteArrayEnd) {
                OptionalInt var10002 = OptionalInt.of(this.size);
                Intrinsics.checkNotNullExpressionValue(var10002, "of(...)");
                return new NBTToken.ByteArrayStart(var10002);
            } else if (token instanceof NBTToken.ByteArrayContent) {
                this.size += ((NBTToken.ByteArrayContent) token).getBuffer().remaining();
                return null;
            } else {
                throw new NBTParseException("Unexpected token: " + token);
            }
        }
    }

    private static final class IntArrayStartFill implements OptionalFill {
        private int size;

        public IntArrayStartFill() {
        }

        @Nullable
        public NBTToken tryFill(@NotNull NBTToken token) {
            Objects.requireNonNull(token, "token");
            if (token instanceof NBTToken.IntArrayEnd) {
                OptionalInt var10002 = OptionalInt.of(this.size);
                Intrinsics.checkNotNullExpressionValue(var10002, "of(...)");
                return new NBTToken.IntArrayStart(var10002);
            } else if (token instanceof NBTToken.IntArrayContent) {
                this.size += ((NBTToken.IntArrayContent) token).getBuffer().remaining();
                return null;
            } else {
                throw new NBTParseException("Unexpected token: " + token);
            }
        }
    }

    private static final class LongArrayStartFill implements OptionalFill {
        private int size;

        public LongArrayStartFill() {
        }

        @Nullable
        public NBTToken tryFill(@NotNull NBTToken token) {
            Objects.requireNonNull(token, "token");
            if (token instanceof NBTToken.LongArrayEnd) {
                OptionalInt var10002 = OptionalInt.of(this.size);
                Intrinsics.checkNotNullExpressionValue(var10002, "of(...)");
                return new NBTToken.LongArrayStart(var10002);
            } else if (token instanceof NBTToken.LongArrayContent) {
                this.size += ((NBTToken.LongArrayContent) token).getBuffer().remaining();
                return null;
            } else {
                throw new NBTParseException("Unexpected token: " + token);
            }
        }
    }

    private static final class NameFill implements OptionalFill {
        @NotNull
        private final String name;

        public NameFill(@NotNull String name) {
            Objects.requireNonNull(name, "name");

            this.name = name;
        }

        @NotNull
        public NBTToken tryFill(@NotNull NBTToken token) {
            Objects.requireNonNull(token, "token");
            NBTTagId id = token.getTagId();
            if (id == null) {
                throw new NBTParseException("Token doesn't represent a tag directly: " + token);
            } else {
                return new NBTToken.Name(this.name, Optional.of(id));
            }
        }
    }

    private interface OptionalFill {
        @Nullable
        NBTToken tryFill(@NotNull NBTToken var1);
    }
}
