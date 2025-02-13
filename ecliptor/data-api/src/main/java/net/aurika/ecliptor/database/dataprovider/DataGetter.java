package net.aurika.ecliptor.database.dataprovider;

import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.util.function.FloatSupplier;
import net.aurika.util.function.TriConsumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    int asInt(@NotNull IntSupplier def);

    long asLong(@NotNull LongSupplier def);

    float asFloat(@NotNull FloatSupplier def);

    double asDouble(@NotNull DoubleSupplier def);

    boolean asBoolean(@NotNull BooleanSupplier def);

    String asString(@NotNull Supplier<String> def);

    @Nullable UUID asUUID();

    <T extends StructuredDataObject> @Nullable T asStruct(@NotNull DataStructSchema<T> template);

    /**
     * @param c       存储数据的集合
     * @param handler 数据处理器, 提供给数据处理器的第一个参数为 {@link C}, 第二个参数为集合元素数据的 Getter.
     * @param <E>     The element type of collection
     * @param <C>     The type of collection
     * @return 处理完成的集合
     */
    @Contract("_, _ -> param1")
    <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler);

    /**
     * @param m       存储数据的映射
     * @param handler 数据处理器, 提供给数据处理器的第一个参数为 {@link M}, 第二个参数为键数据的 Getter, 第三个参数为值数据的 Getter.
     * @param <K>     the key type of map.
     * @param <V>     The value type of map.
     * @param <M>     The map type.
     * @return 处理完成的映射
     */
    @Contract("_, _ -> param1")
    <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler);
}
