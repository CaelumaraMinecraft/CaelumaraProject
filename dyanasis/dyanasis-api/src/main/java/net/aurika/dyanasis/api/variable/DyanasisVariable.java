package net.aurika.dyanasis.api.variable;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisVariable {
    /**
     * Gets the value of this dyanasis variable.
     *
     * @return the value
     */
    @NotNull DyanasisObject value();
}
