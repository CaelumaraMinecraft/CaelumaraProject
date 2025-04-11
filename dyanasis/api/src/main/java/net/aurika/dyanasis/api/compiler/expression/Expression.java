package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.compiler.context.evaluating.DyanasisCompilerEvaluateContext;
import org.jetbrains.annotations.NotNull;

/**
 * The compiled expression.
 */
public interface Expression {

  /**
   * Evaluates and returns the result.
   *
   * @return the result
   */
  @NotNull Object evaluate(DyanasisCompilerEvaluateContext context);

  /**
   * Gets the corresponding compiler for this expression.
   *
   * @return the compiler
   */
  @NotNull DyanasisCompiler compiler();

}
