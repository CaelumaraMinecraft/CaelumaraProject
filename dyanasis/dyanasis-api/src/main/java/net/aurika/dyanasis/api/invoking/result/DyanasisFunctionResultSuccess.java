package net.aurika.dyanasis.api.invoking.result;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisFunctionResultSuccess extends DyanasisFunctionResult {
    /**
     * Gets the returned value of the dyanasis function.
     *
     * @return the returned value
     */
    @NotNull DyanasisObject returned();
}
