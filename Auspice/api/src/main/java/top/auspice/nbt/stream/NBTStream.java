package top.auspice.nbt.stream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.internal.AbstractIterator;
import top.auspice.nbt.stream.impl.OptionalInfoCalculator;
import top.auspice.nbt.stream.token.NBTToken;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Optional;

public interface NBTStream extends NBTStreamable {
    static NBTStream of() {
        return () -> null;
    }

    static NBTStream of(final NBTToken token) {
        return new NBTStream() {
            private NBTToken nextToken = token;

            @Nullable
            public NBTToken nextOrNull() {
                NBTToken token = this.nextToken;
                this.nextToken = null;
                return token;
            }
        };
    }

    static NBTStream of(final NBTToken... tokens) {
        return new NBTStream() {
            private int index = 0;

            @Nullable
            public NBTToken nextOrNull() {
                if (this.index >= tokens.length) {
                    return null;
                } else {
                    NBTToken token = tokens[this.index];
                    ++this.index;
                    return token;
                }
            }
        };
    }

    @Nullable
    NBTToken nextOrNull() throws IOException;

    default Optional<NBTToken> next() throws IOException {
        return Optional.ofNullable(this.nextOrNull());
    }

    default NBTStream calculateOptionalInfo() {
        return new OptionalInfoCalculator(this);
    }

    @NotNull
    default NBTStream stream() {
        return this;
    }

    @NotNull
    default Iterator<NBTToken> asIterator() {
        return new AbstractIterator<>() {
            @Nullable
            protected NBTToken computeNext() {
                try {
                    NBTToken NBTToken = NBTStream.this.nextOrNull();
                    return NBTToken == null ? this.end() : NBTToken;
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        };
    }
}
