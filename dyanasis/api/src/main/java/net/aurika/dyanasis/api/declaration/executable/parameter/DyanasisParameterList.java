package net.aurika.dyanasis.api.declaration.executable.parameter;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface DyanasisParameterList {

  /**
   * Gets the arguments count.
   *
   * @return the arguments count
   */
  int arity();

  /**
   * Gets the parameter that indexed {@code index}
   */
  @NotNull DyanasisParameter parameterAt(int index);

  @NotNull Iterator<? extends DyanasisParameter> parameters();

  @Override
  int hashCode();

  @Override
  boolean equals(Object o);

  @Override
  @NotNull String toString();

}
