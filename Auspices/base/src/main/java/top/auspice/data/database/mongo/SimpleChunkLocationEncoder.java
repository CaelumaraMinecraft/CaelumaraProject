package top.auspice.data.database.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;

import java.util.Objects;

public final class SimpleChunkLocationEncoder implements Codec<SimpleChunkLocation> {
    public SimpleChunkLocationEncoder() {
    }

    public final void encode(@NotNull BsonWriter var1, @NotNull SimpleChunkLocation var2, @Nullable EncoderContext var3) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        (var1 = var1).writeStartDocument();
        var1.writeString("world", var2.getWorld());
        var1.writeInt32("x", var2.getX());
        var1.writeInt32("z", var2.getZ());
        var1.writeEndDocument();
    }

    @NotNull
    public final SimpleChunkLocation decode(@NotNull BsonReader bsonReader, @Nullable DecoderContext context) {
        Objects.requireNonNull(bsonReader);
        bsonReader.readStartDocument();
        String var5 = bsonReader.readString("world");
        int var3 = bsonReader.readInt32("x");
        int var4 = bsonReader.readInt32("z");
        bsonReader.readEndDocument();
        return new SimpleChunkLocation(var5, var3, var4);
    }

    @NotNull
    public final Class<SimpleChunkLocation> getEncoderClass() {
        return SimpleChunkLocation.class;
    }
}
