package net.aurika.config.scope;

import org.jetbrains.annotations.NotNull;
import net.aurika.abstraction.ScopedObject;
import net.aurika.config.CompleteConfigPath;
import net.aurika.common.key.namespace.nested.NamespaceNested;
import net.aurika.common.key.namespace.nested.NestedNamespace;

public interface ConfigScope extends NamespaceNested, ScopedObject.Scope {

    @Override
    @NotNull NestedNamespace getNestedNamespace();

    boolean isAvailable(CompleteConfigPath completePath);
}
