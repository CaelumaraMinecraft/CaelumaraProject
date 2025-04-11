package net.aurika.dyanasis.api.declaration.property;

import net.aurika.dyanasis.api.declaration.member.property.container.DyanasisPropertyContainer;
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
