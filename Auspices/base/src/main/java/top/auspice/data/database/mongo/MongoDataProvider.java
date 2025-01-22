package top.auspice.data.database.mongo;

import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.server.core.Server;
import top.auspice.server.location.Location;
import top.auspice.server.location.World;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;

import java.util.*;
import java.util.function.*;

public final class MongoDataProvider implements DataProvider, SectionCreatableDataSetter {
    @Nullable
    private final String a;
    @NotNull
    private final Document b;

    public MongoDataProvider(@Nullable String var1, @NotNull Document var2) {
        Objects.requireNonNull(var2, "");
        this.a = var1;
        this.b = var2;
    }

    @NotNull
    public Document getDocument$core() {
        return this.b;
    }

    private String a() {
        String var10000 = this.a;
        if (var10000 == null) {
            throw new IllegalStateException("No key name specified");
        } else {
            return var10000;
        }
    }

    private Document b() {
        if (this.a == null) {
            throw new IllegalStateException("No name specified");
        } else {
            return this.b.get(this.a, Document.class);
        }
    }

    private Document c() {
        if (this.a == null) {
            return this.b;
        } else {
            Object var10000 = this.b.get(this.a, Document.class);
            Objects.requireNonNull(var10000, "");
            return (Document) var10000;
        }
    }

    private Document d() {
        if (this.a == null) {
            return this.b;
        } else {
            Document var1;
            if ((var1 = this.b.get(this.a, Document.class)) == null) {
                MongoDataProvider var3 = this;
                Document var2 = new Document();
                var3.b.append(var3.a, var2);
                return var2;
            } else {
                return var1;
            }
        }
    }

