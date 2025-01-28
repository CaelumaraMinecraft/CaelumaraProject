package net.aurika.data.database.mongo;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;

import java.util.Objects;

public final class SimpleLocationEncoder implements Codec<SimpleBlockLocation> {
    public SimpleLocationEncoder() {
    }

    public void encode(@NotNull BsonWriter bsonWriter, @NotNull SimpleBlockLocation location, @Nullable EncoderContext context) {
        Objects.requireNonNull(bsonWriter);
        Objects.requireNonNull(location);
        bsonWriter.writeStartDocument();
        bsonWriter.writeString("world", location.getWorld());
        bsonWriter.writeInt32("x", location.getX());
        bsonWriter.writeInt32("y", location.getY());
        bsonWriter.writeInt32("z", location.getZ());
        bsonWriter.writeEndDocument();
    }

    public @NotNull SimpleBlockLocation decode(@NotNull BsonReader bsonReader, @Nullable DecoderContext context) {
        Objects.requireNonNull(bsonReader);
        bsonReader.readStartDocument();
        String world = bsonReader.readString("world");
        int x = bsonReader.readInt32("x");
        int y = bsonReader.readInt32("y");
        int z = bsonReader.readInt32("z");
        bsonReader.readEndDocument();
        return new SimpleBlockLocation(world, x, y, z);
    }

    public @NotNull Class<SimpleBlockLocation> getEncoderClass() {
        return SimpleBlockLocation.class;
    }
}
