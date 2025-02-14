package net.aurika.ecliptor.database.mongo;

import net.aurika.ecliptor.api.structured.FunctionsDataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredData;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.api.structured.scalars.*;
import net.aurika.validate.Validate;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class StructDataCodec<T extends StructuredDataObject> implements Codec<T> {
    private static final Map<FunctionsDataStructSchema<?>, StructDataCodec<?>> REGISTRY = new HashMap<>();

    private final FunctionsDataStructSchema<T> template;

    protected StructDataCodec(@NotNull FunctionsDataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        this.template = template;
    }

    @Override
    public void encode(@NotNull BsonWriter writer, @NotNull T value, @Nullable EncoderContext encoderContext) {
        Validate.Arg.notNull(writer, "writer");
        Validate.Arg.notNull(value, "value");
        StructuredData data = value.structuredData();
        Objects.requireNonNull(data, "data");
        writer.writeStartDocument();
        for (var entry : data.data().entrySet()) {
            writeEntry(writer, entry.getKey(), entry.getValue());
        }
        writer.writeEndDocument();
    }

    @Override
    public T decode(@NotNull BsonReader reader, @Nullable DecoderContext decoderContext) {
        Validate.Arg.notNull(reader, "reader");
        reader.readStartDocument();
        LinkedHashMap<String, DataScalar> data = new LinkedHashMap<>(template.size());
        for (var templateEntry : template.template().entrySet()) {
            String key = templateEntry.getKey();
            data.put(key, readEntry(reader, key, templateEntry.getValue()));
        }
        reader.readEndDocument();
        return template.structToObject(StructuredData.structuredData(data));
    }

    @Override
    public Class<T> getEncoderClass() {
        return template.type();
    }

    public FunctionsDataStructSchema<T> template() {
        return template;
    }

    private static void writeEntry(@NotNull BsonWriter writer, @NotNull String key, @NotNull DataScalar scalar) {
        switch (scalar.type()) {
            case INT -> writer.writeInt32(key, ((IntDataScalar) scalar).value());
            case LONG -> writer.writeInt64(key, ((LongDataScalar) scalar).value());
            case FLOAT, DOUBLE -> writer.writeDouble(key, ((DoubleDataScalar) scalar).value());
            case BOOLEAN -> writer.writeBoolean(key, ((BooleanDataScalar) scalar).value());
            case STRING -> writer.writeString(key, scalar.valueToString());
        }
    }

    private static DataScalar readEntry(@NotNull BsonReader reader, @NotNull String key, @NotNull DataScalarType type) {
        return switch (type) {
            case INT -> DataScalar.intDataScalar(reader.readInt32(key));
            case LONG -> DataScalar.longDataScalar(reader.readInt64(key));
            case FLOAT -> DataScalar.floatDataScalar((float) reader.readDouble(key));
            case DOUBLE -> DataScalar.doubleDataScalar(reader.readDouble(key));
            case BOOLEAN -> DataScalar.booleanDataScalar(reader.readBoolean(key));
            case STRING -> DataScalar.stringDataScalar(reader.readString(key));
        };
    }

    public static <T extends StructuredDataObject> StructDataCodec<T> getCodec(@NotNull T struct) {
        Validate.Arg.notNull(struct, "struct");
        FunctionsDataStructSchema<T> template = (FunctionsDataStructSchema<T>) struct.dataStructSchema();
        var encoder = REGISTRY.get(template);
        if (encoder == null) {
            encoder = new StructDataCodec<>(template);
            REGISTRY.put(template, encoder);
        }
        return (StructDataCodec<T>) encoder;
    }
}
