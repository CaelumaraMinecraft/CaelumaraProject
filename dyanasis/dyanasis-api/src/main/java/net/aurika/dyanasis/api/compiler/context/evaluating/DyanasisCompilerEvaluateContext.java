package net.aurika.dyanasis.api.compiler.context.evaluating;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceContainer;
import net.aurika.dyanasis.api.variable.DyanasisVariable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public interface DyanasisCompilerEvaluateContext extends DyanasisLexerVariableProvider {

  @NotNull DyanasisNamespaceContainer namespaces();

  @Override
  @Nullable Supplier<DyanasisVariable> provideVariable(@NotNull String varName);

  @Override
  @NotNull Map<String, Supplier<DyanasisVariable>> variables();

}
