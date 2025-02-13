package net.aurika.ecliptor.database.mongo;

import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.database.dataprovider.*;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import net.aurika.util.uuid.FastUUID;
import net.aurika.validate.Validate;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;

public class UnknownDataGetter implements DataProvider {

    private final @NotNull Object value;

    public UnknownDataGetter(@NotNull Object value) {
        Objects.requireNonNull(value, "value");
        this.value = value;
    }

    private @NotNull UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Operation unsupported for value: " + value);
    }

    @Override
    public @NotNull DataProvider get(@Nullable String key) {
        throw unsupported();
    }

    @Override
    public void setInt(int value) {
        this.c().add(value);
    }

    @Override
    public void setLong(long value) {
        this.c().add(value);
    }

    @Override
    public void setFloat(float value) {
        this.c().add(value);
    }

    @Override
    public void setDouble(double value) {
        this.c().add(value);
    }

    @Override
    public void setBoolean(boolean value) {
        throw this.unsupported();
    }

    @Override
    public void setString(@Nullable String value) {
        if (value != null) {
            this.c().add(value);
        }
    }

    @Override
    public void setUUID(@Nullable UUID value) {
        if (value != null) {
            this.c().add(value);
        }
    }

    @Override
    public void setStruct(@Nullable StructuredDataObject value) {
        if (value != null) this.c().add(value);
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection(@NotNull String key) {
        Objects.requireNonNull(key, "key");
        Document var2 = new Document();
        MongoDataProvider var3 = new MongoDataProvider(key, var2);
        this.c().add(var2);
        return var3;
    }

    @Override
    @NotNull
    public SectionableDataSetter createSection() {
        Document var1 = new Document();
        MongoDataProvider var2 = new MongoDataProvider(null, var1);
        this.c().add(var1);
        return var2;
    }

    @Override
    public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "handler");
        List<Object> var3 = new ArrayList<>();
        UnknownDataGetter var4 = new UnknownDataGetter(var3);

        for (V var5 : value) {
            handler.accept(var4, var5);
        }

        this.c().add(var3);
    }

    @Override
    public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
        Objects.requireNonNull(value, "value");
        Objects.requireNonNull(handler, "handler");
        Document var3 = new Document();
        (new MongoDataProvider(null, var3)).setMap(value, handler);
        this.c().add(var3);
    }

    @Override
    public @NotNull SectionableDataGetter asSection() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @NotNull String asString(@NotNull Supplier<String> var1) {
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

    @Override
    public <T extends StructuredDataObject> @Nullable T asStruct(@NotNull DataStructSchema<T> template) {
        Validate.Arg.notNull(template, "template");
        return null;  // TODO
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        Number n = this.__num_value();
        return n != null ? n.intValue() : def.getAsInt();
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "def");
        Number n = this.__num_value();
        return n != null ? n.longValue() : def.getAsLong();
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "def");
        Number n = this.__num_value();
        return n != null ? n.floatValue() : def.getAsFloat();
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        Number n = this.__num_value();
        return n != null ? n.doubleValue() : def.getAsDouble();
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        return (Boolean) this.value;
    }

    @Override
    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
        Objects.requireNonNull(c, "c");
        Objects.requireNonNull(handler, "handler");

        for (Object var4 : (Collection<?>) this.value) {
            Objects.requireNonNull(var4);
            handler.accept(c, MongoDataProvider.createProvider$core(var4));
        }

        return c;
    }

    private @Nullable Number __num_value() {
        Object value = this.value;
        if (value instanceof Number number) {
            return number;
        }
        if (value instanceof String string) {
            try {
                return Double.valueOf(string);
            } catch (NumberFormatException ex) {
                return null;
            }
        }
        return null;
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
        throw this.unsupported();
    }

    private <T> @NotNull List<T> c() {
        Object value = this.value;
        List<T> list = value instanceof List ? (List<T>) value : null;
        if (list == null) {
            throw new IllegalStateException("Cannot add to " + this.value + " (" + this.value.getClass().getSimpleName() + ')');
        } else {
            return list;
        }
    }
}
