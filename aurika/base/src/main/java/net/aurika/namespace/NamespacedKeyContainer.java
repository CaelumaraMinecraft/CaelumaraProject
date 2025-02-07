package net.aurika.namespace;

import org.jetbrains.annotations.NotNull;

/**
 * Container of {@link NSedKey}
 */
public interface NamespacedKeyContainer {
    @NotNull NSedKey getNamespacedKey();
}
