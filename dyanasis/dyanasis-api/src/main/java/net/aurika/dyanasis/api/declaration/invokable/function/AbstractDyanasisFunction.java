package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.declaration.invokable.AbstractDyanasisInvokable;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisFunction<A extends DyanasisFunctionAnchor> extends AbstractDyanasisInvokable<A> implements DyanasisFunction {

    private final @NotNull DyanasisFunctionKey key;

    @SuppressWarnings("PatternValidation")
    public AbstractDyanasisFunction(@NotNull DyanasisFunctionKey key, @NotNull A anchored) {
        super(Validate.Arg.notNull(key, "key").name(), anchored);
        this.key = key;
    }

    @Override
    public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

    @Override
    public @NotNull DyanasisFunctionKey key() {
        return key;
    }
}
