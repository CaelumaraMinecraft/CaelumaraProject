package net.aurika.data.database.mongo;

import net.aurika.checker.Checker;
import net.aurika.data.api.bundles.scalars.DataScalarType;
import net.aurika.data.api.bundles.BundledData;
import net.aurika.data.api.bundles.BundledDataLike;
import net.aurika.data.api.bundles.DataBundleSchema;
import net.aurika.data.api.bundles.entries.BooleanEntry;
import net.aurika.data.api.bundles.entries.IntEntry;
import net.aurika.data.api.bundles.SimpleMappingDataEntry;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleMapObjectCodec<T extends BundledDataLike> implements Codec<T> {
    private static final Map<DataBundleSchema<?>, SimpleMapObjectCodec<?>> REGISTRY = new HashMap<>();

    private final DataBundleSchema<T> template;

    protected SimpleMapObjectCodec(@NotNull DataBundleSchema<T> template) {
        Checker.Arg.notNull(template, "template");
        this.template = template;
    }

    @Override
    public void encode(@NotNull BsonWriter writer, @NotNull T value, @Nullable EncoderContext encoderContext) {
        Checker.Arg.notNull(writer, "writer");
        Checker.Arg.notNull(value, "value");
        BundledData data = value.simpleData();
        Objects.requireNonNull(data, "data");
        writer.writeStartDocument();
        for (SimpleMappingDataEntry entry : data) {
            writeEntry(writer, entry);
        }
        writer.writeEndDocument();
    }

    @Override
    public T decode(@NotNull BsonReader reader, @Nullable DecoderContext decoderContext) {
        Checker.Arg.notNull(reader, "reader");
        reader.readStartDocument();
        SimpleMappingDataEntry[] entries = new SimpleMappingDataEntry[template.size()];
        int i = 0;
        for (Map.Entry<String, DataScalarType> templateEntry : template.templateMap().entrySet()) {
            entries[i] = readEntry(reader, templateEntry.getKey(), templateEntry.getValue());
            i++;
        }
        reader.readEndDocument();
        return template.toObject(BundledData.of(entries));
    }

    @Override
    public Class<T> getEncoderClass() {
        return template.type();
    }

    public DataBundleSchema<T> template() {
        return template;
    }

    private static void writeEntry(@NotNull BsonWriter writer, @NotNull SimpleMappingDataEntry entry) {
        String key = entry.key();
        switch (entry.type()) {
            case INT -> writer.writeInt32(key, ((IntEntry) entry).getValue());
            case LONG -> writer.writeInt64(key, ((IntEntry) entry).getValue());
            case FLOAT, DOUBLE -> writer.writeDouble(key, ((IntEntry) entry).getValue());
            case BOOLEAN -> writer.writeBoolean(key, ((BooleanEntry) entry).getValue());
            case STRING -> writer.writeString(key, entry.valueAsString());
        }
    }

    private static SimpleMappingDataEntry readEntry(@NotNull BsonReader reader, @NotNull String key, @NotNull DataScalarType type) {
        return switch (type) {
            case INT -> SimpleMappingDataEntry.of(key, reader.readInt32(key));
            case LONG -> SimpleMappingDataEntry.of(key, reader.readInt64(key));
            case FLOAT, DOUBLE -> SimpleMappingDataEntry.of(key, reader.readDouble(key));
            case BOOLEAN -> SimpleMappingDataEntry.of(key, reader.readBoolean(key));
            case STRING -> SimpleMappingDataEntry.of(key, reader.readString(key));
        };
    }

    public static <T extends BundledDataLike> SimpleMapObjectCodec<T> getEncoder(@NotNull T simpleDataObject) {
        Checker.Arg.notNull(simpleDataObject, "simpleDataObject");
        DataBundleSchema<T> template = (DataBundleSchema<T>) simpleDataObject.simpleDataTemplate();
        var encoder = REGISTRY.get(template);
        if (encoder == null) {
            encoder = new SimpleMapObjectCodec<>(template);
            REGISTRY.put(template, encoder);
        }
        return (SimpleMapObjectCodec<T>) encoder;
    }
}
