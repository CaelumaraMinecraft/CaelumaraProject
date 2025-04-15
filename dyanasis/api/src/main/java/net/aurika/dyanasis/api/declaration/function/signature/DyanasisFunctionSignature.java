package net.aurika.dyanasis.api.declaration.function.signature;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.executable.parameter.DyanasisParameterList;
import net.aurika.dyanasis.api.declaration.function.DyanasisFunction;
import org.jetbrains.annotations.NotNull;

/**
 * The signature of {@linkplain DyanasisFunction}.
 */
public interface DyanasisFunctionSignature extends Named {

  /**
   * Gets the dyanasis function name.
   *
   * @return the dyanasis function name
   */
  @Override
  @NamingContract.Member
  @NotNull String name();

  @NotNull DyanasisParameterList parameters();

}
