package net.aurika.xariaz.api.data.provider;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataSetter {

  void setBoolean(boolean value) throws IllegalDataStructException;

  void setInt(int value) throws IllegalDataStructException;

  void setLong(long value) throws IllegalDataStructException;

  void setDouble(double value) throws IllegalDataStructException;

  void setString(@NotNull String value) throws IllegalDataStructException;

  void setUUID(@NotNull UUID value) throws IllegalDataStructException;

  void setEnum(@NotNull Enum<?> value) throws IllegalDataStructException;

  <E> void setCollection(@NotNull Collection<E> value, BiConsumer<@NotNull DataSetter, E> elementHandler) throws IllegalDataStructException;

  <K, V> void setMap(@NotNull Map<K, V> value, @NotNull MappingSetterHandler<K, V> entryHandler) throws IllegalDataStructException;

  @NotNull SectionDataSetter asSection() throws DataNotSectionableException;

}
