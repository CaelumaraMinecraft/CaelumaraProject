package net.aurika.data.database.mongo;

import net.aurika.checker.Checker;
import net.aurika.data.api.structure.DataUnits;
import net.aurika.data.api.structure.SimpleDataMapObjectTemplate;
import net.aurika.data.api.structure.SimpleMappingDataEntry;
import net.aurika.data.database.dataprovider.*;
import net.aurika.utils.function.FloatSupplier;
import net.aurika.utils.function.TriConsumer;
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

    private @NotNull String name_notNull() {
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
            Object var10000 = this.document.get(name, Document.class);
            Objects.requireNonNull(var10000, "");
            return (Document) var10000;
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

    public @NotNull MongoDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        if (this.name != null) {
            throw new IllegalStateException("Specified name not processed: " + this.name);
        } else {
            return new MongoDataProvider(key, this.document);
        }
    }

    public @NotNull DataProvider asSection() {
        Document var10003 = this.b();
        if (var10003 == null) {
            var10003 = new Document();
        }
        return new MongoDataProvider(null, var10003);
    }

    public @Nullable String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        String var10000 = this.document.getString(this.name_notNull());
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000;
    }

    @Override
    public @Nullable UUID asUUID() {
        return this.document.get(this.name_notNull(), UUID.class);
    }

    @Override
    public <T> T asObject(SimpleDataMapObjectTemplate<T> template) {
        return null;
    }

    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        Integer i = this.document.getInteger(this.name_notNull());
        return i != null ? i : def.getAsInt();
    }

    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def);
        Long l = this.document.getLong(this.name_notNull());
        return l != null ? l : def.getAsLong();
    }

    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "");
        Number n = this.document.getDouble(this.name_notNull());
        if (n == null) {
            n = def.getAsFloat();
        }

        return n.floatValue();
    }

    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "");
        Double var10000 = this.document.getDouble(this.name_notNull());
        return var10000 == null ? def.getAsDouble() : var10000;
    }

    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "");
        Boolean var10000 = this.document.getBoolean(this.name_notNull());
        if (var10000 == null) {
            var10000 = def.getAsBoolean();
        }

        return var10000;
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c);
        Objects.requireNonNull(dataProcessor);
        List var10000 = this.document.get(this.name, List.class);
        if (var10000 == null) {
            return c;
        } else {

            for (Object var4 : var10000) {
                Objects.requireNonNull(var4);
                dataProcessor.accept(c, createProvider$core(var4));
            }

            return c;
        }
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m, "m");
        Objects.requireNonNull(dataProcessor, "");
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
                dataProcessor.accept(m, idGetter, createProvider$core(var6));
            }

            return m;
        }
    }

    private void a(Object value) {
        this.document.append(this.name_notNull(), value);
    }

    public void setString(@NotNull String value) {
        this.a(value);
    }

    public void setInt(int value) {
        this.a(value);
    }

    public void setLong(long value) {
        this.a(value);
    }

    public void setFloat(float value) {
        this.a(value);
    }

    public void setDouble(double value) {
        this.a(value);
    }

    public void setBoolean(boolean value) {
        this.a(value);
    }

    public void setUUID(@Nullable UUID value) {
        this.a(value);
    }

    @Override
    public void setObject(@NotNull DataUnits value) {
        Checker.Arg.notNull(value, "value");
        Document document = d();
        for (SimpleMappingDataEntry entry : value) {
            Objects.requireNonNull(entry, "entry");
            String entryKey = entry.key();
            Object entryValue = entry.valueAsObject();
            Objects.requireNonNull(entryKey, "entryKey");
            Objects.requireNonNull(entryValue, "entryValue");
            document.append(entryKey, entryValue);
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(handler, "");
        List var3 = new ArrayList<>();

        for (V var4 : value) {
            handler.accept(createProvider$core(var3), var4);
        }

        this.document.append(this.name_notNull(), var3);
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(handler, "");
        final Document var3 = new Document();

        for (Map.Entry<K, ? extends V> kEntry : value.entrySet()) {
            K var5 = kEntry.getKey();
            V var7 = kEntry.getValue();

            handler.map(var5, new StringMappedIdSetter((s) -> new MongoDataProvider(s, var3)), var7);
        }

        this.document.append(this.name_notNull(), var3);
    }

    @NotNull
    public DataProvider createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        Document var2 = new Document();
        this.d().append(key, var2);
        return new MongoDataProvider(null, var2);
    }

    @NotNull
    public SectionableDataSetter createSection() {
        Document var1 = new Document();
        this.document.append(this.name_notNull(), var1);
        return new MongoDataProvider(null, var1);
    }

    public static @NotNull DataProvider createProvider$core(@NotNull Object obj) {
        Objects.requireNonNull(obj);
        return obj instanceof Document ? new MongoDataProvider(null, (Document) obj) : new UnknownDataGetter(obj);
    }
}
