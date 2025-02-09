package net.aurika.data.database.mongo;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.BsonWriter;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SingularMongoIdQueryContainer implements Encoder<SingularMongoIdQueryContainer>, Bson {
    public SingularMongoIdQueryContainer() {
    }

    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> klass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, this);
    }

    public void encode(@NotNull BsonWriter writer, @NotNull SingularMongoIdQueryContainer container, @Nullable EncoderContext context) {
        writer.writeStartDocument();
        writer.writeName(MongoDBDatabase.PRIMARY_KEY_ID);
        writer.writeNull();
        writer.writeEndDocument();
    }

    public Class<SingularMongoIdQueryContainer> getEncoderClass() {
        return SingularMongoIdQueryContainer.class;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
