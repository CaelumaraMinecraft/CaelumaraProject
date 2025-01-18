package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.server.location.Location;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.*;

public interface DataGetter {

    default @Nullable String asString() {
        return this.asString(() -> null);
    }

    default int asInt() {
        return this.asInt(() -> 0);
    }

    default long asLong() {
        return this.asLong(() -> 0L);
    }

    default float asFloat() {
        return this.asFloat(() -> 0.0F);
    }

    default double asDouble() {
        return this.asDouble(() -> 0.0);
    }

    default boolean asBoolean() {
        return this.asBoolean(() -> false);
    }

    @Nullable String asString(@NotNull Supplier<String> def);

    @Nullable UUID asUUID() throws SQLException;

    @Nullable SimpleBlockLocation asSimpleLocation();

    @NotNull SimpleChunkLocation asSimpleChunkLocation();

    @Nullable Location asLocation();

    int asInt(@NotNull IntSupplier def);

    long asLong(@NotNull LongSupplier def);

    float asFloat(@NotNull FloatSupplier def);

    double asDouble(@NotNull DoubleSupplier def);

    boolean asBoolean(@NotNull BooleanSupplier def);

    @NotNull <V, C extends Collection<V>> C asCollection(@NotNull C collection, @NotNull BiConsumer<C, SectionableDataGetter> collectionOperator) throws SQLException;

    @NotNull <K, V, M extends Map<K, V>> M asMap(@NotNull M map, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> mapOperator) throws SQLException;
}
