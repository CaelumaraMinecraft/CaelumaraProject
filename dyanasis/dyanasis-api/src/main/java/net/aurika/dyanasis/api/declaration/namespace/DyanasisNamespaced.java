package net.aurika.dyanasis.api.declaration.namespace;

import org.jetbrains.annotations.NotNull;

public interface DyanasisNamespaced {
    /**
     * Gets the dyanasis namespace.
     *
     * @return the namespace
     */
    @NotNull DyanasisNamespace dyanasisNamespace();
}
