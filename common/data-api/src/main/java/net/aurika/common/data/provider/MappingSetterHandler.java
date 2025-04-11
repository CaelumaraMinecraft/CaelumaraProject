package net.aurika.common.data.provider;

import org.jetbrains.annotations.NotNull;

public interface MappingSetterHandler<K, V> {

  void map(@NotNull K key, @NotNull MappedIdSetter keyProvider, @NotNull V value);

}
