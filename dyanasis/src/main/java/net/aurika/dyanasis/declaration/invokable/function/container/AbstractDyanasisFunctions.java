package net.aurika.dyanasis.declaration.invokable.function.container;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AbstractDyanasisFunctions implements DyanasisFunctions {
    protected final @NotNull Map<DyanasisFunctionKey, DyanasisFunction> functions;

    protected AbstractDyanasisFunctions() {
        this(new HashMap<>());
    }

    protected AbstractDyanasisFunctions(@NotNull Map<DyanasisFunctionKey, DyanasisFunction> functions) {
        Validate.Arg.notNull(functions, "functions");
        this.functions = functions;
    }

    @Override
    public @Nullable DyanasisFunction getFunction(@NotNull DyanasisFunctionKey key) {
        return functions.get(key);
    }

    @Override
    public @Unmodifiable @NotNull Map<DyanasisFunctionKey, DyanasisFunction> allFunctions() {
        return Collections.unmodifiableMap(functions);
    }
}
