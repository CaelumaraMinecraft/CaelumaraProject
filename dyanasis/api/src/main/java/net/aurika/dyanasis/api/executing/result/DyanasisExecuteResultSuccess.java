package net.aurika.dyanasis.api.executing.result;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

public interface DyanasisExecuteResultSuccess extends DyanasisExecuteResult {

  /**
   * Gets the returned value of the dyanasis function.
   *
   * @return the returned value
   */
  @NotNull DyanasisObject returned();

}
