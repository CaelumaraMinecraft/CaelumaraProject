package net.aurika.dyanasis.api.executing.result;

import net.aurika.dyanasis.api.exception.DyanasisFunctionException;
import org.jetbrains.annotations.NotNull;

public interface DyanasisExecuteResultFailed extends DyanasisExecuteResult {

  /**
   * Gets the exception to the failed function result.
   *
   * @return the exception
   */
  @NotNull DyanasisFunctionException exception();

}
