package net.aurika.dyanasis.declaration.invokable.property;

import net.aurika.dyanasis.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisSetableProperty extends DyanasisProperty {
    /**
     * Set the dyanasis property value.
     *
     * @param value the property value
     */
    void set(@NotNull DyanasisObject value);
}
