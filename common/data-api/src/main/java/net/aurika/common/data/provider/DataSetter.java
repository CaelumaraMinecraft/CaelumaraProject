package net.aurika.common.data.provider;

import net.aurika.common.validate.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

public interface DataSetter {

  /**
   * Sets the boolean data.
   *
   * @param value the boolean data
   * @throws IllegalDataStructException when the struct of data setter cannot set a boolean data
   */
  void setBoolean(boolean value) throws IllegalDataStructException;

  void setChar(char value) throws IllegalDataStructException;

  void setByte(byte value) throws IllegalDataStructException;

  void setShort(short value) throws IllegalDataStructException;

  void setInt(int value) throws IllegalDataStructException;

  void setLong(long value) throws IllegalDataStructException;

  void setFloat(float value) throws IllegalDataStructException;

  void setDouble(double value) throws IllegalDataStructException;

  void setString(@NotNull String value) throws IllegalDataStructException;

  void setEnum(@NotNull Enum<?> value) throws IllegalDataStructException;

  <E> void setCollection(
      @NotNull Collection<E> value,
      BiConsumer<
          @NotNull SectionCreatableDataSetter, // collection
          E  // element
          > elementHandler
  ) throws IllegalDataStructException;

  <K, V> void setMap(@NotNull Map<K, V> value, MappingSetterHandler<K, V> entryHandler) throws IllegalDataStructException;

}
