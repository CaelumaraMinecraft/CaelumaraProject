package net.aurika.common.key.namespace;

import org.jetbrains.annotations.NotNull;

/**
 * Container of {@link NSedKey}
 */
@Deprecated
public interface Keyed {
    @NotNull NSedKey getNamespacedKey();
}
