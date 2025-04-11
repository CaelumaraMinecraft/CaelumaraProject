package net.aurika.dyanasis.api.object;

import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectArray extends DyanasisObjectSupport {

  /**
   * Gets the dyanasis array size.
   *
   * @return the size
   */
  @DyanasisObjectDebugMethod
  default int size() {
    throw new UnsupportedOperationException();
  }

  /**
   * Gets the dyanasis array element from the {@code index}.
   *
   * @param index the index to the array
   * @return the array element on the {@code index}
   * @throws IndexOutOfBoundsException when the {@code index} is out of the array bound
   */
  @DyanasisObjectDebugMethod
  default @NotNull DyanasisObject get(int index) {
    throw new UnsupportedOperationException();
  }

  default @NotNull <Lambda extends DyanasisObjectLambda> DyanasisExecuteResult forEach(@NotNull Lambda lambda) {
    throw new UnsupportedOperationException();
  }

  @Override
  default @NotNull SupportType supportType() {
    return SupportType.ARRAY;
  }

  @Override
  @NotNull Object valueAsJava();

}
