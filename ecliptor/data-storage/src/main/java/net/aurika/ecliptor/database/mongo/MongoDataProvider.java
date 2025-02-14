package net.aurika.ecliptor.database.mongo;

import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredData;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.api.structured.scalars.DataScalar;
import net.aurika.ecliptor.api.structured.scalars.DataScalarType;
import net.aurika.ecliptor.database.dataprovider.*;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import net.aurika.validate.Validate;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;

public class MongoDataProvider implements DataProvider, SectionCreatableDataSetter {

    private final @Nullable String name;
    private final @NotNull Document document;

    public MongoDataProvider(@Nullable String name, @NotNull Document document) {
        Objects.requireNonNull(document, "");
        this.name = name;
        this.document = document;
    }

    public final @NotNull Document getDocument$core() {
        return this.document;
    }

    private @NotNull String __name_notNull() {
        if (name == null) {
            throw new IllegalStateException("No key name specified");
        } else {
            return name;
        }
    }

    private @Nullable Document b() {
        if (this.name == null) {
            throw new IllegalStateException("No name specified");
        } else {
            return this.document.get(this.name, Document.class);
        }
    }

    private @NotNull Document c() {
        if (name == null) {
            return document;
        } else {
            Document var10000 = this.document.get(name, Document.class);
            Objects.requireNonNull(var10000, "document");
            return var10000;
        }
    }

    private @NotNull Document d() {
        if (this.name == null) {
            return this.document;
        } else {
            Document var1 = this.document.get(name, Document.class);
            if (var1 == null) {
                Document var2 = new Document();
                document.append(name, var2);
                return var2;
            } else {
                return var1;
            }
        }
    }

    @Override
    public @NotNull MongoDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        if (this.name != null) {
            throw new IllegalStateException("Specified name not processed: " + this.name);
        } else {
            return new MongoDataProvider(key, this.document);
        }
    }

    @Override
    public @NotNull DataProvider asSection() {
        Document var10003 = this.b();
        if (var10003 == null) {
            var10003 = new Document();
        }
        return new MongoDataProvider(null, var10003);
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        Integer i = this.document.getInteger(this.__name_notNull());
        return i != null ? i : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def);
        Long l = this.document.getLong(this.__name_notNull());
        return l != null ? l : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "");
        Number n = this.document.getDouble(this.__name_notNull());
        if (n == null) {
            n = def.getAsFloat();
        }

        return n.floatValue();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "");
        Double var10000 = this.document.getDouble(this.__name_notNull());
        return var10000 == null ? def.getAsDouble() : var10000;
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "");
        Boolean var10000 = this.document.getBoolean(this.__name_notNull());
        if (var10000 == null) {
            var10000 = def.getAsBoolean();
        }

        return var10000;
    }

    @Override
    public @Nullable String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        String var10000 = this.document.getString(this.__name_notNull());
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000;
    }

    @Override
    public @Nullable UUID asUUID() {
        return this.document.get(this.__name_notNull(), UUID.class);
    }

    @Override
    public <T extends StructuredDataObject> @NotNull T asStruct(@NotNull DataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        Document doc = c();
        LinkedHashMap<String, DataScalar> structData = new LinkedHashMap<>(template.size());
        for (Map.Entry<String, DataScalarType> templateEntry : template.template().entrySet()) {
            structData.put(templateEntry.getKey(), readEntry(doc, templateEntry.getKey(), templateEntry.getValue()));
        }
        return template.structToObject(StructuredData.structuredData(structData));
    }

    static @NotNull DataScalar readEntry(@NotNull Document document, @NotNull String key, @NotNull DataScalarType type) {
        return switch (type) {
            case INT -> DataScalar.intDataScalar(document.getInteger(key));
            case LONG -> DataScalar.longDataScalar(document.getLong(key));
            case FLOAT -> DataScalar.floatDataScalar(document.getDouble(key).floatValue());
            case DOUBLE -> DataScalar.doubleDataScalar(document.getDouble(key));
            case BOOLEAN -> DataScalar.booleanDataScalar(document.getBoolean(key));
            case STRING -> DataScalar.stringDataScalar(document.getString(key));
        };
    }

    @Override
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(handler, "handler");
        Collection<?> var10000 = this.document.get(this.name, Collection.class);  // 之前存放的数据为 collection
        if (var10000 != null) {
            for (Object ele : var10000) {
                Objects.requireNonNull(ele);
                handler.accept(c, createProvider$core(ele));
            }
        }
        return c;
    }

    @Override
    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        Objects.requireNonNull(m, "m");
        Objects.requireNonNull(handler, "");
        Document var10000 = this.document.get(this.name, Document.class);
        if (var10000 == null) {
            return m;
        } else {

            for (Map.Entry<String, Object> object : var10000.entrySet()) {
                String var5 = object.getKey();
                Object var6 = object.getValue();
                Objects.requireNonNull(var5);
                MongoIDGetter idGetter = new MongoIDGetter(var5);
                Objects.requireNonNull(var6);
                handler.accept(m, idGetter, createProvider$core(var6));
            }

            return m;
        }
    }

    private void a(Object value) {
        this.document.append(this.__name_notNull(), value);
    }

    @Override
    public void setString(@NotNull String value) {
        this.a(value);
    }

    @Override
    public void setInt(int value) {
        this.a(value);
    }

    @Override
    public void setLong(long value) {
        this.a(value);
    }

    @Override
    public void setFloat(float value) {
        this.a(value);
    }

    @Override
    public void setDouble(double value) {
        this.a(value);
    }

    @Override
    public void setBoolean(boolean value) {
        this.a(value);
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        this.a(value);
    }

    @Override
    public void setStruct(@NotNull StructuredDataObject value) {
        Validate.Arg.notNull(value, "value");
        StructuredData data = value.structuredData();
        Document document = d();
        for (var entry : data.data().entrySet()) {
            Objects.requireNonNull(entry, "entry");
            String entryKey = entry.getKey();
            Object entryValue = entry.getValue().valueAsObject();
            Objects.requireNonNull(entryKey, "entryKey");
            Objects.requireNonNull(entryValue, "entryValue");
            document.append(entryKey, entryValue);
        }
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "handler");
        List var3 = new ArrayList<>();

        for (V var4 : value) {
            handler.accept(createProvider$core(var3), var4);
        }

        this.document.append(this.__name_notNull(), var3);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "handler");
        final Document var3 = new Document();

        for (Map.Entry<K, ? extends V> kEntry : value.entrySet()) {
            K var5 = kEntry.getKey();
            V var7 = kEntry.getValue();

            handler.map(var5, new StringMappedIdSetter((s) -> new MongoDataProvider(s, var3)), var7);
        }

        this.document.append(this.__name_notNull(), var3);
    }

    @Override
    @NotNull
    public DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        Document sub = new Document();
        this.d().append(key, sub);
        return new MongoDataProvider(null, sub);
    }

    @Override
    public @NotNull SectionableDataSetter createSection() {
        Document var1 = new Document();
        this.document.append(this.__name_notNull(), var1);
        return new MongoDataProvider(null, var1);
    }

    public static @NotNull DataProvider createProvider$core(@NotNull Object obj) {
        Objects.requireNonNull(obj, "obj");
        return obj instanceof Document ? new MongoDataProvider(null, (Document) obj) : new UnknownDataGetter(obj);
    }
}
