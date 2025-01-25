package net.aurika.data.database.dataprovider;

import org.jetbrains.annotations.NotNull;

public interface MappingSetterHandler<K, V> {
    void map(K k, @NotNull MappedIdSetter var2, V v);
}
