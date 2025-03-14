package net.aurika.dyanasis.api.declaration.invokable.function.container;

import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class DyanasisFunctionContainerRegistry extends AbstractDyanasisFunctionContainer {
    public DyanasisFunctionContainerRegistry() {
    }

    public DyanasisFunctionContainerRegistry(@NotNull Map<DyanasisFunctionKey, DyanasisFunction> functions) {
        super(functions);
    }

    /**
     * Adds a dyanasis function to this {@linkplain DyanasisFunctionContainerRegistry} and
     * returns the old function that contains the same name to {@code function}.
     *
     * @param function the function to add
     * @return the old function
     */
    public @Nullable DyanasisFunction addFunction(@NotNull DyanasisFunction function) {
        Validate.Arg.notNull(function, "property");
        DyanasisFunctionKey key = function.key();
        Objects.requireNonNull(key, "function.key()");
        return functions.put(key, function);
    }
}
