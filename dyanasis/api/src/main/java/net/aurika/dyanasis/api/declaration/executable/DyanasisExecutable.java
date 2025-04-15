package net.aurika.dyanasis.api.declaration.executable;

import net.aurika.dyanasis.api.access.DyanasisAccessible;
import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import org.jetbrains.annotations.NotNull;

public interface DyanasisExecutable extends DyanasisDeclaration, DyanasisAccessible {

  /**
   * Executes and returns the result.
   *
   * @param input the executing input
   * @return the executing result
   */
  @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input);

}
