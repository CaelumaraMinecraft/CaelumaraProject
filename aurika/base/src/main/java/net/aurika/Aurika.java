package net.aurika;

import net.aurika.common.key.namespace.NSKey;
import net.aurika.common.key.namespace.NSedKey;
import org.jetbrains.annotations.NotNull;

public final class Aurika {
    public static final String NAMESPACE = "aurika";

    private Aurika() {
    }

    public static @NotNull NSedKey namespacedKey(@NotNull @NSKey.Key String key) {
        return NSedKey.of(NAMESPACE, key);
    }
}
