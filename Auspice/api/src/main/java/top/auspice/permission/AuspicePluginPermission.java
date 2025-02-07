package top.auspice.permission;

import org.jetbrains.annotations.NotNull;
import top.auspice.main.Auspice;

public interface AuspicePluginPermission extends Permission {
    default @NotNull String getNamespace() {
        return Auspice.NAMESPACE;
    }
}
