package top.auspice.utils.enumeration;

import com.google.common.base.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import net.aurika.util.collection.nonnull.NonNullMap;

import java.util.Map;
import java.util.function.Function;

public final class Enums {
    public static <T extends Enum<T>> Map<String, T> createMapping(T[] values) {
        return createMapping(values, Enum::name);
    }

    public static <K, T extends Enum<T>> Map<K, T> createMapping(T[] values, Function<T, K> of) {
        return createMapping(values, of, new NonNullMap<>(values.length));
    }

    @Contract("_, _, _ -> param3")
    public static <K, T extends Enum<T>> Map<K, T> createMapping(T @NotNull [] values, Function<T, K> of, Map<K, T> mappings) {
        for (T value : values) {
            mappings.put(of.apply(value), value);
        }
        return mappings;
    }

    @Contract("_, _, _ -> param3")
    public static <K, T extends Enum<T>> Map<K, T> createMultiMapping(T @NotNull [] values, Function<T, Iterable<K>> of, Map<K, T> mappings) {
        for (T value : values) {
            Iterable<K> keys = of.apply(value);
            for (K key : keys) {
                mappings.put(key, value);
            }
        }
        return mappings;
    }

    public static <T extends Enum<T>> T findOneOf(Class<T> enumClass, String @NotNull ... values) {
        Optional<T> optional = Optional.absent();
        for (String value : values) {
            optional = optional.or(com.google.common.base.Enums.getIfPresent(enumClass, value));
        }
        return optional.orNull();
    }
}
