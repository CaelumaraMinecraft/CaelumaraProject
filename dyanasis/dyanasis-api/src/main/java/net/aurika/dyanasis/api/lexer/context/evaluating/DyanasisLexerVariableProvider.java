package net.aurika.dyanasis.api.lexer.context.evaluating;

import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.api.variable.DyanasisVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public interface DyanasisLexerVariableProvider {

  /**
   * Provides a variable that named {@code varName}.
   * Those things can be a variable: <b>{@link DyanasisProperty property}, {@link DyanasisVariable local variable}, parameter</b>
   *
   * @param varName the variable name
   * @return the variable
   */
  @Nullable Supplier<DyanasisVariable> provideVariable(@NotNull String varName);

  /**
   * Gets all variables in this lexer context.
   *
   * @return the variables
   */
  @NotNull Map<String, Supplier<DyanasisVariable>> variables();

}
