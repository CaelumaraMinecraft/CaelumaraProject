package top.auspice.data.database.mongo;

import kotlin.KotlinNothingValueException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.database.dataprovider.*;
import top.auspice.server.location.OldLocation;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class UnknownDataGetter implements DataProvider {
    @NotNull
    private final Object value;

    public UnknownDataGetter(@NotNull Object value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Operation unsupported for value: " + this.value);
    }

    @NotNull
    public DataProvider get(@NotNull String key) {
        throw this.unsupported();
    }

    public void setString(@Nullable String s) {
        if (s != null) {
            this.c().add(s);
        }
    }

    public void setInt(int var1) {
        this.c().add(var1);
    }

    public void setLong(long l) {
        this.c().add(l);
    }

    public void setDouble(double d) {
        this.c().add(d);
    }

    public void setBoolean(boolean b) {
        throw this.unsupported();
    }

    public void setUUID(@Nullable UUID uuid) {
        if (uuid != null) {
            this.c().add(uuid);
        }
    }

    @NotNull
    public SectionableDataSetter createSection(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        Document var2 = new Document();
        MongoDataProvider var3 = new MongoDataProvider(var1, var2);
        this.c().add(var2);
        return var3;
    }

    @NotNull
    public SectionableDataSetter createSection() {
        Document var1 = new Document();
        MongoDataProvider var2 = new MongoDataProvider(null, var1);
        this.c().add(var1);
        return var2;
    }

    @NotNull
    public Void setLocation(@Nullable OldLocation var1) {
        throw this.unsupported();
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation blockLocation) {
        if (blockLocation != null) {
            this.c().add(blockLocation);
        }
    }

    public void setSimpleChunkLocation(@NotNull SimpleChunkLocation chunkLocation) {
        Objects.requireNonNull(chunkLocation, "");
        this.c().add(chunkLocation);
    }

    public void setFloat(float f) {
        this.c().add(f);
    }

    public <V> void setCollection(@NotNull Collection<? extends V> c, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(c, "");
        Objects.requireNonNull(var2, "");
        List<Object> var3 = new ArrayList<>();
        UnknownDataGetter var4 = new UnknownDataGetter(var3);

        for (V var5 : c) {
            var2.accept(var4, var5);
        }

        this.c().add(var3);
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> m, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(m);
        Objects.requireNonNull(var2);
        Document var3 = new Document();
        (new MongoDataProvider(null, var3)).setMap(m, var2);
        this.c().add(var3);
    }

    @NotNull
    public SectionableDataGetter asSection() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public String asString(@NotNull Function0<String> var1) {
        Objects.requireNonNull(var1);
        if (this.value instanceof String) {
            return this.value.toString();
        } else {
            throw new IllegalStateException("Converting " + this.value + " (" + this.value.getClass().getSimpleName() + ") to string");
        }
    }

    @Override
    @NotNull
    public UUID asUUID() {
        Object value = this.value;
        UUID var10000 = value instanceof UUID ? (UUID) value : null;
        if (var10000 == null) {
            var10000 = FastUUID.fromString(this.value.toString());
            Objects.requireNonNull(var10000, "");
        }

        return var10000;
    }

    public @Nullable SimpleBlockLocation asSimpleLocation() {
        SimpleBlockLocation var10000 = SimpleBlockLocation.fromString(this.value.toString());
        Objects.requireNonNull(var10000);
        return var10000;
    }

    @NotNull
    public SimpleChunkLocation asSimpleChunkLocation() {
        SimpleChunkLocation var10000 = SimpleChunkLocation.fromString(this.value.toString());
        Objects.requireNonNull(var10000);
        return var10000;
    }

    @NotNull
    public OldLocation asLocation() {
        throw new KotlinNothingValueException();
    }

    public int asInt(@NotNull Supplier<Integer> def) {
        Objects.requireNonNull(def);
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000.intValue();
    }

    public long asLong(@NotNull Supplier<Long> def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000.longValue();
    }

    public float asFloat(@NotNull Supplier<Float> def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000.floatValue();
    }

    public double asDouble(@NotNull Supplier<Double> def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.get();
        }

        return var10000.doubleValue();
    }

    public boolean asBoolean(@NotNull Supplier<Boolean> def) {
        Objects.requireNonNull(def);
        return (Boolean) this.value;
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c);
        Objects.requireNonNull(dataProcessor);

        for (Object var4 : (Collection<?>) this.value) {
            Objects.requireNonNull(var4);
            dataProcessor.accept(c, MongoDataProvider.createProvider$core(var4));
        }

        return c;
    }

    private Number b() {
        Object value = this.value;
        Number var10000 = value instanceof Number ? (Number) value : null;
        if (var10000 == null) {
            String var2 = value instanceof String ? (String) value : null;
            var10000 = var2 != null ? StringsKt.toDoubleOrNull(var2) : null;
        }

        return var10000;
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(dataProcessor, "");
        throw this.unsupported();
    }

    private <T> List<T> c() {
        Object value = this.value;
        List var10000 = TypeIntrinsics.isMutableList(value) ? (List<?>) value : null;
        if (var10000 == null) {
            throw new IllegalStateException("Cannot add to " + this.value + " (" + this.value.getClass().getSimpleName() + ')');
        } else {
            return var10000;
        }
    }
}
