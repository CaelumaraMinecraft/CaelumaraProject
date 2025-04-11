package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link DyanasisNamespace namespace} access.
 */
public interface AccessNamespace extends Access, AccessDomain {

  @NotNull String namespaceName();

  @Override
  default @NotNull String domainName() {
    return namespaceName();
  }

}
