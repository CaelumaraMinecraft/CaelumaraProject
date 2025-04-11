package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.declaration.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import org.jetbrains.annotations.NotNull;

/**
 * A function executing.
 */
public interface AccessFunction extends Access {

  @NotNull DyanasisExecuteResult invoke();

  @NotNull DyanasisFunctionSignature functionSignature();

  @NotNull FnArgs args();

  @Override
  @NotNull Expression operand();

}
