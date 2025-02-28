package net.aurika.dyanasis.declaration.invokable.function.container;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface DyanasisFunctions {
    /**
     * Gets a dyanasis function by the {@code key}.
     *
     * @param key the function key
     * @return the function
     */
    @Nullable DyanasisFunction getFunction(@NotNull DyanasisFunctionKey key);

    /**
     * Gets a mapping of all dyanasis functions.
     *
     * @return the functions mapping
     */
    @NotNull Map<DyanasisFunctionKey, DyanasisFunction> allFunctions();
}
