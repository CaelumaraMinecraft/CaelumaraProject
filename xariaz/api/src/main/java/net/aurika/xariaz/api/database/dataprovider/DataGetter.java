package net.aurika.xariaz.api.database.dataprovider;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public interface DataGetter {

  boolean getBoolean() throws IllegalDataStructException;

  default char getChar() throws IllegalDataStructException {
    String s = getString();
    if (s.isEmpty()) return '\0';
    if (s.length() == 1) return s.charAt(0);
    throw new IllegalDataStructException("Stored string '" + s + "' contains multiple characters");
  }

  default byte getByte() throws IllegalDataStructException {
    return getNumber().byteValue();
  }

  default short getShort() throws IllegalDataStructException {
    return getNumber().shortValue();
  }

  default int getInt() throws IllegalDataStructException {
    return getNumber().intValue();
  }

  default long getLong() throws IllegalDataStructException {
    return getNumber().longValue();
  }

  default float getFloat() throws IllegalDataStructException {
    return getNumber().floatValue();
  }

  default double getDouble() throws IllegalDataStructException {
    return getNumber().doubleValue();
  }

  @NotNull Number getNumber() throws IllegalDataStructException;

  @NotNull String getString() throws IllegalDataStructException;

  default @NotNull UUID getUUID() throws IllegalDataStructException {
    return UUID.fromString(getString());
  }

  default <E extends Enum<E>> @NotNull E getEnum(@NotNull Class<E> enumType) throws IllegalDataStructException {
    Validate.Arg.notNull(enumType, "enumType");
    String name = getString();
    for (E e : enumType.getEnumConstants()) {
      if (e.name().equals(name)) return e;
    }
    throw new IllegalDataStructException("Stored string '" + name + "' cannot find in enum '" + enumType + "'");
  }

  <E, C extends Collection<E>> @NotNull C getCollection(
      @NotNull C collection,
      BiConsumer<@NotNull C, DataGetter> elementHandler
  ) throws IllegalDataStructException;

  <K, V, M extends Map<K, V>> @NotNull M getMap(
      @NotNull M map,
      @NotNull MappingGetterHandler<M> entryHandler
  ) throws IllegalDataStructException;

  @NotNull SectionDataGetter asSection() throws DataNotSectionableException;

}
