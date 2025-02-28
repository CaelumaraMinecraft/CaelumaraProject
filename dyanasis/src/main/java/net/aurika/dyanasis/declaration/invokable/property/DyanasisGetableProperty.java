package net.aurika.dyanasis.declaration.invokable.property;

import net.aurika.dyanasis.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisGetableProperty extends DyanasisProperty {
    /**
     * Gets the dyanasis property value.
     *
     * @return the property value
     */
    @NotNull DyanasisObject get();
}
