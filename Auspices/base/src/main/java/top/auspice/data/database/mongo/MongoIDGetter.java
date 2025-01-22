package top.auspice.data.database.mongo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.data.database.dataprovider.DataGetter;
import top.auspice.data.database.dataprovider.SectionableDataGetter;
import top.auspice.server.location.Location;
import top.auspice.utils.function.TriConsumer;
import top.auspice.utils.internal.uuid.FastUUID;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public final class MongoIDGetter implements DataGetter {
    @NotNull
    private final String a;

    public MongoIDGetter(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        this.a = var1;
    }

    @NotNull
    public String asString(@NotNull Supplier<String> def) {
        Objects.requireNonNull(def, "");
        return this.a;
    }

    @Override
    @Nullable
    public UUID asUUID() {
        return FastUUID.fromString(this.a);
    }

    public @Nullable SimpleBlockLocation asSimpleLocation() {
        throw new UnsupportedOperationException();
    }

    public @Nullable SimpleChunkLocation asSimpleChunkLocation() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Location asLocation() {
        throw new UnsupportedOperationException();
    }

    public int asInt(@NotNull Supplier<Integer> def) {
        Objects.requireNonNull(def, "");
        return Integer.parseInt(this.a);
    }

    public long asLong(@NotNull Supplier<Long> def) {
        Objects.requireNonNull(def, "");
        return Long.parseLong(this.a);
    }

    public float asFloat(@NotNull Supplier<Float> def) {
        Objects.requireNonNull(def, "");
        return Float.parseFloat(this.a);
    }

    public double asDouble(@NotNull Supplier<Double> def) {
        Objects.requireNonNull(def, "");
        return Double.parseDouble(this.a);
    }

    public boolean asBoolean(@NotNull Supplier<Boolean> def) {
        Objects.requireNonNull(def, "");
        throw new UnsupportedOperationException();
    }

    @NotNull
    public <V, C extends Collection<V>> C asCollection(@NotNull C collection, @NotNull BiConsumer<C, SectionableDataGetter> collectionOperator) {
        Objects.requireNonNull(collection, "");
        Objects.requireNonNull(collectionOperator, "");
        throw new UnsupportedOperationException();
    }

    @NotNull
    public <K, V, M extends Map<K, V>> M asMap(@NotNull M map, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> mapOperator) {
        Objects.requireNonNull(map, "");
        Objects.requireNonNull(mapOperator, "");
        throw new UnsupportedOperationException();
    }
}
