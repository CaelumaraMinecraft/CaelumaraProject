package net.aurika.dyanasis.api.declaration.invokable.function.key;

import net.aurika.dyanasis.api.Named;
import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Iterator;

public interface DyanasisFunctionSignature extends Named {

  @Contract(value = "_, _ -> new", pure = true)
  static @NotNull DefaultDyanasisFunctionSignature standardFunctionSignature(
      @NamingContract.Invokable final @NotNull String name,
      DefaultDyanasisFunctionSignature.ParameterBuilder... parameterBuilders
  ) {
    return new DefaultDyanasisFunctionSignature(name, parameterBuilders);
  }

  /**
   * Gets the dyanasis function name.
   *
   * @return the dyanasis function name
   */
  @Override
  @NamingContract.Invokable
  @NotNull String name();

  /**
   * Gets the arguments count.
   *
   * @return the arguments count
   */
  int arity();

  /**
   * Gets the parameter that indexed {@code index}
   */
  @NotNull Parameter parameterAt(int index);

  @NotNull Iterator<? extends Parameter> parameters();

  interface Parameter {

    boolean isAllowedInputType(@NotNull DyanasisTypeIdent typeIdent);

    /**
     * Gets the parameter name. There is no need to implementation this method.
     *
     * @return the parameter name
     */
    @UnknownNullability
    String name();

  }

  @Override
  int hashCode();

  @Override
  boolean equals(Object o);

  @Override
  @NotNull String toString();

}
