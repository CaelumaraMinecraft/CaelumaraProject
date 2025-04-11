package net.aurika.dyanasis.api.declaration.function.signature;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.executable.parameter.DyanasisParameterList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * {@linkplain DyanasisFunctionSignature} 的默认实现.
 */
public class DefaultDyanasisFunctionSignature implements DyanasisFunctionSignature {

  @NamingContract.Invokable
  private final @NotNull String name;
  private final @NotNull DyanasisParameterList parameters;

  protected DefaultDyanasisFunctionSignature(@NamingContract.Invokable final @NotNull String name, @NotNull DyanasisParameterList parameters) {
    this.name = name;
    this.parameters = parameters;
  }

  /**
   * Gets the dyanasis function name.
   *
   * @return the dyanasis function name
   */

  @Override
  @NamingContract.Invokable
  public @NotNull String name() {
    return name;
  }

  @Override
  public @NotNull DyanasisParameterList parameters() {
    return this.parameters;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, parameters);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DefaultDyanasisFunctionSignature)) return false;
    DefaultDyanasisFunctionSignature that = (DefaultDyanasisFunctionSignature) o;
    return Objects.equals(name, that.name) && Objects.equals(parameters, that.parameters);
  }

  @Override
  public @NotNull String toString() {
    return "FunctionKey[name='" + name + "', arity=" + parameters.arity() + "]";
  }

}
