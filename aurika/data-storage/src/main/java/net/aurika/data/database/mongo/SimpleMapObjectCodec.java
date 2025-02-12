package net.aurika.data.database.mongo;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataUnitType;
import net.aurika.data.api.structure.DataUnits;
import net.aurika.data.api.structure.DataUnitsLike;
import net.aurika.data.api.structure.SimpleDataMapObjectTemplate;
import net.aurika.data.api.structure.entries.BooleanEntry;
import net.aurika.data.api.structure.entries.IntEntry;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
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

public class SimpleMapObjectCodec<T extends DataUnitsLike> implements Codec<T> {
    private static final Map<SimpleDataMapObjectTemplate<?>, SimpleMapObjectCodec<?>> REGISTRY = new HashMap<>();

    private final SimpleDataMapObjectTemplate<T> template;

    protected SimpleMapObjectCodec(@NotNull SimpleDataMapObjectTemplate<T> template) {
        Checker.Arg.notNull(template, "template");
        this.template = template;
    }

    @Override
    public void encode(@NotNull BsonWriter writer, @NotNull T value, @Nullable EncoderContext encoderContext) {
        Checker.Arg.notNull(writer, "writer");
        Checker.Arg.notNull(value, "value");
        DataUnits data = value.simpleData();
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
        for (Map.Entry<String, DataUnitType> templateEntry : template.templateMap().entrySet()) {
            entries[i] = readEntry(reader, templateEntry.getKey(), templateEntry.getValue());
            i++;
        }
        reader.readEndDocument();
        return template.toObject(DataUnits.of(entries));
    }

    @Override
    public Class<T> getEncoderClass() {
        return template.type();
    }

    public SimpleDataMapObjectTemplate<T> template() {
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

    private static SimpleMappingDataEntry readEntry(@NotNull BsonReader reader, @NotNull String key, @NotNull DataUnitType type) {
        return switch (type) {
            case INT -> SimpleMappingDataEntry.of(key, reader.readInt32(key));
            case LONG -> SimpleMappingDataEntry.of(key, reader.readInt64(key));
            case FLOAT, DOUBLE -> SimpleMappingDataEntry.of(key, reader.readDouble(key));
            case BOOLEAN -> SimpleMappingDataEntry.of(key, reader.readBoolean(key));
            case STRING -> SimpleMappingDataEntry.of(key, reader.readString(key));
        };
    }

    public static <T extends DataUnitsLike> SimpleMapObjectCodec<T> getEncoder(@NotNull T simpleDataObject) {
        Checker.Arg.notNull(simpleDataObject, "simpleDataObject");
        SimpleDataMapObjectTemplate<T> template = (SimpleDataMapObjectTemplate<T>) simpleDataObject.simpleDataTemplate();
        var encoder = REGISTRY.get(template);
        if (encoder == null) {
            encoder = new SimpleMapObjectCodec<>(template);
            REGISTRY.put(template, encoder);
        }
        return (SimpleMapObjectCodec<T>) encoder;
    }
}
