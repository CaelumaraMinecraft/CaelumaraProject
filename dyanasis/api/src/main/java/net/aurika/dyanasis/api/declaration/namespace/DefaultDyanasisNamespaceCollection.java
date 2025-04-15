package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class DefaultDyanasisNamespaceCollection implements DyanasisNamespaceContainer {

  @Override
  public @NotNull Map<DyanasisNamespaceIdent, ? extends DyanasisNamespace> allNamespaces() {
    return Map.of();
  }

  @Override
  public @Nullable DyanasisNamespace findNamespace(@NotNull DyanasisNamespaceIdent path) {
    return null;
  }

  @Override
  public @NotNull DyanasisNamespace foundOrCreate(@NotNull DyanasisNamespaceIdent path) {
    return null;
  }

  public class DefaultDyanasisNamespace implements DyanasisNamespace {

    private final @NotNull DyanasisNamespaceIdent identifier;

    @Override
    @NamingContract.Namespace
    public @NotNull String name() {
      // noinspection PatternValidation
      return identifier.endSection();
    }

    @Override
    public @NotNull DyanasisNamespaceIdent ident() {
      return identifier;
    }

    @Override
    public @Nullable DyanasisNamespace parent() {
      return null;
    }

    @Override
    public @Nullable DyanasisNamespace foundChild(@NotNull String name) {
      return null;
    }

    @Override
    public @NotNull Map<String, ? extends DyanasisNamespace> children() {
      return Map.of();
    }

    @Override
    public @NotNull NamespacePropertyContainer<? extends NamespaceProperty> dyanasisProperties() {
      return null;
    }

    @Override
    public @NotNull NamespaceFunctionContainer<? extends NamespaceFunction> dyanasisFunctions() {
      return null;
    }

    @Override
    public @NotNull NamespaceDomainsContainer dyanasisDomains() {
      return null;
    }

    @Override
    public @Nullable NamespaceDoc dyanasisDoc() {
      return null;
    }

    @Override
    public void dyanasisDoc(@Nullable NamespaceDoc doc) {

    }

    @Override
    public @Nullable DyanasisType<?> findDyanasisType(@NotNull String typename) {
      return null;
    }

    @Override
    public boolean hasDyanasisType(@NotNull String typename) {
      return false;
    }

    @Override
    public @NotNull Map<String, ? extends DyanasisType<?>> dyanasisTypes() {
      return Map.of();
    }

    @Override
    public @Nullable DyanasisType<?> addDyanasisType(@NotNull DyanasisType<?> type) {
      return null;
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return null;
    }

  }

}
