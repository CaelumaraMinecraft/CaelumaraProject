package net.aurika.dyanasis.api.declaration.property;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisGettableProperty extends DyanasisProperty {

  /**
   * Gets the dyanasis property value.
   *
   * @return the property value
   */
  @NotNull DyanasisObject getPropertyValue();

}
