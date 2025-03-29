package net.aurika.auspice.permission;

import net.aurika.auspice.user.Auspice;
import org.jetbrains.annotations.NotNull;

public interface AuspicePluginPermission extends Permission {

  default @NotNull String namespace() {
    return Auspice.NAMESPACE;
  }

}
