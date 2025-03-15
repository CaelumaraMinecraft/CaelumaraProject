package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisFunction implements DyanasisFunction {

    private final @NotNull DyanasisFunctionKey key;

    public AbstractDyanasisFunction(@NotNull DyanasisFunctionKey key) {
        this.key = key;
    }

    @Override
    public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

    @Override
    public @NotNull DyanasisFunctionKey key() {
        return key;
    }

    @Override
    public abstract @NotNull DyanasisFunctionAnchor owner();
}
