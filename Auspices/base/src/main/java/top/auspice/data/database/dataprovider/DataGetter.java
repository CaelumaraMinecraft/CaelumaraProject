package top.auspice.data.database.dataprovider;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.constants.location.SimpleBlockLocation;
import top.auspice.constants.location.SimpleChunkLocation;
import top.auspice.constants.location.SimpleLocation;
import top.auspice.utils.function.FloatSupplier;
import top.auspice.utils.function.TriConsumer;

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

    @Nullable UUID asUUID();

    @Nullable SimpleBlockLocation asSimpleLocation();

    @Nullable SimpleChunkLocation asSimpleChunkLocation();

    @Nullable SimpleLocation asLocation();

    int asInt(@NotNull IntSupplier def);

    long asLong(@NotNull LongSupplier def);

    float asFloat(@NotNull FloatSupplier def);

    double asDouble(@NotNull DoubleSupplier def);

    boolean asBoolean(@NotNull BooleanSupplier def);

    @Contract("_, _ -> param1")
    <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C collection, @NotNull BiConsumer<C, SectionableDataGetter> collectionOperator);

    @Contract("_, _ -> param1")
    <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M map, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> mapOperator);
}
