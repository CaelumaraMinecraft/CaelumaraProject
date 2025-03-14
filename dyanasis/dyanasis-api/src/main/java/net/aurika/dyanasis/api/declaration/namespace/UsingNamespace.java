package net.aurika.dyanasis.api.declaration.namespace;

import org.jetbrains.annotations.Nullable;

public interface UsingNamespace {
    /**
     * Gets the current using namespace.
     *
     * @return the namespace
     */
    @Nullable DyanasisNamespace usingNamespace();

    /**
     * Sets the using namespace.
     *
     * @param namespace the namespace
     */
    void usingNamespace(@Nullable DyanasisNamespace namespace);
}
