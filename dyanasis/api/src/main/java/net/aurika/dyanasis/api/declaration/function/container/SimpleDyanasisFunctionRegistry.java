package net.aurika.dyanasis.api.declaration.function.container;

import net.aurika.dyanasis.api.declaration.member.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.member.function.key.DyanasisFunctionSignature;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SimpleDyanasisFunctionRegistry<F extends DyanasisFunction> implements DyanasisFunctionContainer<F> {

  protected final @NotNull Map<DyanasisFunctionSignature, F> functions;

  public SimpleDyanasisFunctionRegistry() {
    this(new HashMap<>());
  }

  public SimpleDyanasisFunctionRegistry(@NotNull Map<DyanasisFunctionSignature, F> functions) {
    Validate.Arg.notNull(functions, "functions");
    this.functions = functions;
  }

  /**
   * Adds a dyanasis function to this {@linkplain SimpleDyanasisFunctionRegistry} and
   * returns the old function that contains the same name to {@code function}.
   *
   * @param function the function to add
   * @return the old function
   */
  public @Nullable DyanasisFunction addFunction(@NotNull F function) {
    Validate.Arg.notNull(function, "property");
    DyanasisFunctionSignature key = function.signature();
    Objects.requireNonNull(key, "function.key()");
    return functions.put(key, function);
  }

  @Override
  public boolean hasFunction(@NotNull DyanasisFunctionSignature key) {
    return functions.containsKey(key);
  }

  @Override
  public @Nullable F getFunction(@NotNull DyanasisFunctionSignature key) {
    return functions.get(key);
  }

  @Override
  public @Unmodifiable @NotNull Map<DyanasisFunctionSignature, F> allFunctions() {
    return Collections.unmodifiableMap(functions);
  }

}
