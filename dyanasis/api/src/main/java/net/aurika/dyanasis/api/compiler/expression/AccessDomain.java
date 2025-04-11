package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import org.jetbrains.annotations.NotNull;

/**
 * Domain object access, such as access a {@link DyanasisNamespace namespace},
 * a {@link DyanasisNamespace.NamespaceProperty namespace level property}.
 * Note that executes a function or a constructor is not a {@linkplain AccessDomain}.
 */
public interface AccessDomain extends Access {

  default @NotNull DomainAccessPath path() {
    throw new UnsupportedOperationException("path");
  }

  @NotNull String domainName();

}
