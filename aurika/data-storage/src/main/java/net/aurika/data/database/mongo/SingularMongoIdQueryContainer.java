package net.aurika.data.database.mongo;

import org.bson.BsonDocument;
import org.bson.BsonDocumentWrapper;
import org.bson.BsonWriter;
import org.bson.codecs.Encoder;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.conversions.Bson;

public class SingularMongoIdQueryContainer implements Encoder<SingularMongoIdQueryContainer>, Bson {
    public SingularMongoIdQueryContainer() {
    }

    public <TDocument> BsonDocument toBsonDocument(Class<TDocument> klass, CodecRegistry codecRegistry) {
        return new BsonDocumentWrapper<>(this, this);
    }

    public void encode(BsonWriter var1, SingularMongoIdQueryContainer container, EncoderContext context) {
        var1.writeStartDocument();
        var1.writeName("_id");
        var1.writeNull();
        var1.writeEndDocument();
    }

    public Class<SingularMongoIdQueryContainer> getEncoderClass() {
        return SingularMongoIdQueryContainer.class;
    }

    public String toString() {
        return this.getClass().getSimpleName();
    }
}
