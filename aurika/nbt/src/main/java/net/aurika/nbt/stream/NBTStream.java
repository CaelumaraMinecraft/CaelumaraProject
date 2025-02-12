package net.aurika.nbt.stream;

import net.aurika.nbt.internal.AbstractIterator;
import net.aurika.nbt.stream.impl.OptionalInfoCalculator;
import net.aurika.nbt.stream.token.NBTToken;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.Optional;

public interface NBTStream extends NBTStreamable {

    static @NotNull NBTStream of() {
        return () -> null;
    }

    static @NotNull NBTStream of(final NBTToken token) {
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

    static @NotNull NBTStream of(final NBTToken... tokens) {
        return new NBTStream() {
            private int index = 0;

            @Nullable
            public NBTToken nextOrNull() {
                if (index >= tokens.length) {
                    return null;
                } else {
                    NBTToken token = tokens[index];
                    ++index;
                    return token;
                }
            }
        };
    }

    @Nullable NBTToken nextOrNull() throws IOException;

    default Optional<NBTToken> next() throws IOException {
        return Optional.ofNullable(this.nextOrNull());
    }

    default NBTStream calculateOptionalInfo() {
        return new OptionalInfoCalculator(this);
    }

    default @NotNull NBTStream stream() {
        return this;
    }

    default @NotNull Iterator<NBTToken> asIterator() {
        return new AbstractIterator<>() {

            protected @Nullable NBTToken computeNext() {
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
