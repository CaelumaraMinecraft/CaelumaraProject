package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.declaration.function.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import org.jetbrains.annotations.NotNull;

/**
 * A function executing.
 */
public interface ExecuteFunction extends Execute {

  @NotNull DyanasisExecuteResult invoke();

  @NotNull DyanasisFunctionSignature functionSignature();

  @NotNull ExecutingArguments args();

  @Override
  @NotNull Expression operand();

}
