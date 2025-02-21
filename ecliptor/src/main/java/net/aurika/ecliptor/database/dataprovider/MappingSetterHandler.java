package net.aurika.ecliptor.database.dataprovider;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MappingSetterHandler<K, V> {
    void map(K key, @NotNull MappedIdSetter idSetter, V value);
}
