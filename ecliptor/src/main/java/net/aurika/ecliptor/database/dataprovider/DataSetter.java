package net.aurika.ecliptor.database.dataprovider;

import net.aurika.ecliptor.api.DataStringRepresentation;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataSetter {

  default void setString(@NotNull DataStringRepresentation value) {
    Objects.requireNonNull(value, "value");
    this.setString(value.asDataString());
  }

  void setInt(int value);

  void setLong(long value);

  void setFloat(float value);

  void setDouble(double value);

  void setBoolean(boolean value);

  void setString(@NotNull String value);

  void setUUID(@NotNull UUID value);

  void setStruct(@NotNull StructuredDataObject value);

  <E> void setCollection(@NotNull Collection<? extends E> value, @NotNull BiConsumer<SectionCreatableDataSetter, E> handler);

  <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler);

}
