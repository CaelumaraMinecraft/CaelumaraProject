package net.aurika.config.part;

import net.aurika.config.path.ConfigEntry;
import org.jetbrains.annotations.NotNull;

public interface ConfigScalar extends ConfigPart {

  @Override
  boolean hasAbsolutePath();

  @Override
  @NotNull ConfigEntry absolutePath();

  @Override
  boolean isNamed();

  @Override
  @NotNull String name();

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
