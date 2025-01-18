package top.auspice.key;

import org.jetbrains.annotations.NotNull;

/**
 * Container of {@link NSedKey}
 */
public interface NSKeyed {
    @NotNull NSedKey getNamespacedKey();
}
