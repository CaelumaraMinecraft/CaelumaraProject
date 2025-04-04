package net.aurika.config.scope;

import net.aurika.abstraction.ScopedObject;
import net.aurika.common.key.namespace.nested.NamespaceNested;
import net.aurika.common.key.namespace.nested.NestedNamespace;
import net.aurika.config.CompleteConfigPath;
import org.jetbrains.annotations.NotNull;

public interface ConfigScope extends NamespaceNested, ScopedObject.Scope {

  @Override
  @NotNull NestedNamespace getNestedNamespace();

  boolean isAvailable(CompleteConfigPath completePath);

}
