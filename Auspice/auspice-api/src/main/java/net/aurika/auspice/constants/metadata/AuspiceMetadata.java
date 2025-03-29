package net.aurika.auspice.constants.metadata;

import net.aurika.auspice.constants.base.KeyedAuspiceObject;
import net.aurika.common.annotations.Getter;
import net.aurika.common.annotations.Setter;
import net.aurika.ecliptor.database.dataprovider.SectionCreatableDataSetter;
import org.jetbrains.annotations.NotNull;

public interface AuspiceMetadata {

  /**
   * Gets the value.
   *
   * @return Value of this metadata
   */
  @Getter
  Object value();

  /**
   * Sets the value.
   *
   * @param value New value of this metadata
   */
  @Setter
  void value(Object value);

  void serialize(@NotNull KeyedAuspiceObject<?> auspiceObject, @NotNull SectionCreatableDataSetter dataSetter);

  default boolean shouldSave(@NotNull KeyedAuspiceObject<?> auspiceObject) {
    return true;
  }

}
