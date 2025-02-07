package top.auspice.nbt.stream.internal;

import org.jetbrains.annotations.Nullable;
import top.auspice.nbt.stream.NBTStream;
import top.auspice.nbt.stream.token.NBTToken;

import java.io.IOException;

public class SurroundingNBTStream implements NBTStream {
    private NBTToken prefix;
    private NBTStream stream;
    private NBTToken suffix;

    public SurroundingNBTStream(NBTToken prefix, NBTStream stream, NBTToken suffix) {
        this.prefix = prefix;
        this.stream = stream;
        this.suffix = suffix;
    }

    @Nullable
    public NBTToken nextOrNull() throws IOException {
        if (this.prefix != null) {
            NBTToken token = this.prefix;
            this.prefix = null;
            return token;
        } else {
            NBTToken token = this.stream == null ? null : this.stream.nextOrNull();
            if (token == null) {
                this.stream = null;
                token = this.suffix;
                this.suffix = null;
            }

            return token;
        }
    }
}