    @NotNull
    public DataProvider get(@NotNull String key) {
        Objects.requireNonNull(key, "");
        if (this.a != null) {
            throw new IllegalStateException("Specified name not processed: " + this.a);
        } else {
            return new MongoDataProvider(key, this.b);
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
    public String asString(@NotNull Supplier<String> var1) {
        Objects.requireNonNull(var1, "");
        String var10000 = this.b.getString(this.a());
        if (var10000 == null) {
            var10000 = var1.get();
        }

        return var10000;
    }

    @Override
    @Nullable
    public UUID asUUID() {
        return this.b.get(this.a(), UUID.class);
    }

    public @Nullable SimpleBlockLocation asSimpleLocation() {
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

    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        Document var1 = this.c();
        String var10002 = var1.getString("world");
        Integer var10003 = var1.getInteger("x");
        Objects.requireNonNull(var10003, "");
        int var2 = var10003.intValue();
        Integer var10004 = var1.getInteger("z");
        Objects.requireNonNull(var10004, "");
        return new SimpleChunkLocation(var10002, var2, var10004.intValue());
    }

    @Nullable
    public Location asLocation() {
        Document document = this.b();
        if (document != null) {
            String worldName = document.getString("world");
            Objects.requireNonNull(worldName, "");
            World world = Server.get().getWorldRegistry().getWorld(worldName);
            Double x = document.getDouble("x");
            Objects.requireNonNull(x, "");
            Double y = document.getDouble("y");
            Objects.requireNonNull(y, "");
            Double z = document.getDouble("z");
            Objects.requireNonNull(z, "");
            return new Location(world, x, y, z, document.getDouble("yaw").floatValue(), document.getDouble("pitch").floatValue());
        } else {
            return null;
        }
    }

    public int asInt(@NotNull IntSupplier var1) {
        Objects.requireNonNull(var1);
        Integer var10000 = this.b.getInteger(this.a());
        return var10000 == null ? var1.getAsInt() : var10000;
    }

    public long asLong(@NotNull LongSupplier var1) {
        Objects.requireNonNull(var1);
        Long var10000 = this.b.getLong(this.a());
        return var10000 == null ? var1.getAsLong() : var10000;
    }

    public float asFloat(@NotNull FloatSupplier var1) {
        Objects.requireNonNull(var1, "");
        Object var10000 = this.b.getDouble(this.a());
        if (var10000 == null) {
            var10000 = var1.getAsFloat();
        }

        return ((Number) var10000).floatValue();
    }

    public double asDouble(@NotNull DoubleSupplier var1) {
        Objects.requireNonNull(var1, "");
        Double var10000 = this.b.getDouble(this.a());
        return var10000 == null ? var1.getAsDouble() : var10000;
    }

    public boolean asBoolean(@NotNull BooleanSupplier var1) {
        Objects.requireNonNull(var1, "");
        Boolean var10000 = this.b.getBoolean(this.a());
        if (var10000 == null) {
            var10000 = var1.getAsBoolean();
        }

        return var10000;
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C var1, @NotNull BiConsumer<C, SectionableDataGetter> var2) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        List var10000 = this.b.get(this.a, List.class);
        if (var10000 == null) {
            return var1;
        } else {

            for (Object var4 : var10000) {
                Objects.requireNonNull(var4);
                var2.accept(var1, createProvider$core(var4));
            }

            return var1;
        }
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M var1, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> var2) {
        Objects.requireNonNull(var1, "");
        Objects.requireNonNull(var2, "");
        Document var10000 = this.b.get(this.a, Document.class);
        if (var10000 == null) {
            return var1;
        } else {

            for (Map.Entry<String, Object> object : var10000.entrySet()) {
                String var5 = object.getKey();
                Object var6 = object.getValue();
                Objects.requireNonNull(var5);
                MongoIDGetter var10002 = new MongoIDGetter(var5);
                Objects.requireNonNull(var6);
                var2.accept(var1, var10002, createProvider$core(var6));
            }

            return var1;
        }
    }

    private void a(Object var1) {
        this.b.append(this.a(), var1);
    }

    public void setString(@Nullable String s) {
        this.a(s);
    }

    public void setInt(int var1) {
        this.a(var1);
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation) {
        this.a(blockLocation);
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation chunkLocation) {
        Objects.requireNonNull(chunkLocation, "");
        this.a(chunkLocation);
    }

    public void setLong(long l) {
        this.a(l);
    }

    public void setFloat(float f) {
        this.a(f);
    }

    public void setDouble(double d) {
        this.a(d);
    }

    public void setBoolean(boolean b) {
        this.a(b);
    }

    public void setUUID(@Nullable UUID uuid) {
        this.a(uuid);
    }

    public void setLocation(@Nullable Location var1) {
        if (var1 != null) {
            Document var10000 = this.d();
            World var10002 = var1.getWorld();
            Objects.requireNonNull(var10002);
            var10000.append("world", var10002.getName()).append("x", var1.getX()).append("y", var1.getY()).append("z", var1.getZ()).append("yaw", var1.getYaw()).append("pitch", var1.getPitch());
        }
    }

    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(c, "");
        Objects.requireNonNull(var2, "");
        List var3 = new ArrayList<>();

        for (V var4 : c) {
            var2.accept(createProvider$core(var3), var4);
        }

        this.b.append(this.a(), var3);
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(var2, "");
        final Document var3 = new Document();

        for (Map.Entry<K, ? extends V> kEntry : m.entrySet()) {
            K var5 = kEntry.getKey();
            V var7 = kEntry.getValue();

            var2.map(var5, new StringMappedIdSetter((s) -> new MongoDataProvider(s, var3)), var7);
        }

        this.b.append(this.a(), var3);
    }

    @NotNull
    public DataProvider createSection(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        Document var2 = new Document();
        this.d().append(var1, var2);
        return new MongoDataProvider(null, var2);
    }

    @NotNull
    public SectionableDataSetter createSection() {
        Document var1 = new Document();
        this.b.append(this.a(), var1);
        return new MongoDataProvider(null, var1);
    }

    @NotNull
    public static DataProvider createProvider$core(@NotNull Object obj) {
        Objects.requireNonNull(obj);
        return obj instanceof Document ? new MongoDataProvider(null, (Document) obj) : new UnknownDataGetter(obj);
    }

    public static final class Companion {
        private Companion() {
        }

        @JvmStatic
        @NotNull
        public DataProvider createProvider$core(@NotNull Object var1) {
            Objects.requireNonNull(var1, "");
            return var1 instanceof Document ? new MongoDataProvider(null, (Document) var1) : new UnknownDataGetter(var1);
        }
    }
}
