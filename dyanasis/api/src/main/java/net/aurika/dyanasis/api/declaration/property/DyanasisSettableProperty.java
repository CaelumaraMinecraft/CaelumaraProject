package net.aurika.dyanasis.api.declaration.property;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisSettableProperty extends DyanasisProperty {

  /**
   * Set the dyanasis property value.
   *
   * @param value the property value
   */
  void setPropertyValue(@NotNull DyanasisObject value);

}
