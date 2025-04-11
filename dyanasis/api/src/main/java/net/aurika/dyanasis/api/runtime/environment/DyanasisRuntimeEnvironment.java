package net.aurika.dyanasis.api.runtime.environment;

import net.aurika.dyanasis.api.declaration.file.DyanasisFile;
import net.aurika.dyanasis.api.declaration.member.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.member.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceContainer;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * A dyanasis runtime environment.
 */
public interface DyanasisRuntimeEnvironment {

  @NotNull Collection<DyanasisFile> files();

  /**
   * Gets the current namespace container on this runtime.
   *
   * @return the namespace container
   */
  @NotNull DyanasisNamespaceContainer namespaces();

  default @NotNull Collection<DyanasisProperty> allProperties() {
    throw new UnsupportedOperationException();
  }

  default @NotNull Collection<DyanasisFunction> allFunctions() {
    throw new UnsupportedOperationException();
  }

}
