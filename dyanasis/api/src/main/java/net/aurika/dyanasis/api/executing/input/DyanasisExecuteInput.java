package net.aurika.dyanasis.api.executing.input;

import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

/**
 * The data of dyanasis function invocation. It is based on string.
 */
public interface DyanasisExecuteInput extends DyanasisRuntimeObject {

  /**
   * Gets an argument string from {@code index}.
   *
   * @param index the index
   * @return the argument
   * @throws IndexOutOfBoundsException when the index is out of bound for the arguments
   */
  @NotNull String stringAtArg(@Range(from = 0, to = 127) int index) throws IndexOutOfBoundsException;

  /**
   * Gets the arguments of the dyanasis function input.
   *
   * @return the arguments
   */
  @NotNull String @NotNull [] argStrings();

  @NotNull String originalString();

  @Override
  @NotNull DyanasisRuntime dyanasisRuntime();

}
