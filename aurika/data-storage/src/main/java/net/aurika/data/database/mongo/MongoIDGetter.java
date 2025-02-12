package net.aurika.data.database.mongo;

import net.aurika.data.database.dataprovider.DataGetter;
import net.aurika.data.database.dataprovider.SectionableDataGetter;
import net.aurika.data.api.bundles.DataBundleSchema;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import net.aurika.util.uuid.FastUUID;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public class MongoIDGetter implements DataGetter {

    private final @NotNull String value;

    public MongoIDGetter(@NotNull String value) {
        Objects.requireNonNull(value, "value");
        this.value = value;
    }

    @Override
    public @NotNull String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "def");
        return this.value;
    }

    @Override
    public @NotNull UUID asUUID() {
        return FastUUID.fromString(this.value);
    }

    @Override
    public <T> T asObject(DataBundleSchema<T> template) {
        throw new UnsupportedOperationException("Not implemented yet");  // TODO
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        return Integer.parseInt(value);
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        return Long.parseLong(value);
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        return Float.parseFloat(value);
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        return Double.parseDouble(value);
    }

    @Override
    public boolean asBoolean(@NotNull BooleanSupplier def) {
        Objects.requireNonNull(def, "def");
        throw new UnsupportedOperationException();
    }

    @Override
    public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(c, "");
        Objects.requireNonNull(dataProcessor, "");
        throw new UnsupportedOperationException();
    }

    @Override
    public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> dataProcessor) {
        Objects.requireNonNull(m, "");
        Objects.requireNonNull(dataProcessor, "");
        throw new UnsupportedOperationException();
    }
}
