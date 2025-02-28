package net.aurika.dyanasis.declaration.invokable.function;

import net.aurika.dyanasis.declaration.invokable.function.container.DyanasisFunctions;
import org.jetbrains.annotations.NotNull;

/**
 * Provider of {@linkplain DyanasisFunctions}.
 */
public interface DyanasisFunctionsProvider {
    /**
     * Gets the dyanasis functions.
     *
     * @return the functions
     */
    @NotNull DyanasisFunctions dyanasisFunctions();
}
