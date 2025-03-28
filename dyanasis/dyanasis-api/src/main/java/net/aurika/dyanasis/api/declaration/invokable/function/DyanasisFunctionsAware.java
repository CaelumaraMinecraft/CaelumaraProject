package net.aurika.dyanasis.api.declaration.invokable.function;

import net.aurika.dyanasis.api.declaration.invokable.function.container.DyanasisFunctionContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Provider of {@linkplain DyanasisFunctionContainer}.
 */
public interface DyanasisFunctionsAware {

  /**
   * Gets the dyanasis functions.
   *
   * @return the functions
   */
  @NotNull DyanasisFunctionContainer dyanasisFunctions();

}
