package net.aurika.dyanasis.declaration.invokable.function;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface DyanasisFunctionsProvider {
    /**
     * Gets the dyanasis functions.
     *
     * @return the functions
     */
    @NotNull Map<DyanasisFunctionKey, ? extends DyanasisFunction> dyanasisFunctions();
}
