package net.aurika.dyanasis.api.declaration.function;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.executable.DyanasisExecutable;
import net.aurika.dyanasis.api.declaration.member.DyanasisMember;
import net.aurika.dyanasis.api.declaration.executable.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import org.jetbrains.annotations.NotNull;

/**
 * A {@linkplain DyanasisFunction} accepts {@linkplain DyanasisExecuteInput} and returns the result {@linkplain DyanasisExecuteResult}.
 */
public interface DyanasisFunction extends DyanasisExecutable, DyanasisMember {

  @Override
  @SuppressWarnings("PatternValidation")
  @NamingContract.Invokable
  default @NotNull String name() {
    return signature().name();
  }

  /**
   * Gets the dyanasis function key.
   *
   * @return the function key
   */
  @NotNull DyanasisFunctionSignature signature();

  /**
   * Gets the owner of this dyanasis function.
   *
   * @return the owner
   */
  @Override
  @NotNull DyanasisFunctionAnchor owner();

}
