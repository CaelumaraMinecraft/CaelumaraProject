package net.aurika.ecliptor.database.mongo;

import net.aurika.util.unsafe.fn.Fn;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.BsonWriter;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MongoIdQueryContainer<K> implements Bson {
    private final K obj;
    private final Class<K> type;

    public MongoIdQueryContainer(K obj, Class<K> type) {
        super();
        this.obj = obj;
        this.type = type;
    }

    @Override
    public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> clazz, final CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, new a(codecRegistry));
    }

    @Override
    public @NotNull String toString() {
        return this.getClass().getSimpleName() + '[' + this.obj + ']';
    }

    private final class a implements Encoder<MongoIdQueryContainer<K>> {
        private final CodecRegistry codecRegistry;

        private a(final CodecRegistry codecRegistry) {
            this.codecRegistry = codecRegistry;
        }

        @Override
        public Class<MongoIdQueryContainer<K>> getEncoderClass() {
            return Fn.cast(MongoIdQueryContainer.class);
        }

        @Override
        public void encode(@NotNull BsonWriter bsonWriter, @NotNull MongoIdQueryContainer<K> value, @Nullable EncoderContext encoderContext) {
            bsonWriter.writeStartDocument();
            bsonWriter.writeName("_id");
            encoderContext.encodeWithChildContext(this.codecRegistry.get(value.type), bsonWriter, value.obj);
            bsonWriter.writeEndDocument();
        }
    }
}