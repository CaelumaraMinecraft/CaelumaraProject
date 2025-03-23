package net.aurika.auspice.permission;

import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.user.Auspice;

public interface AuspicePluginPermission extends Permission {
    default @NotNull String namespace() {
        return Auspice.NAMESPACE;
    }
}
