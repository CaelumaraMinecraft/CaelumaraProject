package net.aurika.dyanasis.api.declaration.invokable.function.container;

import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

public final class EmptyDyanasisFunctionContainer implements DyanasisFunctionContainer<DyanasisFunction> {
    static final EmptyDyanasisFunctionContainer INSTANCE = new EmptyDyanasisFunctionContainer();

    private EmptyDyanasisFunctionContainer() {
    }

    @Override
    public boolean hasFunction(@NotNull DyanasisFunctionKey key) {
        return false;
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
