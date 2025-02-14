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

    default @Nullable String asString() {
        return this.asString(() -> null);
    }

    /**
     * 读取 {@code int} 值
     *
     * @param def 在当前 {@linkplain DataGetter} 实际在数据库中不存在时被调用
     */
    int asInt(@NotNull IntSupplier def);

    long asLong(@NotNull LongSupplier def);

    float asFloat(@NotNull FloatSupplier def);

    double asDouble(@NotNull DoubleSupplier def);

    boolean asBoolean(@NotNull BooleanSupplier def);

    String asString(@NotNull Supplier<String> def);

    @Nullable UUID asUUID();

    <T extends StructuredDataObject> @Nullable T asStruct(@NotNull DataStructSchema<T> template);

    /**
     * 将当前 {@linkplain DataGetter} 作为 Collection 读取.
     *
     * @param c       存储数据的集合
     * @param handler 数据处理器, 提供给数据处理器的第一个参数为 {@link C}, 第二个参数为集合元素数据的 {@linkplain SectionableDataGetter}.
     * @throws RuntimeException 当实际存储的数据不为 Collection 结构时
     */
    @Contract("_, _ -> param1")
    <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler);

    /**
     * 将当前 {@linkplain DataGetter} 作为 Map 读取.
     *
     * @param m       存储数据的映射
     * @param handler 数据处理器, 提供给数据处理器的第一个参数为 {@link M}, 第二个参数为键数据的 {@linkplain DataGetter}, 第三个参数为值数据的 {@linkplain SectionableDataGetter}.
     * @throws RuntimeException 当实际存储的数据不为 Map 结构时
     */
    @Contract("_, _ -> param1")
    <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler);
}
