package net.aurika.data.database.mongo;

import net.aurika.data.database.dataprovider.*;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;

import java.util.*;
import java.util.function.*;

public class MongoDataProvider implements DataProvider, SectionCreatableDataSetter {
    @Nullable
    private final String keyName;
    @NotNull
    private final Document document;

    public MongoDataProvider(@Nullable String var1, @NotNull Document document) {
        Objects.requireNonNull(document, "");
        this.keyName = var1;
        this.document = document;
    }

    @NotNull
    public final Document getDocument$core() {
        return this.document;
    }

    private String a() {
        String var10000 = this.keyName;
        if (var10000 == null) {
            throw new IllegalStateException("No key name specified");
        } else {
            return var10000;
        }
    }

    private Document b() {
        if (this.keyName == null) {
            throw new IllegalStateException("No name specified");
        } else {
            return this.document.get(this.keyName, Document.class);
        }
    }

    private Document c() {
        if (this.keyName == null) {
            return this.document;
        } else {
            Object var10000 = this.document.get(this.keyName, Document.class);
            Objects.requireNonNull(var10000, "");
            return (Document) var10000;
        }
    }

    private Document d() {
        if (this.keyName == null) {
            return this.document;
        } else {
            Document var1;
            if ((var1 = this.document.get(this.keyName, Document.class)) == null) {
                MongoDataProvider var3 = this;
                Document var2 = new Document();
                var3.document.append(var3.keyName, var2);
                return var2;
            } else {
                return var1;
            }
        }
    }

    public @NotNull MongoDataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "");
        if (this.keyName != null) {
            throw new IllegalStateException("Specified name not processed: " + this.keyName);
        } else {
            return new MongoDataProvider(key, this.document);
        }
    }

    @NotNull
    public DataProvider asSection() {
        Document var10003 = this.b();
        if (var10003 == null) {
            var10003 = new Document();
        }
        return new MongoDataProvider(null, var10003);
    }

    @Nullable
    public String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        String var10000 = this.document.getString(this.a());
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000;
    }

    @Override
    @Nullable
    public UUID asUUID() {
        return this.document.get(this.a(), UUID.class);
    }

    public @NotNull SimpleBlockLocation asSimpleLocation() {
        Document document = this.c();
        String worldName = document.getString("world");
        Integer x = document.getInteger("x");
        Objects.requireNonNull(x, "");
        Integer y = document.getInteger("y");
        Objects.requireNonNull(y, "");
        Integer z = document.getInteger("z");
        Objects.requireNonNull(z, "");
        return new SimpleBlockLocation(worldName, x, y, z);
    }

    public @NotNull SimpleChunkLocation asSimpleChunkLocation() {
        Document var1 = this.c();
        String world = var1.getString("world");
        Integer x = var1.getInteger("x");
        Objects.requireNonNull(x, "");
        Integer z = var1.getInteger("z");
        Objects.requireNonNull(z, "");
        return new SimpleChunkLocation(world, x, z);
    }

    @Nullable
    public SimpleLocation asLocation() {
        Document document = this.b();
        if (document != null) {
            String world = document.getString("world");
            Double x = document.getDouble("x");
            Objects.requireNonNull(x, "");
            Double y = document.getDouble("y");
            Objects.requireNonNull(y, "");
            Double z = document.getDouble("z");
            Objects.requireNonNull(z, "");
            return new SimpleLocation(world, x, y, z, document.getDouble("yaw").floatValue(), document.getDouble("pitch").floatValue());
        } else {
            return null;
        }
    }

    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        Integer var10000 = this.document.getInteger(this.a());
        return var10000 == null ? def.getAsInt() : var10000;
    }

    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def);
        Long var10000 = this.document.getLong(this.a());
        return var10000 == null ? def.getAsLong() : var10000;
    }

    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.document.getDouble(this.a());
        if (var10000 == null) {
            var10000 = def.getAsFloat();
        }

        return var10000.floatValue();
    }

    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "");
        Double var10000 = this.document.getDouble(this.a());
        return var10000 == null ? def.getAsDouble() : var10000;
    }

    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "");
        Boolean var10000 = this.document.getBoolean(this.a());
        if (var10000 == null) {
            var10000 = def.getAsBoolean();
        }

        return var10000;
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c);
        Objects.requireNonNull(dataProcessor);
        List var10000 = this.document.get(this.keyName, List.class);
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
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(dataProcessor, "");
        Document var10000 = this.document.get(this.keyName, Document.class);
        if (var10000 == null) {
            return m;
        } else {

            for (Map.Entry<String, Object> object : var10000.entrySet()) {
                String var5 = object.getKey();
                Object var6 = object.getValue();
                Objects.requireNonNull(var5);
                MongoIDGetter var10002 = new MongoIDGetter(var5);
                Objects.requireNonNull(var6);
                dataProcessor.accept(m, var10002, createProvider$core(var6));
            }

            return m;
        }
    }

    private void a(Object value) {
        this.document.append(this.a(), value);
    }

    public void setString(@NotNull String value) {
        this.a(value);
    }

    public void setInt(int value) {
        this.a(value);
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        this.a(value);
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation value) {
        Objects.requireNonNull(value, "");
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

    public void setLocation(@Nullable SimpleLocation var1) {
        if (var1 != null) {
            Document var10000 = this.d();
            String var10002 = var1.getWorld();
            Objects.requireNonNull(var10002);
            var10000.append("world", var10002).append("x", var1.getX()).append("y", var1.getY()).append("z", var1.getZ()).append("yaw", var1.getYaw()).append("pitch", var1.getPitch());
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        List var3 = new ArrayList<>();

        for (V var4 : value) {
            var2.accept(createProvider$core(var3), var4);
        }

        this.document.append(this.a(), var3);
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        final Document var3 = new Document();

        for (Map.Entry<K, ? extends V> kEntry : value.entrySet()) {
            K var5 = kEntry.getKey();
            V var7 = kEntry.getValue();

            var2.map(var5, new StringMappedIdSetter((s) -> new MongoDataProvider(s, var3)), var7);
        }

        this.document.append(this.a(), var3);
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
        this.document.append(this.a(), var1);
        return new MongoDataProvider(null, var1);
    }

    public static @NotNull DataProvider createProvider$core(@NotNull Object obj) {
        Objects.requireNonNull(obj);
        return obj instanceof Document ? new MongoDataProvider(null, (Document) obj) : new UnknownDataGetter(obj);
    }

    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public DataProvider createProvider$core(@NotNull Object var1) {
            Objects.requireNonNull(var1, "");
            return var1 instanceof Document ? new MongoDataProvider(null, (Document) var1) : new UnknownDataGetter(var1);
        }
    }
}
