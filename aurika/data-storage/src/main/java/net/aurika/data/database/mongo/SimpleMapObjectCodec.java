package net.aurika.data.database.mongo;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataMetaType;
import net.aurika.data.api.structure.SimpleData;
import net.aurika.data.api.structure.SimpleDataObject;
import net.aurika.data.api.structure.SimpleDataObjectTemplate;
import net.aurika.data.api.structure.entries.BooleanMapDataEntry;
import net.aurika.data.api.structure.entries.IntMapDataEntry;
import net.aurika.data.api.structure.entries.MapDataEntry;
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

public class SimpleMapObjectCodec<T extends SimpleDataObject> implements Codec<T> {
    private static final Map<SimpleDataObjectTemplate<?>, SimpleMapObjectCodec<?>> REGISTRY = new HashMap<>();

    private final SimpleDataObjectTemplate<T> template;

    protected SimpleMapObjectCodec(@NotNull SimpleDataObjectTemplate<T> template) {
        Checker.Arg.notNull(template, "template");
        this.template = template;
    }

    @Override
    public void encode(@NotNull BsonWriter writer, @NotNull T value, @Nullable EncoderContext encoderContext) {
        Checker.Arg.notNull(writer, "writer");
        Checker.Arg.notNull(value, "value");
        SimpleData data = value.simpleData();
        Objects.requireNonNull(data, "data");
        writer.writeStartDocument();
        for (MapDataEntry entry : data) {
            writeEntry(writer, entry);
        }
        writer.writeEndDocument();
    }

    @Override
    public T decode(@NotNull BsonReader reader, @Nullable DecoderContext decoderContext) {
        Checker.Arg.notNull(reader, "reader");
        reader.readStartDocument();
        MapDataEntry[] entries = new MapDataEntry[template.size()];
        int i = 0;
        for (Map.Entry<String, DataMetaType> templateEntry : template.templateMap().entrySet()) {
            entries[i] = readEntry(reader, templateEntry.getKey(), templateEntry.getValue());
            i++;
        }
        reader.readEndDocument();
        return template.toObject(SimpleData.of(entries));
    }

    @Override
    public Class<T> getEncoderClass() {
        return template.type();
    }

    public SimpleDataObjectTemplate<T> template() {
        return template;
    }

    private static void writeEntry(@NotNull BsonWriter writer, @NotNull MapDataEntry entry) {
        String key = entry.key();
        switch (entry.type()) {
            case INT -> writer.writeInt32(key, ((IntMapDataEntry) entry).getValue());
            case LONG -> writer.writeInt64(key, ((IntMapDataEntry) entry).getValue());
            case FLOAT, DOUBLE -> writer.writeDouble(key, ((IntMapDataEntry) entry).getValue());
            case BOOLEAN -> writer.writeBoolean(key, ((BooleanMapDataEntry) entry).getValue());
            case STRING -> writer.writeString(key, entry.valueAsString());
        }
    }

    private static MapDataEntry readEntry(@NotNull BsonReader reader, @NotNull String key, @NotNull DataMetaType type) {
        return switch (type) {
            case INT -> MapDataEntry.of(key, reader.readInt32(key));
            case LONG -> MapDataEntry.of(key, reader.readInt64(key));
            case FLOAT, DOUBLE -> MapDataEntry.of(key, reader.readDouble(key));
            case BOOLEAN -> MapDataEntry.of(key, reader.readBoolean(key));
            case STRING -> MapDataEntry.of(key, reader.readString(key));
        };
    }

    public static <T extends SimpleDataObject> SimpleMapObjectCodec<T> getEncoder(@NotNull T simpleDataObject) {
        Checker.Arg.notNull(simpleDataObject, "simpleDataObject");
        SimpleDataObjectTemplate<T> template = (SimpleDataObjectTemplate<T>) simpleDataObject.simpleDataTemplate();
        var encoder = REGISTRY.get(template);
        if (encoder == null) {
            encoder = new SimpleMapObjectCodec<>(template);
            REGISTRY.put(template, encoder);
        }
        return (SimpleMapObjectCodec<T>) encoder;
    }
}
