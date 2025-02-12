package net.aurika.nbt.stream.internal;

import org.jetbrains.annotations.Nullable;
import net.aurika.nbt.stream.NBTStream;
import net.aurika.nbt.stream.NBTStreamable;
import net.aurika.nbt.stream.token.NBTToken;

import java.io.IOException;
import java.util.Iterator;

public class FlatteningNBTStream implements NBTStream {
    private final Iterator<? extends NBTStreamable> streamables;
    private NBTStream current;

    public FlatteningNBTStream(Iterator<? extends NBTStreamable> streamables) {
        this.streamables = streamables;
    }

    @Nullable
    public NBTToken nextOrNull() throws IOException {
        while (true) {
            if (this.current == null) {
                if (!this.streamables.hasNext()) {
                    return null;
                }

                this.current = this.streamables.next().stream();
            }

            NBTToken token = this.current.nextOrNull();
            if (token != null) {
                return token;
            }

            this.current = null;
        }
    }
}
