package net.aurika.dyanasis.api.invoking.result;

import net.aurika.dyanasis.api.exception.DyanasisFunctionException;
import org.jetbrains.annotations.NotNull;

public interface DyanasisFunctionResultFailed extends DyanasisFunctionResult {
    /**
     * Gets the exception to the failed function result.
     *
     * @return the exception
     */
    @NotNull DyanasisFunctionException exception();
}
