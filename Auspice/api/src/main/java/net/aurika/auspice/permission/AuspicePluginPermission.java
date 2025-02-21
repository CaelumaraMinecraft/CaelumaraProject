package net.aurika.auspice.permission;

import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.main.Auspice;

public interface AuspicePluginPermission extends Permission {
    default @NotNull String namespace() {
        return Auspice.NAMESPACE;
    }
}
