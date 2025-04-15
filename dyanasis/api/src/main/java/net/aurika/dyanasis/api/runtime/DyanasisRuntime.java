package net.aurika.dyanasis.api.runtime;

import net.aurika.dyanasis.api.runtime.environment.DyanasisRuntimeEnvironment;
import org.jetbrains.annotations.NotNull;

/**
 * A dyanasis runtime.
 */
public interface DyanasisRuntime {

  /**
   * Gets the id for this dyanasis runtime in this JVM. Each dyanasis runtime id is unique in a JVM.
   *
   * @return the runtime id
   */
  int runtimeID();

  /**
   * Gets the runtime environment.
   *
   * @return the runtime environment
   */
  @NotNull DyanasisRuntimeEnvironment environment();

  /**
   * Gets the label of the dyanasis runtime.
   *
   * @return the label
   */
  String label();

  /**
   * Sets the label of the dyanasis runtime.
   *
   * @param label the label
   */
  void label(String label);

}
