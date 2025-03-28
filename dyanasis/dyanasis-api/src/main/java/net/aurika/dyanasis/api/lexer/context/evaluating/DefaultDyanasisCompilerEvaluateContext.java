package net.aurika.dyanasis.api.lexer.context.evaluating;

import net.aurika.dyanasis.api.variable.DyanasisVariable;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DefaultDyanasisCompilerEvaluateContext implements MutableDyanasisLexerVariableContext, Cloneable {

  private @NotNull Map<String, Supplier<DyanasisVariable>> variables;

  public DefaultDyanasisCompilerEvaluateContext() {
    this(new HashMap<>());
  }

  public DefaultDyanasisCompilerEvaluateContext(@NotNull Map<String, Supplier<DyanasisVariable>> variables) {
    Validate.Arg.notNull(variables, "variables");
    this.variables = variables;
  }

  @Override
  public @Nullable Supplier<DyanasisVariable> provideVariable(@NotNull String varName) {
    return variables.get(varName);
  }

  @Override
  public @NotNull Map<String, Supplier<DyanasisVariable>> variables() {
    return new HashMap<>(variables);
  }

  @Override
  public void addVariable(@NotNull String varName, @NotNull Supplier<DyanasisVariable> variable) {
    variables.put(varName, variable);
  }

  @Override
  public @NotNull Map<String, Supplier<DyanasisVariable>> variables(Map<String, Supplier<DyanasisVariable>> variables) {
    var temp = this.variables;
    this.variables = variables;
    return temp;
  }

  @Override
  public @NotNull DefaultDyanasisCompilerEvaluateContext clone() {
    DefaultDyanasisCompilerEvaluateContext cloned;
    try {
      cloned = (DefaultDyanasisCompilerEvaluateContext) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
    cloned.variables = this.variables;
    return cloned;
  }

}
