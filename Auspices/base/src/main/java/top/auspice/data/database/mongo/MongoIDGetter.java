package top.auspice.data.database.mongo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.data.database.dataprovider.DataGetter;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.unsafe.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public final class MongoIDGetter implements DataGetter {

    private final @NotNull String a;

    public MongoIDGetter(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        this.a = var1;
    }

    @Override
    public @NotNull String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        return this.a;
    }

    @Override
    public @NotNull UUID asUUID() {
        return FastUUID.fromString(this.a);
    }

    @Override
    public @Nullable SimpleBlockLocation asSimpleLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public @Nullable SimpleLocation asLocation() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int asInt(@NotNull IntSupplier def) {
        Objects.requireNonNull(def, "def");
        return Integer.parseInt(this.a);
    }

    @Override
    public long asLong(@NotNull LongSupplier def) {
        Objects.requireNonNull(def, "def");
        return Long.parseLong(this.a);
    }

    @Override
    public float asFloat(@NotNull FloatSupplier def) {
        Objects.requireNonNull(def, "def");
        return Float.parseFloat(this.a);
    }

    @Override
    public double asDouble(@NotNull DoubleSupplier def) {
        Objects.requireNonNull(def, "def");
        return Double.parseDouble(this.a);
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
