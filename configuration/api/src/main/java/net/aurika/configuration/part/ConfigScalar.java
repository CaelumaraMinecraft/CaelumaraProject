package net.aurika.configuration.part;

import org.jetbrains.annotations.NotNull;

public interface ConfigScalar extends ConfigPart {

  /**
   * Gets the scalar value.
   *
   * @return the salar value
   */
  @NotNull String scalarValue();

  /**
   * Sets the scalar value.
   *
   * @param scalarValue the scalar value
   */
  void scalarValue(@NotNull String scalarValue);

}
