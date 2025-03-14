package net.aurika.dyanasis.api.declaration.namespace;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface DyanasisNamespaceContainer {
    @NotNull Collection<? extends DyanasisNamespace> allNamespaces();

    @Nullable DyanasisNamespace findNamespace(@NotNull String @NotNull [] path);
}
