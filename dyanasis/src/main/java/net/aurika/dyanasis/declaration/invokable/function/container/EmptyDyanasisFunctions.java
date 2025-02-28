package net.aurika.dyanasis.declaration.invokable.function.container;

import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.declaration.invokable.function.DyanasisFunctionKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

public final class EmptyDyanasisFunctions implements DyanasisFunctions {
    public static final EmptyDyanasisFunctions INSTANCE = new EmptyDyanasisFunctions();

    private EmptyDyanasisFunctions() {
    }

    @Override
    public @Nullable DyanasisFunction getFunction(@NotNull DyanasisFunctionKey key) {
        return null;
    }

    @Override
    public @NotNull Map<DyanasisFunctionKey, DyanasisFunction> allFunctions() {
        return Collections.emptyMap();
    }
}
