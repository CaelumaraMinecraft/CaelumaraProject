package top.auspice.data.database.mongo;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.BsonWriter;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;
import top.auspice.utils.internal.Fn;

public final class MongoIdQueryContainer<K> implements Bson {
    private final K a;
    private final Class<K> b;

    public MongoIdQueryContainer(K var1, Class<K> var2) {
        this.a = var1;
        this.b = var2;
    }

    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> var1, CodecRegistry var2) {
        return new BsonDocumentWrapper(this, new a(var2));
    }

    public String toString() {
        return this.getClass().getSimpleName() + '[' + this.a + ']';
    }

    private final class a implements Encoder<MongoIdQueryContainer<K>> {
        private final CodecRegistry a;

        private a(CodecRegistry var2) {
            this.a = var2;
        }

        @Override
        public void encode(BsonWriter bsonWriter, MongoIdQueryContainer<K> kMongoIdQueryContainer, EncoderContext encoderContext) {

        }

        public Class<MongoIdQueryContainer<K>> getEncoderClass() {
            return Fn.cast(MongoIdQueryContainer.class);
        }
    }
}
