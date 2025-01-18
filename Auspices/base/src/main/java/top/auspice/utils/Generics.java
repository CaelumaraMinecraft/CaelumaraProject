package top.auspice.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.utils.internal.Fn;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public final class Generics {

    public static <K, V, M extends Map<K, V>> @Nullable M filterKVType(Map<?, ?> map, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        Checker.Argument.checkNotNull(keyType, "keyType");
        Checker.Argument.checkNotNull(valueType, "valueType");

        if (map == null) {
            return null;
        }

        boolean allType = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if ((!keyType.isInstance(entry.getKey())) || (!valueType.isInstance(entry.getValue()))) {
                allType = false;
                break;
            }
        }

        if (allType) {
            return Fn.cast(map);
        }
        return null;
    }

    public static <K, V, M extends Map<K, V>> @Nullable M filterKeyType(Map<?, V> map, @NotNull Class<K> keyType) {
        Checker.Argument.checkNotNull(keyType, "keyType");

        if (map == null) {
            return null;
        }

        boolean allType = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!keyType.isInstance(entry.getKey())) {
                allType = false;
                break;
            }
        }

        if (allType) {
            return Fn.cast(map);
        }
        return null;
    }

    public static <K, V, M extends Map<K, V>> @Nullable M filterValueType(Map<K, ?> map, @NotNull Class<K> valueType) {
        Checker.Argument.checkNotNull(valueType, "valueType");

        if (map == null) {
            return null;
        }

        boolean allType = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!valueType.isInstance(entry.getValue())) {
                allType = false;
                break;
            }
        }

        if (allType) {
            return Fn.cast(map);
        }
        return null;
    }

    @Contract("_, _ -> param1")
    public static <E, C extends Collection<E>> @Nullable C filterElementType(Collection<?> collection, @NotNull Class<E> elementType) {
        Checker.Argument.checkNotNull(elementType, "elementType");

        if (collection == null) {
            return null;
        }

        boolean allType = true;
        for (Object o : collection) {
            if (!elementType.isInstance(o)) {
                allType = false;
                break;
            }
        }

        if (allType) {
            return Fn.cast(collection);
        }
        return null;
    }

    @Contract("_, _, _, _ -> param2")
    public static <OldType, NewType, C extends Collection<NewType>> @NotNull C migrationType(@NotNull Collection<OldType> oldTypeCollection,
                                                                                             @NotNull C newTypeCollection,
                                                                                             @NotNull Class<NewType> newType,
                                                                                             @NotNull Function<OldType, NewType> toNewType
    ) {
        Checker.Argument.checkNotNull(oldTypeCollection, "oldTypeCollection");
        Checker.Argument.checkNotNull(newTypeCollection, "newTypeCollection");
        Checker.Argument.checkNotNull(newType, "newType");
        Checker.Argument.checkNotNull(toNewType, "toNewType");

        for (OldType oldTypeObj : oldTypeCollection) {
            if (newType.isInstance(oldTypeObj)) {
                newTypeCollection.add(Fn.cast(oldTypeObj));
                continue;
            }
            newTypeCollection.add(toNewType.apply(oldTypeObj));
        }

        return newTypeCollection;
    }
}
