package top.auspice.config.scope;

import org.jetbrains.annotations.NotNull;
import top.auspice.abstraction.ScopedObject;
import top.auspice.config.CompleteConfigPath;
import top.auspice.key.nested.NamespaceNested;
import top.auspice.key.nested.NestedNamespace;

public interface ConfigScope extends NamespaceNested, ScopedObject.Scope {

    @Override
    @NotNull NestedNamespace getNestedNamespace();

    boolean isAvailable(CompleteConfigPath completePath);
}
