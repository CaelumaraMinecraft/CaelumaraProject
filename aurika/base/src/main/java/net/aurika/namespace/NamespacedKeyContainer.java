package net.aurika.namespace;

import org.jetbrains.annotations.NotNull;

/**
 * Container of {@link NSedKey}
 */
@Deprecated
public interface NamespacedKeyContainer {
    @NotNull NSedKey getNamespacedKey();
}
