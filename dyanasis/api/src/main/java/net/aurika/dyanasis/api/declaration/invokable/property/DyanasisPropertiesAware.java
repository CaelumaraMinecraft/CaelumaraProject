package net.aurika.dyanasis.api.declaration.invokable.property;

import net.aurika.dyanasis.api.declaration.invokable.property.container.DyanasisPropertyContainer;
import org.jetbrains.annotations.NotNull;

/**
 * The implementation of {@linkplain DyanasisPropertiesAware} can provide {@linkplain DyanasisPropertyContainer}.
 */
public interface DyanasisPropertiesAware {

  /**
   * Gets the available dyanasis properties.
   *
   * @return the properties
   */
  @NotNull DyanasisPropertyContainer<?> dyanasisProperties();

}
