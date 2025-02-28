package net.aurika.dyanasis.declaration.invokable.function;

import net.aurika.dyanasis.declaration.invokable.DyanasisInvokable;
import net.aurika.dyanasis.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.invoking.result.DyanasisFunctionResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * A {@linkplain DyanasisFunction} accepts {@linkplain DyanasisFunctionInput} and returns the result {@linkplain DyanasisFunctionResult}.
 */
public interface DyanasisFunction extends DyanasisInvokable, Function<DyanasisFunctionInput, DyanasisFunctionResult> {
    /**
     * Gets the dyanasis function key.
     *
     * @return the function key
     */
    @NotNull DyanasisFunctionKey key();

    /**
     * Gets the owner of this dyanasis function.
     *
     * @return the owner
     */
    @Override
    @NotNull DyanasisFunctionOwner owner();

    /**
     * Applies the dyanasis function invoking and returns the result.
     *
     * @param input the invoking input
     * @return the invoking result
     */
    @Override
    @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);
}
