package net.aurika.common.data.provider;

import net.aurika.common.data.util.TriConsumer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Provides data gettable. When the current data struct cannot be cast to necessary type, An {@link IllegalDataStructException} will be thrown.
 */
public interface DataGetter {

  /**
   * Gets the boolean data.
   *
   * @return the boolean data
   * @throws IllegalDataStructException when the data getter cannot cast to a boolean value
   */
  boolean asBoolean() throws IllegalDataStructException;

  char asChar() throws IllegalDataStructException;

  byte asByte() throws IllegalDataStructException;

  short asShort() throws IllegalDataStructException;

  int asInt() throws IllegalDataStructException;

  long asLong() throws IllegalDataStructException;

  float asFloat() throws IllegalDataStructException;

  double asDouble() throws IllegalDataStructException;

  @NotNull String asString() throws IllegalDataStructException;

  @NotNull <E extends Enum<E>> E asEnum(@NotNull Class<E> enumType) throws IllegalDataStructException;

  <E, C extends Collection<E>> @NotNull C asCollection(
      @NotNull C collection,
      BiConsumer<
          @NotNull C, // collection
          @NotNull SectionableDataGetter // elementData
          > elementHandler
  ) throws IllegalDataStructException;

  <K, V, M extends Map<K, V>> @NotNull M asMap(
      @NotNull M map,
      TriConsumer<
          @NotNull M, // map
          @NotNull DataGetter, // keyData
          @NotNull SectionableDataGetter // valueData
          > entryHandler
  ) throws IllegalDataStructException;

}
