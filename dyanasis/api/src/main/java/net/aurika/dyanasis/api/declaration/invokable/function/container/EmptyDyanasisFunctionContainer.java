package net.aurika.dyanasis.api.declaration.invokable.function.container;

import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Map;

public final class EmptyDyanasisFunctionContainer implements DyanasisFunctionContainer<DyanasisFunction> {

  static final EmptyDyanasisFunctionContainer INSTANCE = new EmptyDyanasisFunctionContainer();

  private EmptyDyanasisFunctionContainer() {
  }

  @Override
  public boolean hasFunction(@NotNull DyanasisFunctionSignature key) {
    return false;
  }

  @Override
  public @Nullable DyanasisFunction getFunction(@NotNull DyanasisFunctionSignature key) {
    return null;
  }

  @Override
  public @NotNull Map<DyanasisFunctionSignature, DyanasisFunction> allFunctions() {
    return Collections.emptyMap();
  }

}
