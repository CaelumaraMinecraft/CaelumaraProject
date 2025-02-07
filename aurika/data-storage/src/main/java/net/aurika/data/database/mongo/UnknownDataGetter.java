package net.aurika.data.database.mongo;

import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import net.aurika.data.database.dataprovider.*;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.*;
import java.util.function.*;

public class UnknownDataGetter implements DataProvider {

    private final @NotNull Object value;

    public UnknownDataGetter(@NotNull Object value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    private UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Operation unsupported for value: " + this.value);
    }

    public @NotNull DataProvider get(@Nullable String key) {
        throw this.unsupported();
    }

    public void setString(@Nullable String value) {
        if (value != null) {
            this.c().add(value);
        }
    }

    public void setInt(int value) {
        this.c().add(value);
    }

    public void setLong(long value) {
        this.c().add(value);
    }

    public void setDouble(double value) {
        this.c().add(value);
    }

    public void setBoolean(boolean value) {
        throw this.unsupported();
    }

    public void setUUID(@Nullable UUID value) {
        if (value != null) {
            this.c().add(value);
        }
    }

    @NotNull
    public SectionableDataSetter createSection(@NotNull String key) {
        Objects.requireNonNull(key, "");
        Document var2 = new Document();
        MongoDataProvider var3 = new MongoDataProvider(key, var2);
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

    public void setLocation(@Nullable SimpleLocation value) {
        throw this.unsupported();
    }

    public void setSimpleLocation(@Nullable SimpleBlockLocation value) {
        if (value != null) this.c().add(value);
    }

    public void setSimpleChunkLocation(@Nullable SimpleChunkLocation value) {
        if (value != null) this.c().add(value);
    }

    public void setFloat(float value) {
        this.c().add(value);
    }

    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> var2) {
        Objects.requireNonNull(value, "");
        Objects.requireNonNull(var2, "");
        List<Object> var3 = new ArrayList<>();
        UnknownDataGetter var4 = new UnknownDataGetter(var3);

        for (V var5 : value) {
            var2.accept(var4, var5);
        }

        this.c().add(var3);
    }

    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> var2) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(var2);
        Document var3 = new Document();
        (new MongoDataProvider(null, var3)).setMap(value, var2);
        this.c().add(var3);
    }

    @NotNull
    public SectionableDataGetter asSection() {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public String asString(@NotNull Supplier<String> var1) {
        Objects.requireNonNull(var1);
        if (this.value instanceof String) {
            return this.value.toString();
        } else {
            throw new IllegalStateException("Converting " + this.value + " (" + this.value.getClass().getSimpleName() + ") to string");
        }
    }

    @Override
    public @Nullable UUID asUUID() {
        Object value = this.value;
        UUID var10000 = value instanceof UUID ? (UUID) value : null;
        if (var10000 == null) {
            var10000 = FastUUID.fromString(this.value.toString());
            Objects.requireNonNull(var10000, "");
        }

        return var10000;
    }

    public @Nullable SimpleBlockLocation asSimpleLocation() {
        return SimpleBlockLocation.fromDataString(this.value.toString());
    }

    @NotNull
    public SimpleChunkLocation asSimpleChunkLocation() {
        return SimpleChunkLocation.fromDataString(this.value.toString());
    }

    @NotNull
    public SimpleLocation asLocation() {
        return SimpleLocation.fromDataString(this.value.toString());
    }

    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def);
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.getAsInt();
        }

        return var10000.intValue();
    }

    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.getAsLong();
        }

        return var10000.longValue();
    }

    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.getAsFloat();
        }

        return var10000.floatValue();
    }

    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "");
        Number var10000 = this.b();
        if (var10000 == null) {
            var10000 = def.getAsDouble();
        }

        return var10000.doubleValue();
    }

    public boolean asBoolean(@NotNull BooleanSupplier def) {
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
