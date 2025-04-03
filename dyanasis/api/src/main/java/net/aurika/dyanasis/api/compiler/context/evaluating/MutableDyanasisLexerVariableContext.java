package net.aurika.dyanasis.api.compiler.context.evaluating;

import net.aurika.dyanasis.api.variable.DyanasisVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public interface MutableDyanasisLexerVariableContext extends DyanasisLexerVariableProvider {

  @Override
  @Nullable Supplier<DyanasisVariable> provideVariable(@NotNull String varName);

  /**
   * Adds a variable.
   *
   * @param varName  the variable name
   * @param variable the variable
   */
  void addVariable(@NotNull String varName, @NotNull Supplier<DyanasisVariable> variable);

  /**
   * Sets the variables.
   *
   * @param variables the variables to set
   * @return the old variables map
   */
  @NotNull Map<String, Supplier<DyanasisVariable>> variables(Map<String, Supplier<DyanasisVariable>> variables);

}
