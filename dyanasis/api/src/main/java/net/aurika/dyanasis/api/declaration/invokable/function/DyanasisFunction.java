package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.DyanasisInvokable;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * A {@linkplain DyanasisFunction} accepts {@linkplain DyanasisFunctionInput} and returns the result {@linkplain DyanasisFunctionResult}.
 */
public interface DyanasisFunction extends DyanasisInvokable, Function<DyanasisFunctionInput, DyanasisFunctionResult> {

  /**
   * Applies the dyanasis function invoking and returns the result.
   *
   * @param input the invoking input
   * @return the invoking result
   */
  @Override
  @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

  @Override
  @SuppressWarnings("PatternValidation")
  @NamingContract.Invokable
  default @NotNull String name() {
    return signature().name();
  }

  /**
   * Gets the dyanasis function key.
   *
   * @return the function key
   */
  @NotNull DyanasisFunctionSignature signature();

  /**
   * Gets the owner of this dyanasis function.
   *
   * @return the owner
   */
  @Override
  @NotNull DyanasisFunctionAnchor owner();

}
