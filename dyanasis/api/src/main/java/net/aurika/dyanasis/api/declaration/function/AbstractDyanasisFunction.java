package net.aurika.dyanasis.api.declaration.function;

import net.aurika.dyanasis.api.declaration.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractDyanasisFunction implements DyanasisFunction {

  private final @NotNull DyanasisFunctionSignature key;

  public AbstractDyanasisFunction(@NotNull DyanasisFunctionSignature key) {
    this.key = key;
  }

  @Override
  public abstract @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input);

  @Override
  public @NotNull DyanasisFunctionSignature signature() {
    return key;
  }

  @Override
  public abstract @NotNull DyanasisFunctionAnchor owner();

}
