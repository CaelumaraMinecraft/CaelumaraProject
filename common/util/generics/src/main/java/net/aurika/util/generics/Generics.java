package net.aurika.util.generics;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public final class Generics {

    /**
     * @param srcMap    the source map
     * @param destMap   the destination map
     * @param keyType   the key type
     * @param valueType the value map
     * @param <K>       the key type
     * @param <V>       the value type
     * @param <M>       the destination map type
     * @return the destination map
     */
    @Contract("_, _, _, _ -> param2")
    public static <K, V, M extends Map<K, V>> @NotNull M pickKVType(@NotNull Map<?, ?> srcMap, @NotNull M destMap, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        Validate.Arg.notNull(srcMap, "srcMap");
        Validate.Arg.notNull(destMap, "destMap");
        Validate.Arg.notNull(keyType, "keyType");
        Validate.Arg.notNull(valueType, "valueType");

        for (Map.Entry<?, ?> entry : srcMap.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            if (keyType.isInstance(key) && valueType.isInstance(value)) {
                destMap.put((K) key, (V) value);
            }
        }

        return destMap;
    }

    /**
     * @param srcMap  the source map
     * @param destMap the destination map
     * @param keyType the key type
     * @param <K>     the key type
     * @param <V>     the value type
     * @param <M>     the destination map type
     * @return the destination map
     */
    @Contract("_, _, _ -> param2")
    public static <K, V, M extends Map<K, V>> @NotNull M pickKeyType(@NotNull Map<?, V> srcMap, @NotNull M destMap, @NotNull Class<K> keyType) {
        Validate.Arg.notNull(srcMap, "srcMap");
        Validate.Arg.notNull(destMap, "destMap");
        Validate.Arg.notNull(keyType, "keyType");

        for (Map.Entry<?, V> entry : srcMap.entrySet()) {
            Object key = entry.getKey();
            V value = entry.getValue();
            if (keyType.isInstance(key)) {
                destMap.put((K) key, value);
            }
        }

        return destMap;
    }

    /**
     * @param srcMap    the source map
     * @param destMap   the destination map
     * @param valueType the value map
     * @param <K>       the key type
     * @param <V>       the value type
     * @param <M>       the destination map type
     * @return the destination map
     */
    @Contract("_, _, _ -> param2")
    public static <K, V, M extends Map<K, V>> @NotNull M pickValueType(@NotNull Map<K, ?> srcMap, @NotNull M destMap, @NotNull Class<V> valueType) {
        Validate.Arg.notNull(srcMap, "srcMap");
        Validate.Arg.notNull(destMap, "destMap");
        Validate.Arg.notNull(valueType, "valueType");

        for (Map.Entry<K, ?> entry : srcMap.entrySet()) {
            K key = entry.getKey();
            Object value = entry.getValue();
            if (valueType.isInstance(value)) {
                destMap.put(key, (V) value);
            }
        }

        return destMap;
    }

    public static <K, V, M extends Map<K, V>> @Nullable M filterKVType(Map<?, ?> map, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        Validate.Arg.notNull(keyType, "keyType");
        Validate.Arg.notNull(valueType, "valueType");

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
            return (M) map;
        }
        return null;
    }

    public static <K, V, M extends Map<K, V>> @Nullable M filterKeyType(Map<?, V> map, @NotNull Class<K> keyType) {
        Validate.Arg.notNull(keyType, "keyType");

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
            return (M) map;
        }
        return null;
    }

    public static <K, V, M extends Map<K, V>> @Nullable M filterValueType(Map<K, ?> map, @NotNull Class<K> valueType) {
        Validate.Arg.notNull(valueType, "valueType");

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
            return (M) map;
        }
        return null;
    }

    @Contract("_, _ -> param1")
    public static <E, C extends Collection<E>> @Nullable C filterElementType(Collection<?> collection, @NotNull Class<E> elementType) {
        Validate.Arg.notNull(elementType, "elementType");

        if (collection == null) {
            return null;
        }

        boolean allType = true;
        for (Object e : collection) {
            if (!elementType.isInstance(e)) {
                allType = false;
                break;
            }
        }

        if (allType) {
            return (C) collection;
        }
        return null;
    }

    @Contract("_, _, _, _ -> param2")
    public static <OldType, NewType, C extends Collection<NewType>> @NotNull C migrationType(@NotNull Collection<OldType> oldTypeCollection,
                                                                                             @NotNull C newTypeCollection,
                                                                                             @NotNull Class<NewType> newType,
                                                                                             @NotNull Function<OldType, NewType> toNewType
    ) {
        Validate.Arg.notNull(oldTypeCollection, "oldTypeCollection");
        Validate.Arg.notNull(newTypeCollection, "newTypeCollection");
        Validate.Arg.notNull(newType, "newType");
        Validate.Arg.notNull(toNewType, "toNewType");

        for (OldType oldTypeObj : oldTypeCollection) {
            newTypeCollection.add(toNewType.apply(oldTypeObj));
        }

        return newTypeCollection;
    }
}
