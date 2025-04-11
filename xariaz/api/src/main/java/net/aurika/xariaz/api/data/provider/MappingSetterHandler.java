package net.aurika.xariaz.api.data.provider;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface MappingSetterHandler<K, V> {

  void map(@NotNull DataSetter keyDataSetter, @NotNull K key, @NotNull DataSetter valueDataSetter, @NotNull V value);

}
