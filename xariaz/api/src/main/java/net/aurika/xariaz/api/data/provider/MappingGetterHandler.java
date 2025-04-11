package net.aurika.xariaz.api.data.provider;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

@FunctionalInterface
public interface MappingGetterHandler<M extends Map<?, ?>> {

  void map(@NotNull M map, @NotNull DataGetter keyData, DataGetter valueData);

}
