package net.aurika.dyanasis.api.runtime;

import org.jetbrains.annotations.NotNull;

/**
 * An object that knows a {@linkplain DyanasisRuntime}.
 */
public interface DyanasisRuntimeAware {

  /**
   * Gets the dyanasis runtime.
   *
   * @return the runtime
   */
  @NotNull DyanasisRuntime dyanasisRuntime();

}
