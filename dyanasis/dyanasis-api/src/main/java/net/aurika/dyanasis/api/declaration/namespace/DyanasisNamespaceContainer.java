package net.aurika.dyanasis.api.declaration.namespace;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface DyanasisNamespaceContainer {
    @NotNull Map<DyanasisNamespacePath, ? extends DyanasisNamespace> allNamespaces();

    @Nullable DyanasisNamespace findNamespace(@NotNull DyanasisNamespacePath path);

    /**
     * Finds a namespace that contains the {@code path}, if the namespace doesn't exist, creates a new one.
     *
     * @param path the namespace path
     * @return the found or created namespace
     */
    @NotNull DyanasisNamespace foundOrCreate(@NotNull DyanasisNamespacePath path);
}
