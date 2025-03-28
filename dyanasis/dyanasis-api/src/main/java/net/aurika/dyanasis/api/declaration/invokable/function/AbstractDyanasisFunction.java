package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisFunction implements DyanasisFunction {

  private final @NotNull DyanasisFunctionSignature key;

  public AbstractDyanasisFunction(@NotNull DyanasisFunctionSignature key) {
    this.key = key;
  }

  @Override
  public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

  @Override
  public @NotNull DyanasisFunctionSignature signature() {
    return key;
  }

  @Override
  public abstract @NotNull DyanasisFunctionAnchor owner();

}
